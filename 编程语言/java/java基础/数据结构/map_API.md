```java
public class MapAPITest01 {
    public static void main(String[] args) {
        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.put(1, "a");
        hashMap.put(2, "b");
        hashMap.put(3, "c");
        HashMap<Integer, String> hashMap1 = new HashMap<>(hashMap);
        HashMap<Integer, String> hashMap2 = new HashMap<>(hashMap);
        HashMap<Integer, String> hashMap3 = new HashMap<>(hashMap);
        HashMap<Integer, String> hashMap4 = new HashMap<>(hashMap);
        HashMap<Integer, String> hashMap5 = new HashMap<>(hashMap);
        HashMap<Integer, String> hashMap6 = new HashMap<>(hashMap);
        HashMap<Integer, String> hashMap7 = new HashMap<>(hashMap);


        //1.1、简单添加，若k不存在则添加
        hashMap1.putIfAbsent(1, "a1");
        System.out.println("hashMap1:" + hashMap1);
        //1.2简单添加，添加map，根据k去重
        hashMap1.putAll(hashMap1);
        System.out.println("hashMap1:" + hashMap1);

        //1.2、计算后添加，若存在则替换，不存在则添加
        hashMap2.compute(1, (k, v) -> k + v);
        hashMap2.compute(4, (k, v) -> k + v);
        //计算后添加，若不存在才添加
        hashMap2.computeIfAbsent(5, (k) -> String.valueOf(k));
        //计算后添加，若存在才添加
        hashMap2.computeIfPresent(2, (k, v) -> k + v);
        System.out.println("hashMap2:" + hashMap2);

        //1.3.1 存在k则替换
        hashMap3.replace(1, "d");
        //1.3.2 存在指定的k和v后替换新的v
        hashMap3.replace(2, "b", "b1");
        System.out.println("hashMap3:" + hashMap3);
        hashMap3.replaceAll((k, v) -> k + v);
        System.out.println("hashMap3:" + hashMap3);

        //1.4.1 合并算法,若计算值不为空，则将计算值替换老值
        hashMap4.merge(1, "new value", (oldValue, newValue) -> oldValue + newValue);
        hashMap4.merge(4, "new value", (oldValue, newValue) -> oldValue + newValue);
        //1.4.1 合并算法,若计算值为空，则删除此k
        hashMap4.merge(2, "new value", (oldValue, newValue) -> null);
        System.out.println("hashMap4:" + hashMap4);

        //2.1查找，没找到则赋新值
        String f = hashMap5.getOrDefault(6, "f");
        System.out.println("f:" + f);

        //3.1 删除指定k和指定kv
        hashMap6.remove(1);
        hashMap6.remove(1, "a");
        System.out.println("hashMap6:" + hashMap6);
        //3.2 根据条件删除指定k：先获取所有k，然后删除指定k
        hashMap6.keySet().removeIf(k -> 2 == k);
        System.out.println("hashMap6:" + hashMap6);
        //3.3 根据条件删除指定v：先获取所有v，然后删除指定v
        //(注：removeIf为删除所有指定条件，remove只删除第一个匹配条件)
        hashMap6.values().removeIf(v -> "c".equals(v));
        System.out.println("hashMap6:" + hashMap6);

        //4 遍历map
        hashMap7.forEach((k, v) -> System.out.println("k:" + k + ",v:" + v));
        //5 是否包含
        boolean a = hashMap7.containsKey(1);
        boolean b = hashMap7.containsValue("a");
        //6.1 取出所有key，集合不可重复
        System.out.println("keySet:" + hashMap7.keySet());
        //6.1 取出所有value,集合可重复
        System.out.println("values:" + hashMap7.values());
    }
}


输出：
hashMap1:{1=a, 2=b, 3=c}
hashMap1:{1=a, 2=b, 3=c}
hashMap2:{1=1a, 2=2b, 3=c, 4=4null, 5=5}
hashMap3:{1=d, 2=b1, 3=c}
hashMap3:{1=1d, 2=2b1, 3=3c}
hashMap4:{1=anew value, 3=c, 4=new value}
f:f
hashMap6:{2=b, 3=c}
hashMap6:{3=c}
hashMap6:{}
k:1,v:a
k:2,v:b
k:3,v:c
keySet:[1, 2, 3]
values:[a, b, c]

```

1
