# bamboo音乐电台

## 使用到的technology:

### 	后端 primary:

​		spring boot 

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

### 	前端 primary：

​		vue 3.0

​		bootsrap v5

​		html

​		axios



### 	第三方服务:

​	[MobileIMSDK](https://github.com/JackJiang2011/MobileIMSDK)

​    [captcha](https://github.com/anji-plus/captcha)

​    [nginx-rtmp-module](https://github.com/arut/nginx-rtmp-module)

## Project Description:

## bamboo音乐直播电台,使用推流服务和spring实现的一个可以通过推流工具进行直播推流的Web项目

login auth登录模块==>实现登录注册 spring security

bamboo直播模块==>实现推流rmtp

消息处理模块==>kafka异步消息消费

搜索模块==>elasticsearch index sync mysql    query datainfo  in redis-->elasticsearch

common模块==>entity dto util...

getway==>网关控制开发中...

registry:注册中心server eureka

os run:ubuntu (linux) or  docker...



项目是用来学习java spring 而开发的,后续的功能有待开发更新...

主要是开发是后端,前端是使用的bootstrap,html,vue来请求的,页面的功能不多,可以正常请求








