USE no_problem;

CREATE TABLE IF NOT EXISTS Project_ProductVersion (
	`versionID` int NOT NULL AUTO_INCREMENT COMMENT '产品版本ID'
	,`productID` int NOT NULL COMMENT '产品ID'
	,`versionName` varchar(50) COMMENT '版本名称'
	,`downloadUrl` varchar(200) COMMENT '下载地址'
	,`description` varchar(2000) COMMENT '版本描述'
	,`isActive` boolean NOT NULL DEFAULT 1 COMMENT '0-删除, 1-正常'
	,`inDate` datetime(3) NOT NULL COMMENT '写入时间'
	,`inUser` int NOT NULL COMMENT '创建用户ID'
	,`lastEditUser` int COMMENT '更新用户'
	,`lastEditDate` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更改日期'
	,CONSTRAINT PK_Project_ProductVersion_versionID PRIMARY KEY (versionID)
)engine=InnoDB default charset utf8mb4;

CREATE INDEX IX_Project_ProductVersion_productID ON Project_ProductVersion(productID);
