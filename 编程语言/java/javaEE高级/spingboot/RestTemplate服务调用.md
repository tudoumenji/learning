<https://juejin.im/post/5b717eab6fb9a0096e21cc24>



## 34.使用`RestTemplate`调用REST服务

如果需要从应用程序调用远程REST服务，可以使用Spring Framework的[`RestTemplate`](https://docs.spring.io/spring/docs/5.1.3.RELEASE/javadoc-api/org/springframework/web/client/RestTemplate.html)类。由于`RestTemplate`实例在使用之前通常需要进行自定义，因此Spring Boot不提供任何单个自动配置`RestTemplate` bean。但是，它会自动配置`RestTemplateBuilder`，可在需要时用于创建`RestTemplate`实例。自动配置的`RestTemplateBuilder`确保将合理的`HttpMessageConverters`应用于`RestTemplate`实例。

以下代码显示了一个典型示例：

```java
@Service
public class MyService {

	private final RestTemplate restTemplate;

	public MyService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public Details someRestCall(String name) {
		return this.restTemplate.getForObject("/{name}/details", Details.class, name);
	}

}
```









```java
    //注入方式比较特别，也可以new一个
    private final RestTemplate restTemplate;

    public controller(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping("/test3")
    @ResponseBody
    public ResponseEntity<String> test3() {
        RestTemplate restTemplate1 = new RestTemplate();
        //url返回的是ResponseEntity<Student>格式，可以用String接收，也可以直接用Student.class接收
        String url = "http://localhost:8080/getStudentByPathstring?name=hahaa";
        String s = restTemplate1.getForObject(url, String.class);//String接收
        return ResponseEntity.ok(s);
    }

    @GetMapping("/test4")
    @ResponseBody
    public ResponseEntity<Student> test4() {
        //url返回的是ResponseEntity<T>格式，可以用String接收，也可以直接用Student.class接收
        String url = "http://localhost:8080/getStudentByPathstring?name=hahaa";
        Student s = restTemplate.getForObject(url, Student.class);//Student.class接收
        return ResponseEntity.ok(s);
    }
```

