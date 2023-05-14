package com.bamboo.service.Impl;

import com.bamboo.mapper.BambooGoodsMapper;
import com.bamboo.mapper.BambooGoodsOrderMapper;
import com.bamboo.service.BambooGoodsOrderService;
import com.bamboo.service.feign.PaymentFeignService;
import com.bamboo.util.RedisIdWorker;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uwan.common.constant.ProductResults;
import com.uwan.common.constant.RedisConstants;
import com.uwan.common.dto.BambooPaymentTransactionDTO;
import com.uwan.common.entity.BambooGoods;
import com.uwan.common.entity.BambooGoodsOrder;
import com.uwan.common.entity.BambooGoodsSeckill;
import com.uwan.common.entity.BambooPaymentTransaction;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class BambooGoodsOrderServiceImpl extends ServiceImpl<BambooGoodsOrderMapper,BambooGoodsOrder> implements BambooGoodsOrderService {
    @Autowired
    RedissonClient redissonClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisIdWorker redisIdWorker;

    @Autowired
    PaymentFeignService paymentFeignService;

    @Autowired
    BambooGoodsMapper bambooGoodsMapper;



    private static final DefaultRedisScript<Long> SECKILL_SCRIPT;

    static {
        SECKILL_SCRIPT = new DefaultRedisScript<>();
        SECKILL_SCRIPT.setLocation(new ClassPathResource("script/seckill.lua"));
        SECKILL_SCRIPT.setResultType(Long.class);
    }

    private BlockingQueue<BambooGoodsOrder> orderTasks =new ArrayBlockingQueue<>(1024 * 1024);
    private static final ExecutorService SECKILL_ORDER_EXECUTOR = Executors.newSingleThreadExecutor();

    //项目启动后执行该方法
    @PostConstruct
    private void init() {
        SECKILL_ORDER_EXECUTOR.submit(new GoodsOrderHandler());
    }

    // 用于线程池处理的任务
    // 当初始化完毕后 就会去从对列中去拿信息
    private class GoodsOrderHandler implements Runnable {

        @Override
        public void run() {
            while (true){
                try {
                    // 1.获取队列中的订单信息
                    BambooGoodsOrder bambooGoodsOrder = orderTasks.take();
                    // 2.创建订单
                    handleGoodsOrder(bambooGoodsOrder);
                } catch (Exception e) {
                    log.error("处理订单异常", e);
                }
            }
        }
    }

    private void handleGoodsOrder(BambooGoodsOrder bambooGoodsOrder) {
        //1.获取用户
        Long userId = bambooGoodsOrder.getUserId();
        // 2.创建锁对象
        RLock lock = redissonClient.getLock("lock:order:" + userId);
        // 3.尝试获取锁
        boolean isLock = lock.tryLock();

        // 4.判断是否获得锁成功
        if (!isLock) {
            // 获取锁失败，直接返回失败或者重试
            log.error("不允许重复下单！");
            return;
        }
        try {
            //注意：由于是spring的事务是放在threadLocal中，此时的是多线程，事务会失效
            createBambooGoodsOrder(bambooGoodsOrder);
        } finally {
            //查看是自己加的锁吗,是自己的锁再释放
            if (lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }


    // 代理对象
//    private IVoucherOrderService proxy;

    @Override
    public String seckillGoods(Long goodsId,Long userId) {

        // 获取订单id
        long orderId = redisIdWorker.nextId("order");

        // 1. 执行lua 脚本
        Long result = stringRedisTemplate.execute(
                SECKILL_SCRIPT,
                Collections.emptyList(),
                goodsId.toString(),
                userId.toString(), String.valueOf(orderId)
        );

        int r = result.intValue();

        // 2. 判断结果是否为0
        if (r != 0) {
            // 2.1 不为0 代表没有购买资格
            return r== 1?ProductResults.PRODUCT_NOT_ENOUGH.getMsg():ProductResults.PRODUCT_REORDER.getMsg();
//            return r == 1 ? "库存不足" : "不允许重复下单";
        }
        // 2.2 为0，有购买资格 把下单信息保存到阻塞队列
        // 2.2 有购买的资格，创建订单放入阻塞队列中
        BambooGoodsOrder bambooGoodsOrder = new BambooGoodsOrder();
        // 2.3.订单id
        bambooGoodsOrder.setId(orderId);
        // 2.4.用户id
        bambooGoodsOrder.setUserId(userId);
        // 2.5.代金券id
        bambooGoodsOrder.setGoodsId(goodsId);
        // 2.6.放入阻塞队列
        orderTasks.add(bambooGoodsOrder);
        //3.获取代理对象
//        proxy = (IVoucherOrderService)AopContext.currentProxy();
        // 2.3 返回订单id

        return String.valueOf(orderId);
    }

    @Override
    public void addSeckillGoods(BambooGoodsSeckill bambooGoodsSeckill) {
        // 保存秒杀商品
    }


//    @Transactional
//    @GlobalTransactional(rollbackFor = Exception.class)
    public void createBambooGoodsOrder (BambooGoodsOrder bambooGoodsOrder){
//        log.info("开始全局事务，XID = " + RootContext.getXID());

        // 5.一人一单逻辑
        // 5.1.用户id
        boolean save = save(bambooGoodsOrder);//保存订单

        if (save){
            log.info("save GoodsOrder To DB Success");
            //保存到redis中 将orderId作为key,userid作为val保存到redis中
//            stringRedisTemplate.opsForValue().set(RedisConstants.SECKILL_ORDER_KEY.getMsg()+bambooGoodsOrder.getId().toString(),bambooGoodsOrder.getUserId().toString());

            log.info("save GoodsOrder To Redis Success");
            log.info("调用payment-service 添加支付信息");
            BambooPaymentTransactionDTO bambooPaymentTransactionDTO=new BambooPaymentTransactionDTO();
            bambooPaymentTransactionDTO.setUserId(bambooGoodsOrder.getUserId());
            bambooPaymentTransactionDTO.setOrderId(bambooGoodsOrder.getId());

            //根据goodsId查询出订单商品的价格
            QueryWrapper<BambooGoods> queryWrapper=Wrappers.query();
            queryWrapper.eq("id",bambooGoodsOrder.getGoodsId());
            queryWrapper.select("name,item_price");
            BambooGoods goods = bambooGoodsMapper.selectOne(queryWrapper);

            //秒杀商品数量为1
            bambooPaymentTransactionDTO.setPayAmount(goods.getItemPrice());
            bambooPaymentTransactionDTO.setProductName(goods.getName());

            String s= RootContext.getXID();
            paymentFeignService.addPaymentTransaction(bambooPaymentTransactionDTO);

        }else {
            log.error("save fail");
        }
    }
}
