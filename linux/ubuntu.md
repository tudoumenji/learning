#### 开启open-ssh

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



#### 镜像

ubuntu16.04下载地址：
　中科大源 
      http://mirrors.ustc.edu.cn/ubuntu-releases/16.04/
　阿里云开源镜像站 
      http://mirrors.aliyun.com/ubuntu-releases/16.04/
　兰州大学开源镜像站 
      http://mirror.lzu.edu.cn/ubuntu-releases/16.04/
　北京理工大学开源
      http://mirror.bit.edu.cn/ubuntu-releases/16.04/
　浙江大学 
      http://mirrors.zju.edu.cn/ubuntu-releases/16.04/



#### 下载源

1、原文件备份

sudo cp /etc/apt/sources.list /etc/apt/sources.list.bak

2、编辑源列表文件

sudo vim /etc/apt/sources.list

3、将原来的列表删除，添加如下内容（中科大镜像源）

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

