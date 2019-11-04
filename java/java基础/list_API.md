list_API



```java
public class ListAPITest01 {
    public static void main(String[] args) {
        ArrayList<Student> list = new ArrayList<>();
        ArrayList<Student> sList1 = new ArrayList<>();
        ArrayList<Student> sList2 = new ArrayList<>();
        list.add(new Student(1, "a"));
        list.add(new Student(2, "b"));
        list.add(new Student(3, "c"));
        sList1.add(new Student(1, "a"));
        sList1.add(new Student(2, "b"));
        sList2.add(new Student(2, "b"));
        sList2.add(new Student(3, "c"));
        ArrayList<Student> list1 = new ArrayList<>(list);
        ArrayList<Student> list2 = new ArrayList<>(list);
        ArrayList<Student> list3 = new ArrayList<>(list);
        ArrayList<Student> list4 = new ArrayList<>(list);
        ArrayList<Student> list5 = new ArrayList<>(list);
        ArrayList<Student> list6 = new ArrayList<>(list);
        ArrayList<Student> list7 = new ArrayList<>(list);

        //1.1 增加单个
        list1.add(0, new Student(4, "d"));
        //1.2 增加集合
        list1.addAll(1, list);
        System.out.println("list1:" + list1);

        //2.1删除，removeIf为删除全部符合条件的元素，remove为删除第一个
        list2.removeIf(s -> "a".equals(s.getName()));
        System.out.println("list2:" + list2);
        //2.2 本集合，删除参数集合中本集合存在的元素
        list2.removeAll(sList1);
        System.out.println("list2:" + list2);
        //2.2 本集合，保留指定集合中本集合存在的元素
        list2.retainAll(sList2);
        System.out.println("list2:" + list2);


        //3.1替换所有
        list3.replaceAll(s -> new Student(4, "d"));
        System.out.println("list3:" + list3);

        //4.1 查找第一个
        list4.add(new Student(1, "a"));
        int a = list4.indexOf(new Student(1, "a"));
        System.out.println("a:" + a);
        //4.2查找最后一个
        int b = list4.lastIndexOf(new Student(1, "a"));
        System.out.println("b:" + b);

        //5 截取成新集合
        List<Student> newList5 = list5.subList(1, 2);
        System.out.println("list5:" + list5);
        System.out.println("newList5:" + newList5);

        //6 使用集合转数组的方法，必须使用集合的toArray(T[] array)，
        // 传入的是类型完全一致、长度为0的空数组(数组长度为0时，动态创建与size相同的数组，性能最好)。
        Student[] list6ToArray = list6.toArray(new Student[0]);
        System.out.println("list6ToArray:" + Arrays.toString(list6ToArray));

        //7 排序
        list7.sort(Comparator.comparing(Student::getName).reversed());
        System.out.println("list7:" + list7);

        //8 将数组容量压缩到元素个数大小，节省内存空间
        list7.trimToSize();

        //9.1是否包含单个 list7.contains(Object o);
        //9.2是否包含集合 list7.containsAll(Collection<?> c)
    }
}


输出：
list1:[Student(id=4, name=d), Student(id=1, name=a), Student(id=2, name=b), Student(id=3, name=c), Student(id=1, name=a), Student(id=2, name=b), Student(id=3, name=c)]
list2:[Student(id=2, name=b), Student(id=3, name=c)]
list2:[Student(id=3, name=c)]
list2:[Student(id=3, name=c)]
list3:[Student(id=4, name=d), Student(id=4, name=d), Student(id=4, name=d)]
a:0
b:3
list5:[Student(id=1, name=a), Student(id=2, name=b), Student(id=3, name=c)]
newList5:[Student(id=2, name=b)]
list6ToArray:[Student(id=1, name=a), Student(id=2, name=b), Student(id=3, name=c)]
list7:[Student(id=3, name=c), Student(id=2, name=b), Student(id=1, name=a)]
```

2
