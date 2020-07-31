```java
// java.nio.file.Path
// java.nio.file.Files
@Test
    void test03() throws Exception {
        Path path = Paths.get("D:\\1.txt");
        Stream<String> lines = Files.lines(path);
        lines.forEach(System.out::println);
    }
```

