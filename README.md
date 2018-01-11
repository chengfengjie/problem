# no-problem BUG管理系统

此项目是一个BUG管理系统，提供了基本的bug管理功能，基本功能如下:

- 1、项目基本信息维护 (创建项目、修改项目信息、项目成员管理)
- 2、用户管理
- 3、BUG维护（提交BUG,修复BUG,BUG指派，审核等功能）
- 4、BUG统计

#### 项目配置:  
- 1、创建数据库, 数据库名称为 `no_problem`
- 2、执行db-design/sql下的脚本创建数据表  
- 3、在backend/src/main/resources/application-pro.yml中设置项目相关配置，配置如下   

```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/no_problem?characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true&useSSL=false //数据库连接url
    username: npdbo //数据库账号
    password: 123 //数据库密码
aliyun:
  oss:
    displayUrl:阿里云图片显示url，注意:不要以"/"结尾
    accessKey:阿里云OSS Accesskey
    secret:阿里云OSS AccessSecret
    endPoint: 阿里云OSS endPoint
config:
  openRegister: false //是否开启用户自己注册创建项目
```

配置解释:  
`config.openRegister`: 如果开启，用户可以自己注册账号，创建项目并且管理自己的项目。如果为false, 则只有超级管理员具有创建项目和管理成员的功能，所有成员的账号都需要超级管理员创建。


#### 项目部署:
- 1、安装mysql 5.7+的版本
- 2、源代码通过mvn管理，可clone源代码自行build项目运行
