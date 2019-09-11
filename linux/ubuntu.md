#### 安装事项

1、选择镜像（安装时会下载部分必须软件和更新，从官方给的地址下载太慢）

```
163镜像： http://mirrors.163.com/ubuntu/
```

2、安装时注意选择安装open-ssh（忘了装，可以看下面）

#### 切换root用户

su root命令

（若还未设置root密码，会显示没有权限：

运行  sudo passwd root

终端会显示：Enter new UNIX password: 

设置root用户密码，按Enter）



#### 开启open-ssh（ssh连不上时请选择）

安装完成之后，如果你直接连接[Ubuntu](http://www.linuxidc.com/topicnews.aspx?tid=2)主机会发现连接不上，这是因为Ubuntu主机没有开启SSH服务，需要开启openssh-server：

```
root@ubuntu:~# sudo apt-get install openssh-server
```

使用

```
root@ubuntu:~# ps -e | grep ssh
```

如果只有ssh-agent表示还没启动，需要

```
root@ubuntu:~# /etc/init.d/ssh start
```

如果显示sshd则说明已启动成功。



#### 配置阿里下载源

1、原文件备份

sudo cp /etc/apt/sources.list /etc/apt/sources.list.bak

2、编辑源列表文件

sudo vim /etc/apt/sources.list

3、将原来的列表删除，添加如下内容（阿里镜像源）

dd命令可以删除整行

```
deb http://mirrors.aliyun.com/ubuntu/ trusty main restricted universe multiverse  
deb http://mirrors.aliyun.com/ubuntu/ trusty-security main restricted universe multiverse 
deb http://mirrors.aliyun.com/ubuntu/ trusty-updates main restricted universe multiverse 
deb http://mirrors.aliyun.com/ubuntu/ trusty-proposed main restricted universe multiverse 
deb http://mirrors.aliyun.com/ubuntu/ trusty-backports main restricted universe multiverse  
deb-src http://mirrors.aliyun.com/ubuntu/ trusty main restricted universe multiverse  
deb-src http://mirrors.aliyun.com/ubuntu/ trusty-security main restricted universe multiverse  
deb-src http://mirrors.aliyun.com/ubuntu/ trusty-updates main restricted universe multiverse  
deb-src http://mirrors.aliyun.com/ubuntu/ trusty-proposed main restricted universe multiverse  
deb-src http://mirrors.aliyun.com/ubuntu/ trusty-backports main restricted universe multiverse 
```

4、运行sudo apt-get update
————————————————
版权声明：本文为CSDN博主「zgljl2012」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/zgljl2012/article/details/79065174

