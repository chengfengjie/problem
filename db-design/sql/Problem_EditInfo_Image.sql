USE no_problem;
CREATE TABLE IF NOT EXISTS Problem_EditInfo_Image (
	`id` int NOT NULL AUTO_INCREMENT COMMENT '主键'
	,`editInfoID` int NOT NULL COMMENT '修改记录ID'
	,`imageName` varchar(200) NOT NULL COMMENT '图片url路径'
	,`sortIndex` int NOT NULL DEFAULT 0 COMMENT '排序'
	,`isActive` boolean NOT NULL DEFAULT 1 COMMENT '0-删除, 1-正常'
	,`inDate` datetime(3) NOT NULL COMMENT '写入时间'
	,`inUser` int NOT NULL COMMENT '创建用户ID'
	,`lastEditUser` int COMMENT '更新用户'
	,`lastEditDate` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更改日期'
	,CONSTRAINT PK_Problem_EditInfo_Image_id PRIMARY KEY (id)
)engine=InnoDB default charset utf8mb4;
CREATE INDEX IX_Problem_EditInfo_Image_editInfoID ON Problem_EditInfo_Image(editInfoID);
