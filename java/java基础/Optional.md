Stream中处理空指针

```java
List<Integer> list4 = list.stream()
    .map(stu -> Optional.ofNullable(stu.id).orElse(0))
    .collect(Collectors.toList());
```

