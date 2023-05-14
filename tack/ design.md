https://blog.csdn.net/weixin_45526437/article/details/127608388

秒杀服务设计

1. 查询秒杀的商品
2. 判断秒杀库存是否足够
3. 查询订单
4. 校验是否是一人一单
5. 扣减库存
6. 创建订单，完成

商品信息，订单信息，秒杀商品信息

首先秒杀商品是商品表中的，然后将秒杀商品信息存入到redis集群中(高并发)，

<考虑的问题:秒杀商品时高并发问题>

<尝试:加入分布式锁以及分布式事务>

用户秒杀商品成功后将秒杀的商品信息存入到redis中（编写**Lua 秒杀脚本**）

-- 1.参数列表
-- 1.1 优惠卷id
local voucherId = ARGV[1]
-- 1.2 用户id
local userId = ARGV[2]

-- 2. 数据key
-- 2.1 库存key 拼接 ..
local stockKey = 'seckill:stock:' .. voucherId
-- 2.2 订单key 拼接 ..
local orderKey = "seckill:order" .. voucherId

-- 3. 脚本业务
-- 3.1 判断库存是否充足
if (tonumber(redis.call('get', stockKey)) <= 0) then
    -- 3.2 库存不足
    return 1
end
-- 3.2 判断用户是否下单 SISMEMBER orderKey userId
if (redis.call('sismember', orderKey, userId) == 1) then
    -- 3.3 存在，证明是重复下单
    return 2
end
-- 3.4 扣库存 incrby stockKey -1
redis.call('incrby', stockKey, -1)
-- 3.5 下单 保存用户 sadd orderKey userId
redis.call('sadd', orderKey, userId)
return 0



基于阻塞队列完成异步秒杀下单

核心思路：**将请求存入阻塞队列中 进行缓存，开启线程池读取任务并依次处理。**