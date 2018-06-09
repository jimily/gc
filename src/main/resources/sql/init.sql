CREATE TABLE `biz_project` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '唯一主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '项目名称',
  `type` tinyint(2) NOT NULL COMMENT '项目类型： 1：书籍 2：漫画 3：电视剧 4：艺术品 5：电影',
  `description` varchar(1024) NOT NULL COMMENT '项目描述',
  `copyright_no` varchar(64) NOT NULL COMMENT '版权号',
  `ratio` bigint(32) NOT NULL COMMENT '众筹占比',
  `gross` bigint(32) NOT NULL COMMENT '总量',
  `once_buy_limit` bigint(32) NOT NULL COMMENT '单次购买限制 0 默认没有单次购买限制',
  `balance` tinyint(2) NOT NULL COMMENT '余额',
  `is_end` tinyint(2) NOT NULL COMMENT '项目是否已经结束',
  `contract_address` varchar(64) NOT NULL COMMENT '合约区块链地址',
  `user_id` bigint(32) NOT NULL COMMENT '用户id',
  `create_time` bigint(32) NOT NULL COMMENT '创建时间',
  `update_time` bigint(32) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='项目表';

CREATE TABLE `biz_user_account` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '唯一主键id',
  `user_id` bigint(32) NOT NULL COMMENT '账号id',
  `asset` bigint(32) NOT NULL COMMENT '账号资产',
  `pub_key` varchar(128) NOT NULL,
  `private_key` varchar(128) NOT NULL ,
  `blockchain_address` varchar(64) NOT NULL COMMENT '区块链地址',
  `create_time` bigint(32) NOT NULL COMMENT '创建时间',
  `update_time` bigint(32) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT = '用户资产表';

CREATE TABLE `biz_project_schedule` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '唯一主键id',
  `user_id` bigint(32) NOT NULL COMMENT '购买人',
  `project_id` bigint(32) NOT NULL COMMENT '项目id',
  `balance` bigint(32) NOT NULL COMMENT '购买数量',
  `tx_hash` varchar(255) NOT NULL COMMENT '交易hash', 
  `create_time` bigint(32) NOT NULL COMMENT '创建时间',
  `update_time` bigint(32) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT = '项目进度表';

CREATE TABLE `biz_project_sell` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '唯一主键id',
  `project_id` bigint(32) NOT NULL COMMENT '项目id',
  `copyright_sell_type` tinyint(2) NOT NULL COMMENT '版权交易类型',
  `selling_price` bigint(32) NOT NULL COMMENT '售价',
  `tx_hash` varchar(255) NOT NULL COMMENT '交易hash', 
  `description` varchar(1024) NOT NULL COMMENT '发布说明',
  `owner_user_id` bigint(32) NOT NULL COMMENT '购买人id',
  `buy_user_id` bigint(32) NOT NULL COMMENT '购买人id',
  `is_sell` tinyint(2) NOT NULL COMMENT '是否已经出售',
  `create_time` bigint(32) NOT NULL COMMENT '创建时间',
  `update_time` bigint(32) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT = '项目出售表';


