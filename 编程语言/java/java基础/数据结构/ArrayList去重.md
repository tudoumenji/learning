**ArrayList去重**

 

 

1.利用HashSet（不保证元素顺序一致）

　　HashSet不会存在相同的元素，可以利用这一点去除List中的重复元素

　　　　 List<String> beforeList = new ArrayList<String>();

​        

​        beforeList.add("sun");

​        beforeList.add("star");

​        beforeList.add("moon");

​        beforeList.add("earth");

​        beforeList.add("sun");

​        beforeList.add("earth");

​        

​        Set<String> middleHashSet = new HashSet<String>(beforeList);

​        

​        List<String> afterHashSetList = new ArrayList<String>(middleHashSet);

​        

​        System.out.println(beforeList);

​        System.out.println(afterHashSetList);



但是HashSet不保证顺序，如果要按照原来的顺序，用第二种方法

2.利用LinkedHashSet (去重后顺序一致)

　　　　 List<String> beforeList = new ArrayList<String>();

​        

​        beforeList.add("sun");

​        beforeList.add("star");

​        beforeList.add("moon");

​        beforeList.add("earth");

​        beforeList.add("sun");

​        beforeList.add("earth");

​        

​        Set<String> middleLinkedHashSet = new LinkedHashSet<String>(beforeList);

​        

​        List<String> afterHashSetList = new ArrayList<String>(middleLinkedHashSet);

​        

​        System.out.println(beforeList);

​        System.out.println(afterHashSetList);



去重后元素顺序不变

 
