https://blog.csdn.net/mononoke111/article/details/81088472



```java
@SpringBootApplication
public class Demo03Application {

    static String name;
    @Value("${name}")
    public void setName(String aname){
        name =aname;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Demo03Application.class, args);
        System.out.println(name);
        SpringApplication.exit(applicationContext, new ExitCodeGenerator() {
            @Override
            public int getExitCode() {
                return 22;
            }
        });
    }
}
```

