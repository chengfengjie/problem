USE no_problem;

CREATE TABLE IF NOT EXISTS Project_BasicInfo (
	`projectID` int NOT NULL AUTO_INCREMENT COMMENT '项目ID'
	,`projectName` varchar(50) NOT NULL COMMENT '项目名称'
	,`projectDescription` varchar(200) COMMENT '项目描述'
	,`beginDate` datetime(3) COMMENT '开始时间'
	,`finishDate` datetime(3) COMMENT '结束时间'
	,`status` tinyint NOT NULL DEFAULT 1 COMMENT '0-未开始,1-已开始,2-已竣工,3-已终止'
	,`inUser` int NOT NULL COMMENT '创建用户ID'
	,`inDate` datetime(3) NOT NULL COMMENT '创建时间'
	,`lastEditDate` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更改日期'
	,`isActive` boolean NOT NULL DEFAULT 1 COMMENT '0-活跃, 1-关闭'
	,CONSTRAINT PK_Project_BasicInfo_projectID PRIMARY KEY (projectID)
)engine=InnoDB default charset utf8mb4;
