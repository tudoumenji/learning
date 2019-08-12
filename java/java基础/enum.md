```java
package enumcase;

public enum SeasonEnum {
    SPRING("春天"),SUMMER("夏天"),FALL("秋天"),WINTER("冬天");
    
    private final String name; //构造器初始化final值
    
    private SeasonEnum(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

