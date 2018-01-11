USE no_problem;
CREATE TABLE IF NOT EXISTS User_BasicInfo (
	`userID` int NOT NULL AUTO_INCREMENT COMMENT '用户ID'
	,`email` varchar(100) NOT NULL COMMENT '用户邮箱'
	,`cellphone` varchar(30) COMMENT '手机号'
	,`password` varchar(100) NOT NULL COMMENT '密码'
	,`passwordSalt` varchar(50) NOT NULL COMMENT '密码加盐'
	,`userName` varchar(100) NOT NULL COMMENT '用户名称'
	,`avatar` varchar(200) NOT NULL COMMENT '用户头像'
	,`status` tinyint NOT NULL DEFAULT 1 COMMENT '0-删除，1-启用，2-禁用'
	,`role` tinyint NOT NULL DEFAULT 2 COMMENT '1-超级管理员,2-普通用户'
	,`description` VARCHAR(200) COMMENT '用户描述'
	,`inDate` datetime(3) NOT NULL COMMENT '注册时间'
	,`lastEditDate` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更改日期'
	,CONSTRAINT PK_User_BasicInfo_userID PRIMARY KEY (userID)
)engine=InnoDB default charset utf8mb4;
