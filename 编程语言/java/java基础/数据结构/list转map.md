https://blog.csdn.net/Hatsune_Miku_/article/details/73435580

list转map

```
public class Test01 {
    public static void main(String[] args) {
        //构造list
        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student(1,"zhangsan"));
        list.add(new Student(1,"zhangsan2"));
        list.add(new Student(2,"lisi"));
        list.add(new Student(3,"wangwu"));
        //list转map
        HashMap<Integer, String> hashMap =
                list.stream().sorted(Comparator.comparing(Student::getId).reversed()). //逆序
                        collect(Collectors.toMap(
                                Student::getId,//key
                                Student::getName,//value
                                (oldValue, newValue) -> oldValue,//相同key取老值
                                LinkedHashMap::new));//有序map接收
        System.out.println(hashMap);
    }
}

输出：{3=wangwu, 2=lisi, 1=zhangsan}
```

