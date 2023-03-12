start slave;
stop slave;
show slave status;
show master status;

set global sql_slave_skip_counter=1;

grant all privileges on *.* to 'mysql'@'%' ;

SELECT * FROM mysql.user;

SHOW GRANTS FOR 'mysql'@'localhost';

select * from mysql.user ;

grant replication slave,replication client on *.* to 'mysql'@'%';

flush privileges;

create database test1;
use test1;
#创建表
create table tom (id int not null,name varchar(100)not null ,age tinyint);
#插入数据
insert tom (id,name,age) values(1,'xxx',20),(2,'yyy',7),(3,'zzz',23);


change master to master_host='192.168.1.103',master_port=3306,master_user='mysql',master_password='zw123',master_log_file='binlog.000146',master_log_pos=2070,get_master_public_key=1;



