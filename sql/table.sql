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


-- myproject.user1 definition

CREATE TABLE `user1` (
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
