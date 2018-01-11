USE no_problem;

CREATE TABLE IF NOT EXISTS Project_Member (
	`id` int NOT NULL AUTO_INCREMENT COMMENT 'id 主键'
	,`userID` int NOT NULL COMMENT '用户ID'
	,`projectID` int NOT NULL COMMENT '项目ID'
	,`isOwner` boolean NOT NULL COMMENT '0-非项目拥有者,1-项目拥有者'
	,`isAdmin` boolean NOT NULL COMMENT '0-非管理员, 1-管理员'
	,`displayName` varchar(50) COMMENT '项目显示名称'
	,`inUser` int NOT NULL COMMENT '写入用户ID'
	,`inDate` datetime(3) NOT NULL COMMENT '添加时间'
	,`lastEditUser` int COMMENT '编辑者ID'
	,`lastEditDate` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更改日期'
	,`isActive` boolean NOT NULL COMMENT '0-未启用, 1-启用'
	,CONSTRAINT PK_Project_Member_id PRIMARY KEY (id)
)engine=InnoDB default charset utf8mb4;

CREATE INDEX IX_Project_Member_userID ON Project_Member(userID);
CREATE INDEX IX_Project_Member_projectID ON Project_Member(projectID);
