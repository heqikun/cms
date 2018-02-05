后台管理demo

1.创建数据库
CREATE DATABASE cms;
2.使用cms数据库
use cms;
3.创建数据表
CREATE TABLE user(
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50),
	username VARCHAR(50),
	password VARCHAR(50)
);