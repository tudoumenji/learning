



```java
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String getHello() throws IOException {
        //PropertySourceLoader => PropertiesPropertySourceLoader || YamlPropertySourceLoader
        PropertySourceLoader loader = new PropertiesPropertySourceLoader();
        List<PropertySource<?>> load = loader.load("application", new ClassPathResource("application.properties"));
        for (PropertySource<?> source : load) {
            Object dog = source.getProperty("dog");
            System.out.println(dog);
        }
        return "hello";
    }
}
```

