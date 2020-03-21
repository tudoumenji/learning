1、采集源需要注意编码，确定采集源是不是utf-8，否则会报charset编码错误

2、注意对接hdfs是否开启了kerberos认证，需要配置keytab和krb5

3、注意batchsize要小于transactionCapacity，否则报错

4、如果出现outofmemory错误，在conf/flume-env.sh将内存占用调为1000-2000

5、springboot package的jar包是不可依赖的，应该用.jar.original
