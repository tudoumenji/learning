解决idea中mysql连接失败Could not create connection to database server. Attempted reconnect 3 times. Giving up.

原因是少一个参数，设置时区的。

 解决方法：加一个时区参数

```
serverTimezone=UTC

jdbc:mysql://localhost:3306/SshProject?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
```
