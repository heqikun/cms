��̨����demo

1.�������ݿ�
CREATE DATABASE cms;
2.ʹ��cms���ݿ�
use cms;
3.�������ݱ�
CREATE TABLE user(
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50),
	username VARCHAR(50),
	password VARCHAR(50)
);