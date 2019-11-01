```java
public class Test11 {
    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1,2);
        map.put(2,2);
        map.put(3,3);
        //取出map所有v的集合
        Collection<Integer> values = map.values();
        System.out.println(values);
        //根据条件删除制定的v，原来map中含有v的entry自动删除
        //注：remove只删除第一个符合条件的元素，removeIf删除所有符合条件的元素
        values.removeIf(o->o==2);
        System.out.println(values);
        System.out.println(map);
    }
}


输出结果：
[2, 2, 3]
[3]
{3=3}
```

1
