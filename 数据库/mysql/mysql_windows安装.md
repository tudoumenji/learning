#### 下载地址（社区版）：https://dev.mysql.com/downloads/     =》  MySQL Community Server（zip安装包）



#### 安装（http://www.runoob.com/mysql/mysql-install.html）：

##### 1、解压安装包

##### 2、安装的配置文件

打开刚刚解压的文件夹 C:\web\mysql-8.0.11 ，在该文件夹下创建 my.ini 配置文件，编辑 my.ini 配置以下基本信息：

```
[client]
# 设置mysql客户端默认字符集
default-character-set=utf8
 
[mysqld]
# 设置3306端口
port = 3306
# 设置mysql的安装目录
basedir=C:\\web\\mysql-8.0.11
# 设置 mysql数据库的数据的存放目录，MySQL 8+ 不需要以下配置，系统自己生成即可，否则有可能报错
# datadir=C:\\web\\sqldata
# 允许最大连接数
max_connections=20
# 服务端使用的字符集默认为8比特编码的latin1字符集
character-set-server=utf8
# 创建新表时将使用的默认存储引擎
default-storage-engine=INNODB
```



##### 3、启动 MySQL 数据库：

以管理员身份打开 cmd 命令行工具，切换目录：

（先输入cd\进入c盘根目录，在输入D:进入D盘）

```
cd C:\web\mysql-8.0.11\bin
```

初始化数据库：

```
mysqld --initialize --console
```

执行完成后，会输出 root 用户的初始默认密码，如：

```
...
2018-04-20T02:35:05.464644Z 5 [Note] [MY-010454] [Server] A temporary password is generated for root@localhost: APWCY5ws&hjQ
...
```

**APWCY5ws&hjQ** 就是初始密码，后续登录需要用到，你也可以在登陆后修改密码。

输入以下安装命令：

```
mysqld install
```

启动输入以下命令即可：

```
net start mysql
```

> 注意: 在 5.7 需要初始化 data 目录：
>
> ```
> cd C:\web\mysql-8.0.11\bin 
> mysqld --initialize-insecure 
> ```
>
> 初始化后再运行 net start mysql 即可启动 mysql。



##### 4、登陆Mysql

当 MySQL 服务已经运行时, 我们可以通过 MySQL 自带的客户端工具登录到 MySQL 数据库中, 首先打开命令提示符, 输入以下格式的命名:

```
mysql -h 主机名 -u 用户名 -p
```

参数说明：

- **-h** : 指定客户端所要登录的 MySQL 主机名, 登录本机(localhost 或 127.0.0.1)该参数可以省略;
- **-u** : 登录的用户名;
- **-p** : 告诉服务器将会使用一个密码来登录, 如果所要登录的用户名密码为空, 可以忽略此选项。

##### 5、修改密码

先登录

```
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '你的新密码'

```

