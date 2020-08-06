使用hutool包

```java
@SpringBootApplication
public class Demo03Application {

    static String name;

    @Value("${name}")
    public void setName(String aname) {
        name = aname;
    }

    public static void main(String[] args) throws FileNotFoundException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Demo03Application.class, args);
        System.out.println(name);
        
        Props props = new Props("application.properties");
        String str = props.getStr("name");
        System.out.println(str);

        String s = ResourceUtil.readStr("临时.txt", StandardCharsets.UTF_8);
        System.out.println(s);

        InputStream stream = ResourceUtil.getStream("临时.txt");
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<String> list = IoUtil.readLines(stream, StandardCharsets.UTF_8, lines);
        System.out.println(list);

        SpringApplication.exit(applicationContext, new ExitCodeGenerator() {
            @Override
            public int getExitCode() {
                return 22;
            }
        });
    }
}
```

