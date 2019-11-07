#### 枚举类根据key值获取value值



```
public static String getValue(String code) {
		for (TestEnum ele : values()) {
			if(ele.getCode().equals(code)) return ele.getValue();
		}
		return null;
	}
————————————————
版权声明：本文为CSDN博主「孤芳不自賞」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/en_joker/article/details/85044179
```

values() 理论上此方法可以将枚举类转变为一个枚举类型的数组
