新建数据库：

字符集：

```
数据库中的排序规则用来定义字符在进行排序和比较的时候的一种规则。常见的如下：
（1） utf8_general_ci 不区分大小写，utf8_general_cs 区分大小写
（2） utf8_bin 规定每个字符串用二进制编码存储，区分大小写，可以直接存储二进制的内容

所以一般字符集用utf-8，排序规则用utf8_general_ci
```
**MySQL 8.0 以上版本的数据库连接有所不同：**

- 1、MySQL 8.0 以上版本驱动包版本 [mysql-connector-java-8.0.16.jar](https://static.runoob.com/download/mysql-connector-java-8.0.16.jar)。
- 2、**com.mysql.jdbc.Driver** 更换为 **com.mysql.cj.jdbc.Driver**。
- MySQL 8.0 以上版本不需要建立 SSL 连接的，需要显示关闭。
- 最后还需要设置 CST。

加载驱动与连接数据库方式如下：

```
Class.forName("com.mysql.cj.jdbc.Driver");
conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_demo?useSSL=false&serverTimezone=UTC","root","password");
```

1
