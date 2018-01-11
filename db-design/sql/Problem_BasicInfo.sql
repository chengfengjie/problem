USE no_problem;

CREATE TABLE IF NOT EXISTS Problem_BasicInfo (
	`problemID` int NOT NULL AUTO_INCREMENT COMMENT '问题ID'
	,`projectID` int NOT NULL COMMENT '项目ID'
	,`productID` int COMMENT '产品ID'
	,`problemType` tinyint NOT NULL COMMENT '1-缺陷, 2-任务, 3-需求'
	,`moduleID` int COMMENT '模块ID'
	,`problemTitle` varchar(50) NOT NULL COMMENT '问题标题'
	,`versionName` varchar(50) COMMENT '版本名称'
	,`versionID` int COMMENT '版本ID'
	,`environment` varchar(100) COMMENT '发现环境'
	,`description` varchar(2000) COMMENT '问题描述'
	,`solveUserID` int COMMENT '指派给的用户ID'
	,`priority` tinyint NOT NULL COMMENT '1-急,2-高,3-中,4-低'
	,`beginDate` datetime(3) COMMENT '计划开始时间'
	,`finishDate` datetime(3) COMMENT '计划结束时间'
	,`planVersion` varchar(50) COMMENT '计划版本'
	,`status` tinyint COMMENT '0-关闭, 1-打开, 2-延期，3-待审核，4-已解决, 11-不是错误, 12-重复问题, 13-延期解决, 14-设计如此, 15-不能复现, 16-不同意意见, 17-忽略'
	,`isActive` boolean NOT NULL DEFAULT 1 COMMENT '0-删除, 1-正常'
	,`inDate` datetime(3) NOT NULL COMMENT '写入时间'
	,`inUser` int NOT NULL COMMENT '创建用户ID'
	,`lastEditUser` int COMMENT '更新用户'
	,`lastEditDate` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更改日期'
	,CONSTRAINT PK_Problem_BasicInfo_problemID PRIMARY KEY (problemID)
)engine=InnoDB default charset utf8mb4;

CREATE INDEX IX_Problem_BasicInfo_projectID ON Problem_BasicInfo(projectID);
CREATE INDEX IX_Problem_BasicInfo_productID ON Problem_BasicInfo(productID);
