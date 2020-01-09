map flapmap

映射成新的optional

```java
public class IOTest {
    public static void main(String[] args) {
        Student student = new Student(1, "zhangsan");
        Student student2 = null;
        Optional<Integer> integer = Optional.ofNullable(student2).map(student1 -> student1.getId());
        Optional<Integer> o = Optional.ofNullable(student2).flatMap(student1 -> Optional.ofNullable(student.getId()));
        System.out.println(integer);
        System.out.println(o);
    }
}
```



