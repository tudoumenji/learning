https://blog.csdn.net/yy1098029419/article/details/89452380



parallelStream向下并行处理，遇到distinct等会暂停，然后继续向下并行



常见分组处理

```java
void contextLoads() {
        ArrayList<String> list = new ArrayList<>();
        //分区并行处理
        List<String> collect = Lists.partition(list, 200).parallelStream()
                .flatMap(smallList -> process(smallList))
                .collect(Collectors.toList());
    }

    public Stream<String> process(List<String> smallList) {
        return smallList.stream();
    }
```



并行流与串行流的转换

```java
//list.并行.转串行.转并行 {注意：此种转换会默认以最后一种流类型为准}
list.parallelStream().sequential().parallel();
```



若想真正转换并行和串行，需要collectingAndThen

```java
    void test05() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        List<Integer> collect = list.parallelStream()
                .map(a -> tran(a)).collect(Collectors.collectingAndThen(Collectors.toList(), a -> a.stream().map(b -> tran2(b)).collect(Collectors.toList())));
    }
```

