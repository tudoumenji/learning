##### 1、直接启动

nohup java -jar /xxx/xxx/xxx.jar >/dev/null 2>&1 &

关键在于最后的 >/dev/null 2>&1 部分，/dev/null是一个虚拟的空设备（类似物理中的黑洞），任何输出信息被重定向到该设备后，将会石沉大海

\>/dev/null 表示将标准输出信息重定向到"黑洞"

2>&1 表示将标准错误重定向到标准输出(由于标准输出已经定向到“黑洞”了，即：标准输出此时也是"黑洞"，再将标准错误输出定向到标准输出，相当于错误输出也被定向至“黑洞”)



##### 2、pm2启动

pm2 start java  --no-autorestart  -- -jar /data/mainTest/demo03-0.0.1-SNAPSHOT.jar  

--no-autorestart：不会自动重启