https://blog.csdn.net/yuhui123999/article/details/80593750

首先需要进到自己springboot项目的根目录，然后执行如下linux命令

nohup java -jar 自己的springboot项目.jar >日志文件名.log 2>&1 &

命令详解：

nohup：不挂断地运行命令，退出帐户之后继续运行相应的进程。

> 日志文件名.log：是nohup把command的输出重定向到当前目录的指定的“日志文件名.log”文件中，即输出内容不打印到屏幕上，而是输出到”日志文件名.log”文件中。不指定文件名会在当前目录创建nohup.out，如果当前目录的 nohup.out 文件不可写，输出重定向到 $HOME/nohup.out 文件中。如果没有文件能创建或打开以用于追加，那么 Command 参数指定的命令不可调用。

2>&1：2就是标准错误，1是标准输出，该命令相当于把标准错误重定向到标准输出么。这里&相当于标准错误等效于标准输出，即把标准错误和标准输出同时输出到指定的“日志文件名.log”文件中。

java -jar 自己的springboot项目.jar：执行springboot的项目，如果单单只执行该命令，linux只会短暂的运行该项目，当退出控制台后会自动关闭该项目。

**最后的&：让该作业在后台运行。**