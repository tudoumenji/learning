### 26。1日志格式

Spring Boot的默认日志输出类似于以下示例：

```
2014-03-05 10:57:51.112  INFO 45469 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet Engine: Apache Tomcat/7.0.52
2014-03-05 10:57:51.253  INFO 45469 --- [ost-startStop-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2014-03-05 10:57:51.253  INFO 45469 --- [ost-startStop-1] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1358 ms
2014-03-05 10:57:51.698  INFO 45469 --- [ost-startStop-1] o.s.b.c.e.ServletRegistrationBean        : Mapping servlet: 'dispatcherServlet' to [/]
2014-03-05 10:57:51.702  INFO 45469 --- [ost-startStop-1] o.s.b.c.embedded.FilterRegistrationBean  : Mapping filter: 'hiddenHttpMethodFilter' to: [/*]
```

输出以下项目：

- 日期和时间：毫秒精度，易于排序。
- 日志级别：`ERROR`，`WARN`，`INFO`，`DEBUG`或`TRACE`。
- 进程ID。
- 一个`---`分隔符，用于区分实际日志消息的开头。
- 线程名称：括在方括号中（可能会截断控制台输出）。
- 记录器名称：这通常是源类名称（通常缩写）。
- 日志消息。

 Logback没有`FATAL`级别。它映射到`ERROR`。         

### 26.3文件输出

默认情况下，Spring Boot仅记录到控制台，不会写入日志文件。如果除了控制台输出之外还要编写日志文件，则需要设置`logging.file`或`logging.path`属性（例如，在`application.properties`中）。

下表显示了`logging.*`属性如何一起使用：



**表26.1。记录属性**

| `logging.file` | `logging.path` | 例         | 描述                                                         |
| -------------- | -------------- | ---------- | ------------------------------------------------------------ |
| *（没有）*     | *（没有）*     |            | 仅控制台记录。                                               |
| 具体文件       | *（没有）*     | `my.log`   | 写入指定的日志文件。名称可以是精确位置或相对于当前目录。     |
| *（没有）*     | 具体目录       | `/var/log` | 将`spring.log`写入指定的目录。名称可以是精确位置或相对于当前目录。 |

日志文件在达到10 MB时会轮换，与控制台输出一样，默认情况下会记录`ERROR` - 级别，`WARN` - 级别和`INFO`级别的消息。可以使用`logging.file.max-size`属性更改大小限制。除非已设置`logging.file.max-history`属性，否则以前轮换的文件将无限期归档。

### 26。4日志级别

所有受支持的日志记录系统都可以使用`logging.level.<logger-name>=<level>`在Spring `Environment`中设置记录器级别（例如，在`application.properties`中），其中`level`是TRACE，DEBUG，INFO之一，警告，错误，致命或关闭。可以使用`logging.level.root`配置`root`记录器。

以下示例显示`application.properties`中的潜在日志记录设置：

```
logging.level.root=WARN
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate=ERROR
```



### 日志输出

```
#相对路径 ：项目顶层文件夹
# 直接填充指定log
logging.file=hijklmn/my.log
# 新生成log，默认spring.log
logging.path=abcdefg/log
```



### 新建日志对象

```
private final Log logger = LogFactory.getLog(this.getClass());
```





### Lombok

You put the variant of `@Log` on your class (whichever one applies to the logging system you use); you then have a static final `log` field, initialized to the name of your class, which you can then use to write log statements.

There are several choices available:

- `@CommonsLog`

  Creates `private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(LogExample.class);`

- `@Flogger`

  Creates `private static final com.google.common.flogger.FluentLogger log = com.google.common.flogger.FluentLogger.forEnclosingClass();`

- `@JBossLog`

  Creates `private static final org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(LogExample.class);`

- `@Log`

  Creates `private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(LogExample.class.getName());`

- `@Log4j`

  Creates `private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LogExample.class);`

- `@Log4j2`

  Creates `private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(LogExample.class);`

- `@Slf4j`

  Creates `private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class);`

- `@XSlf4j`

  Creates `private static final org.slf4j.ext.XLogger log = org.slf4j.ext.XLoggerFactory.getXLogger(LogExample.class);`



By default, the topic (or name) of the logger will be the class name of the class annotated with the `@Log` annotation. This can be customised by specifying the `topic` parameter. For example: `@XSlf4j(topic="reporting")`.
