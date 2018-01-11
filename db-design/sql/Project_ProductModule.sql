USE no_problem;

CREATE TABLE IF NOT EXISTS Project_ProductModule (
	`moduleID` int NOT NULL AUTO_INCREMENT COMMENT '项目模块ID'
	,`productID` int NOT NULL COMMENT '产品ID'
	,`moduleName` varchar(30) NOT NULL COMMENT '模块名称'
	,`isActive` boolean NOT NULL DEFAULT 1 COMMENT '0-删除, 1-正常'
	,`inDate` datetime(3) NOT NULL COMMENT '写入时间'
	,`inUser` int NOT NULL COMMENT '创建用户ID'
	,`lastEditUser` int COMMENT '更新用户'
	,`lastEditDate` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更改日期'
	,CONSTRAINT PK_Project_ProductModule_moduleID PRIMARY KEY (moduleID)
)engine=InnoDB default charset utf8mb4;

CREATE INDEX IX_Project_ProductModule_productID ON Project_ProductModule(productID);
