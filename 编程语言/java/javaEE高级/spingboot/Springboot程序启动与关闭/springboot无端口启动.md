https://www.cnblogs.com/chywx/p/11234527.html



```java
 public static void main(String[] args) {
        new SpringApplicationBuilder(Application .class)
                .web(WebApplicationType.NONE) // .REACTIVE, .SERVLET
                .run(args);
    }
```

