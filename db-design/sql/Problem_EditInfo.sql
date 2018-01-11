USE no_problem;
CREATE TABLE IF NOT EXISTS Problem_EditInfo (
	`editInfoID` int NOT NULL AUTO_INCREMENT COMMENT 'id 主键'
	,`problemID` int NOT NULL COMMENT '问题ID'
	,`editType` tinyint NOT NULL COMMENT '0-创建,1-评论, 2-指派, 3-修复, 4-拒绝, 5-关闭, 6-延期, 7-通过, 8-不通过, 9-打开'
	,`content` varchar(500) COMMENT '编辑内容'
	,`rejectType` tinyint COMMENT '1-不是错误, 2-重复问题, 3-延期解决, 4-设计如此, 5-不能复现, 6-不同意意见, 7-忽略'
	,`auditVersionName` varchar(50) COMMENT '审核版本'
	,`auditVersionID` int COMMENT '审核版本ID'
	,`solveUserID` int COMMENT '指派给的用户ID'
	,`isActive` boolean NOT NULL DEFAULT 1 COMMENT '0-删除, 1-正常'
	,`inDate` datetime(3) NOT NULL COMMENT '写入时间'
	,`inUser` int NOT NULL COMMENT '创建用户ID'
	,`lastEditUser` int COMMENT '更新用户'
	,`lastEditDate` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更改日期'
	,CONSTRAINT PK_Problem_EditInfo_editInfoID PRIMARY KEY (editInfoID)
)engine=InnoDB default charset utf8mb4;
CREATE INDEX IX_Problem_EditInfo_problemID ON Problem_EditInfo(problemID);
