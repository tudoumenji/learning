map_API

```java
import java.util.HashMap;

public class Test02 {
    public static void main(String[] args) {
        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.put(1, "a");
        hashMap.put(2, "b");
        hashMap.put(3, "c");
        HashMap<Integer, String> hashMap1 = new HashMap<>(hashMap);
        HashMap<Integer, String> hashMap2 = new HashMap<>(hashMap);
        HashMap<Integer, String> hashMap3 = new HashMap<>(hashMap);
        HashMap<Integer, String> hashMap4 = new HashMap<>(hashMap);

        //1.1、简单添加，若k不存在则添加
        hashMap1.putIfAbsent(1, "a1");
        System.out.println(hashMap1);

        //1.2、计算后添加，若存在则替换，不存在则添加
        hashMap2.compute(1, (k, v) -> k + v);
        hashMap2.compute(4, (k, v) -> k + v);
        //计算后添加，若不存在才添加
        hashMap2.computeIfAbsent(5, (k) -> String.valueOf(k));
        //计算后添加，若存在才添加
        hashMap2.computeIfPresent(2, (k, v) -> k + v);
        System.out.println(hashMap2);

        //2.1查找，没找到则赋新值
        String f = hashMap.getOrDefault(6, "f");
        System.out.println("f:" + f);

    }
}

```

