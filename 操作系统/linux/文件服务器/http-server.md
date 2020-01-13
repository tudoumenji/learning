### 1 下载`node.js`

官网地址： [https://nodejs.org](https://nodejs.org/)
```undefined
sudo yum -y install nodejs
```

下载完成后在命令行输入命令`$ node -v`以及`$ npm -v`检查版本，确认是否安装成功。

### 2 下载`http-server`

在终端输入：
`$ npm install http-server -g`

### 3 开启 `http-server`服务

终端进入目标文件夹，然后在终端输入：

```
$ http-server -c-1   （⚠️只输入http-server的话，更新了代码后，页面不会同步更新）


Starting up http-server, serving ./
Available on:
  http://127.0.0.1:8080
  http://192.168.8.196:8080
Hit CTRL-C to stop the server
```

### 4 关闭 `http-server`服务

按快捷键`CTRL-C`
终端显示`^Chttp-server stopped.`即关闭服务成功。