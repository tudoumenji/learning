https://blog.csdn.net/hry2015/article/details/72353994



## @Value注入

### 不通过配置文件的注入属性的情况

通过@Value将外部的值动态注入到Bean中，使用的情况有：

- 注入普通字符串

- 注入操作系统属性

- 注入表达式结果

- 注入其他Bean属性：注入beanInject对象的属性another

- 注入文件资源

- 注入URL资源

  详细代码见：

```
    @Value("normal")
    private String normal; // 注入普通字符串

    @Value("#{systemProperties['os.name']}")
    private String systemPropertiesName; // 注入操作系统属性

    @Value("#{ T(java.lang.Math).random() * 100.0 }")
    private double randomNumber; //注入表达式结果

    @Value("#{beanInject.another}")
    private String fromAnotherBean; // 注入其他Bean属性：注入beanInject对象的属性another，类具体定义见下面

    @Value("classpath:com/hry/spring/configinject/config.txt")
    private Resource resourceFile; // 注入文件资源

    @Value("http://www.baidu.com")
    private Resource testUrl; // 注入URL资源1234567891011121314151617
```

注入其他Bean属性：注入beanInject对象的属性another

```
@Component
public class BeanInject {
    @Value("其他Bean的属性")
    private String another;

    public String getAnother() {
        return another;
    }

    public void setAnother(String another) {
        this.another = another;
    }
}
1234567891011121314
```

注入文件资源：com/hry/spring/configinject/config.txt

```
test configuration file1
```

测试类：

```
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class ConfiginjectApplicationTest {
    @Autowired
    private BaseValueInject baseValueInject;

    @Test
    public void baseValueInject(){
        System.out.println(baseValueInject.toString());
    }
}
1234567891011121314151617181920
```

运行测试类

```
normal=normal
systemPropertiesName=Windows 10
randomNumber=35.10603794922444
fromAnotherBean=其他Bean的属性
resourceFile=test configuration file
testUrl=<html>...<title>百度一下，你就知道</title>...略</html>123456
```

### 通过配置文件的注入属性的情况

通过@Value将外部配置文件的值动态注入到Bean中。配置文件主要有两类：

- application.properties。application.properties在spring boot启动时默认加载此文件
- 自定义属性文件。自定义属性文件通过@PropertySource加载。@PropertySource可以同时加载多个文件，也可以加载单个文件。如果相同第一个属性文件和第二属性文件存在相同key，则最后一个属性文件里的key启作用。加载文件的路径也可以配置变量，如下文的${anotherfile.configinject}，此值定义在第一个属性文件config.properties

第一个属性文件config.properties内容如下：
${anotherfile.configinject}作为第二个属性文件加载路径的变量值

```
book.name=bookName
anotherfile.configinject=placeholder12
```

第二个属性文件config_placeholder.properties内容如下：

```
book.name.placeholder=bookNamePlaceholder1
```

下面通过@Value(“${app.name}”)语法将属性文件的值注入bean属性值，详细代码见：

```
@Component
// 引入外部配置文件组：${app.configinject}的值来自config.properties。
// 如果相同
@PropertySource({"classpath:com/hry/spring/configinject/config.properties",
    "classpath:com/hry/spring/configinject/config_${anotherfile.configinject}.properties"})
public class ConfigurationFileInject{
    @Value("${app.name}")
    private String appName; // 这里的值来自application.properties，spring boot启动时默认加载此文件

    @Value("${book.name}")
    private String bookName; // 注入第一个配置外部文件属性

    @Value("${book.name.placeholder}")
    private String bookNamePlaceholder; // 注入第二个配置外部文件属性

    @Autowired
    private Environment env;  // 注入环境变量对象，存储注入的属性值

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("bookName=").append(bookName).append("\r\n")
        .append("bookNamePlaceholder=").append(bookNamePlaceholder).append("\r\n")
        .append("appName=").append(appName).append("\r\n")
        .append("env=").append(env).append("\r\n")
        // 从eniroment中获取属性值
        .append("env=").append(env.getProperty("book.name.placeholder")).append("\r\n");
        return sb.toString();
    }   
}1234567891011121314151617181920212223242526272829
```

测试代码：
Application.java同上文

```
@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class ConfiginjectApplicationTest {
    @Autowired
    private ConfigurationFileInject configurationFileInject;

    @Test
    public void configurationFileInject(){
        System.out.println(configurationFileInject.toString());
    }
}123456789101112
```

测试运行结果：

```
bookName=bookName
bookNamePlaceholder=bookNamePlaceholder
appName=appName
env=StandardEnvironment {activeProfiles=[], defaultProfiles=[default], propertySources=[Inlined Test Properties,systemProperties,systemEnvironment,random,applicationConfig: [classpath:/application.properties],class path resource [com/hry/spring/configinject/config_placeholder.properties],class path resource [com/hry/spring/configinject/config.properties]]}
env=bookNamePlaceholder
123456
```

## 代码