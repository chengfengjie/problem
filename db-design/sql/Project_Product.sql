USE no_problem;

CREATE TABLE IF NOT EXISTS Project_Product (
	`productID` int NOT NULL AUTO_INCREMENT COMMENT '项目产品ID'
	,`projectID` int NOT NULL COMMENT '项目ID'
	,`productName` varchar(100) NOT NULL COMMENT '产品名称'
	,`isOpen` boolean NOT NULL DEFAULT 1 COMMENT '0-关闭, 1-打开'
	,`isActive` boolean NOT NULL DEFAULT 1 COMMENT '0-删除, 1-正常'
	,`inDate` datetime(3) NOT NULL COMMENT '写入时间'
	,`inUser` int NOT NULL COMMENT '创建用户ID'
	,`lastEditUser` int COMMENT '更新用户'
	,`lastEditDate` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更改日期'
	,CONSTRAINT PK_Project_Product_productID PRIMARY KEY (productID)
)engine=InnoDB default charset utf8mb4;

CREATE INDEX IX_Project_Product_projectID ON Project_Product(projectID);
