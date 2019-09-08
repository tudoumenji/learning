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


docker exec -it MySQL运行成功后的容器ID     /bin/bash
```

