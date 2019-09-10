# springboot中避免Thymeleaf对html语法检查过严

0.1612017.03.10 15:04:54字数 125阅读 2940

> 最近在使用Thymeleaf的时候发现Thymeleaf对html语法的检查简直到了丧心病狂的地步，各种需要转义和闭合。实在没办法忍受，于是上网搜了下，找到一个办法，分享下。

1. 首先在application.properties中修改spring.thymeleaf.mode属性：
   `spring.thymeleaf.mode=LEGACYHTML5`
2. 在maven配置文件pom.xml中加入以下依赖：

```xml
<dependency>
    <groupId>net.sourceforge.nekohtml</groupId>
    <artifactId>nekohtml</artifactId>
    <version>1.9.21</version>
</dependency>
```

OK，这下不用刻意关注标签闭合或者字符转义了。
