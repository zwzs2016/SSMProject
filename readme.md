# 在线视频商品平台bamboo

​		spring cloud (eureka)

​		spring security

​		openfeign

​		elasticsearch

​		kafka

​		redis

​		mysql

​		docker

​		maven

​		nginx



### 	第三方服务:

​	[MobileIMSDK](https://github.com/JackJiang2011/MobileIMSDK)

​    [captcha](https://github.com/anji-plus/captcha)

​    [nginx-rtmp-module](https://github.com/arut/nginx-rtmp-module)

## Project Description:

## 在线视频商品平台bamboo,使用推流服务和spring实现的通过推流工具直播推流的商品项目

login auth登录模块==>实现登录注册 spring security

bamboo==>实现推流rmtp

消息处理模块==>kafka异步消息消费

搜索模块==>elasticsearch index sync mysql    query datainfo  in redis-->elasticsearch

common模块==>entity dto util...

getway==>网关控制开发中...

registry:注册中心server eureka

os run:ubuntu (linux) or  docker...

new function:

- ​	 mahout recommend service:service-recommend

- ​     getway service add > redis-rate-limiter 			

- ​	mysql主从复制 mycat sharding分库分表

- ​    seata分布式框架

