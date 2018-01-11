USE no_problem;

CREATE TABLE IF NOT EXISTS Project_TagInfo (
	`tagID` int NOT NULL AUTO_INCREMENT COMMENT 'tagID 主键'
	,`projectID` int NOT NULL COMMENT '项目ID'
	,`tagName` varchar(20) NOT NULL COMMENT 'tag名称'
	,`tagColor` varchar(10) NOT NULL COMMENT 'tag颜色'
	,`isActive` boolean NOT NULL DEFAULT 1 COMMENT '0-删除, 1-正常'
	,`inDate` datetime(3) NOT NULL COMMENT '写入时间'
	,`inUser` int NOT NULL COMMENT '创建用户ID'
	,`lastEditUser` int COMMENT '更新用户'
	,`lastEditDate` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更改日期'
	,CONSTRAINT PK_Project_TagInfo_tagID PRIMARY KEY (tagID)
)engine=InnoDB default charset utf8mb4;

CREATE INDEX IX_Project_TagInfo_projectID ON Project_TagInfo(projectID);
