-- 1.参数列表
-- 1.1 优惠卷id
local goodsId = ARGV[1]
-- 1.2 用户id
local userId = ARGV[2]

-- 2. 数据key
-- 2.1 库存key 拼接 ..
local stockKey = 'SECKILL:STOCK:' .. goodsId
-- 2.2 订单key 拼接 ..
local orderKey = "SECKILL:ORDER:" .. goodsId

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
