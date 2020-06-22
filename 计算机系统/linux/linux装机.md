1 安装系统时配置网络

 

1 修改ip

$ sudo vim /etc/sysconfig/network-scripts/ifcfg-ens33

 

BOOTPROTO=static

ONBOOT=yes

IPADDR=192.168.177.10

NETMASK=255.255.255.0

GATEWAY=192.168.177.1

 

 

2 下载必要软件

yum install net-tools（安装ifconfig）

yum install vim

yum -y install yum-utils

 

3 安装docker

设置 Docker 镜像仓库

$ sudo yum install -y yum-utils device-mapper-persistent-data lvm2

$ sudo yum-config-manager --add-repo 

  <https://download.docker.com/linux/centos/docker-ce.repo>

安装 DOCKER CE

$ sudo yum makecache fast

$ sudo yum install docker-ce

启动docker

$ sudo systemctl start docker

验证docker

sudo docker run hello-world

Docker镜像加速

\2. 配置镜像加速器

针对Docker客户端版本大于 1.10.0 的用户

您可以通过修改daemon配置文件/etc/docker/daemon.json来使用加速器

sudo mkdir -p /etc/docker

sudo tee /etc/docker/daemon.json <<-'EOF'

{

  "registry-mirrors": ["https://1qszl3b2.mirror.aliyuncs.com"]

}

EOF

sudo systemctl daemon-reload

sudo systemctl restart docker

 

 

 

 

###  

### **1.1.1.** **修改静态IP**

1.在windows上执行ipconfig查看vmnet8的ip

2.在虚拟机中执行vim命令修改ip,不是在xshell中执行

修改ip地址与vmnet8在同一个网络中，命令如下，先拷贝下面的命令，

 

vim /etc/sysconfig/network-scripts/ifcfg-ens33

 

然后单击vmware的编辑菜单à粘贴 拷贝命令到虚拟机中执行，如图-130 所示。

 

![img](file:///C:\Users\y1207\AppData\Local\Temp\ksohtml520\wps1.jpg) 

图- 130

修改倒数第四行的ipAddr, 如图-131 所示。

![img](file:///C:\Users\y1207\AppData\Local\Temp\ksohtml520\wps2.jpg) 

图- 131

 

只修改IPADDR,按esc进入命令模式，输入:wq,注意:wq显示在屏幕最下面。

3.执行下面的命令重启网络服务，让ip地址生效

 

service network restart

 

### **1.1.2.** **关闭防火墙**

systemctl stop firewalld.service			#关闭防火墙服务

systemctl disable firewalld.service		#禁止防火墙开启启动

firewall-cmd   --state		#检查防火墙状态

### **1.1.3.** **用xshell连接上虚拟机** 

## **1.1.** **安装****docker**

 