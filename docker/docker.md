注：以下操作一般无问题，不行1、重启docker(命令sudo service docker restart)；2、重启服务器！！（ubuntu命令:reboot）

注：挂起ubuntu服务器后，docker里容器出现外部不能连接的情况，请重启docker

#### Docker镜像加速

通过修改daemon配置文件/etc/docker/daemon.json来使用加速器（阿里云申请）

```
sudo mkdir -p /etc/docker


sudo tee /etc/docker/daemon.json <<-'EOF'

{

  "registry-mirrors": ["https://1qszl3b2.mirror.aliyuncs.com"]

}

EOF


sudo systemctl daemon-reload


sudo systemctl restart docker
```





#### docker命令

```
进入正在运行的容器并以命令行交互：docker exec -it 容器ID /bin/bash
重新进入：docker attach 容器ID（这个命令好像有问题）
退出容器：1、exit容器停止退出；2、ctrl+P+Q容器不停止退出
查看容器详细信息：docker inspect container_id
docker pull mysql 拉取镜像
docker images 查看镜像
docker ps 查看所有运行的容器
docker ps -a 查看所有容器，包括未运行的
docker start 容器id 运行容器
docker stop 容器id 停止容器
docker restart 容器id 重启容器
```



#### mysql

```
docker run -p 12345:3306 --name mysql -v /zzyyuse/mysql/conf:/etc/mysql/conf.d -v /zzyyuse/mysql/logs:/logs -v /zzyyuse/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.6
 
命令说明：
-p 12345:3306：将主机的12345端口映射到docker容器的3306端口。
--name mysql：运行服务名字
-v /zzyyuse/mysql/conf:/etc/mysql/conf.d ：将主机/zzyyuse/mysql录下的conf/my.cnf 挂载到容器的 /etc/mysql/conf.d
-v /zzyyuse/mysql/logs:/logs：将主机/zzyyuse/mysql目录下的 logs 目录挂载到容器的 /logs。
-v /zzyyuse/mysql/data:/var/lib/mysql ：将主机/zzyyuse/mysql目录下的data目录挂载到容器的 /var/lib/mysql 
-e MYSQL_ROOT_PASSWORD=123456：初始化 root 用户的密码。
-d mysql:5.6 : 后台程序运行mysql5.6


docker exec -it MySQL运行成功后的容器ID /bin/bash

注意：查看 mysql.user表，一般会有user：host => root:%,则说明root用户已对外开启远程访问权限，直接开启navicat连接吧！

（默认已开启）开启远程访问权限
grant all privileges on *.* to 'root'@'%' identified by '123456' with grant option;
flush privileges;
```



redis

```
简单开启
$ docker run --name redis容器名字 -p 6379:6379 -d redis

简单开启并持久化
$ docker run --name redis容器名字 -p 6379:6379 -d redis redis-server --appendonly yes
持久化数据在/data里

进入容器,并用redis-cli打开测试
$ docker exec -it redis运行成功后的容器ID /bin/bash
$ redis-cli

用外部redis desktop manager测试
```

