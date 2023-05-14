-- myproject.bamboo_goods definition

CREATE TABLE `bamboo_goods` (
  `id` bigint NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '商品名字',
  `is_ground` tinyint NOT NULL DEFAULT '0' COMMENT '是否上架 0未上架 1上架',
  `item_price` decimal(10,2) NOT NULL COMMENT '商品价格',
  `extra_info` varchar(255) DEFAULT NULL COMMENT '商品信息',
  `stock` int DEFAULT '0' COMMENT '库存',
  `item_img` blob COMMENT '商品图片',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品表';


-- myproject.bamboo_goods_order definition

CREATE TABLE `bamboo_goods_order` (
  `id` bigint NOT NULL COMMENT '订单id',
  `goods_id` bigint NOT NULL COMMENT '商品id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `pay_count` int NOT NULL COMMENT '购买数量(数量小于商品数量)',
  `address` varchar(225) DEFAULT NULL COMMENT '邮寄地址',
  `is_pay_success` tinyint NOT NULL DEFAULT '0' COMMENT '是否支付成功 0未成功 1成功',
  `is_pay_overdue` tinyint NOT NULL DEFAULT '0' COMMENT '是否支付过期 0未过期 1过期',
  `is_delete` tinyint NOT NULL DEFAULT '0' COMMENT '是否被删除(采用逻辑删除) 0未删除 1删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `bamboo_goods_order_goods_id_IDX` (`goods_id`) USING BTREE,
  KEY `bamboo_goods_order_user_id_IDX` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- myproject.bamboo_goods_seckill definition

CREATE TABLE `bamboo_goods_seckill` (
  `goods_id` bigint NOT NULL COMMENT '商品id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `stock` int NOT NULL COMMENT '库存',
  `seckill_start_time` datetime NOT NULL COMMENT '开始时间',
  `seckill_end_time` datetime NOT NULL COMMENT '结束时间',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`goods_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品秒杀表';


-- myproject.bamboo_music_info definition

CREATE TABLE `bamboo_music_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `room_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '房间号码',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `author` varchar(50) NOT NULL COMMENT '作家',
  `remarks` varchar(100) DEFAULT NULL COMMENT '备注',
  `live_url` varchar(100) NOT NULL COMMENT 'liveurl地址',
  `token` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'token',
  `img_data` blob COMMENT '图片',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bamboo_music_info_UN` (`room_id`),
  UNIQUE KEY `bamboo_music_info_AUTHOR_UN` (`author`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- myproject.bamboo_payment_channel definition

CREATE TABLE `bamboo_payment_channel` (
  `id` bigint NOT NULL COMMENT 'id',
  `channel_name` varchar(32) NOT NULL COMMENT '渠道名称',
  `channel_id` bigint NOT NULL COMMENT '渠道id',
  `merchant_id` bigint NOT NULL COMMENT '商户id',
  `sync_url` text NOT NULL COMMENT '同步回调url',
  `asyn_url` text NOT NULL COMMENT '异步回调url',
  `public_key` text COMMENT '公钥',
  `private_key` text COMMENT '私钥',
  `channel_state` tinyint DEFAULT '0' COMMENT '渠道状态 0开启1关闭',
  `revision` int DEFAULT NULL COMMENT '乐观锁',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `updated_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `class_addres` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付渠道 ';


-- myproject.bamboo_payment_transaction definition

CREATE TABLE `bamboo_payment_transaction` (
  `id` bigint NOT NULL COMMENT '主键id',
  `pay_amount` decimal(10,2) NOT NULL COMMENT '支付金额',
  `payment_status` tinyint NOT NULL DEFAULT '0' COMMENT '支付状态 0待支付1已经支付2支付超时3支付失败',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `order_id` bigint NOT NULL COMMENT '订单号码',
  `revision` int DEFAULT NULL COMMENT '乐观锁',
  `partypay_id` bigint DEFAULT NULL,
  `payment_id` bigint DEFAULT NULL COMMENT '订单号',
  `product_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `updated_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付交易 ';


-- myproject.bamboo_payment_transaction_log definition

CREATE TABLE `bamboo_payment_transaction_log` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `synch_log` text COMMENT '同步回调日志',
  `async_log` text COMMENT '异步回调日志',
  `channel_id` bigint DEFAULT NULL COMMENT '支付渠道id',
  `transaction_id` bigint DEFAULT NULL COMMENT '支付交易id',
  `revision` int DEFAULT NULL COMMENT '乐观锁',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `updated_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `untitled` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付交易日志表 ';


-- myproject.`role` definition

CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'roleID',
  `role_name` varchar(20) DEFAULT NULL,
  `role_name_cn` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- myproject.room_info definition

CREATE TABLE `room_info` (
  `room_id` bigint NOT NULL COMMENT '随机房间ID',
  `room_name` varchar(100) DEFAULT NULL COMMENT '房间名称',
  `room_creator_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `room_creator_id` varchar(100) DEFAULT NULL COMMENT '创建者ID',
  `room_mode` varchar(1) DEFAULT NULL COMMENT '模式',
  `room_remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `room_max_num_people` varchar(30) DEFAULT NULL COMMENT '最大人数',
  `room_online_people` varchar(30) DEFAULT NULL COMMENT '在线人数',
  `room_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT ' 创建时间',
  `room_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- myproject.room_preferences definition

CREATE TABLE `room_preferences` (
  `user_id` bigint NOT NULL,
  `room_id` int NOT NULL,
  `preference` float NOT NULL COMMENT '喜欢程度暂时用关注度来代替',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`,`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- myproject.`user` definition

CREATE TABLE `user` (
  `id` bigint NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
  `sex` int DEFAULT NULL COMMENT '性别 0 男 1女',
  `age` int DEFAULT NULL COMMENT '年龄',
  `height` varchar(100) DEFAULT NULL,
  `password` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
  `locked` tinyint NOT NULL DEFAULT '1' COMMENT '0未锁定 1锁定',
  `enabled` tinyint NOT NULL DEFAULT '0' COMMENT '0激活 1未激活',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_UN` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';


-- myproject.user_with_role definition

CREATE TABLE `user_with_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `role_id` int NOT NULL DEFAULT '2' COMMENT '1.超级管理员 2.普通用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色用户关联表';
