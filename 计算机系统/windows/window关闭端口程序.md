window关闭端口程序
1. netstat -nao | findstr “8080” 查询8080端口 
2. taskkill /pid 3017 /F 关闭pid为3017的进程 （找到pid后可直接调任务管理器看看什么进程在使用）