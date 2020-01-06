#### SimpleDateFormat 格式化日期

日期和时间格式由 日期和时间模式字符串 指定。在 日期和时间模式字符串 中，未加引号的字母 'A' 到 'Z' 和 'a' 到 'z' 被解释为模式字母，用来表示日期或时间字符串元素。文本可以使用单引号 (') 引起来，以免进行解释。所有其他字符均不解释；只是在格式化时将它们简单复制到输出字符串

白话文的讲：这些A——Z，a——z这些字母(不被单引号包围的)会被特殊处理替换为对应的日期时间，其他的字符串还是原样输出。
日期和时间模式(注意大小写，代表的含义是不同的)

```

yyyy：年
MM：月
dd：日
hh：1~12小时制(1-12)
HH：24小时制(0-23)
mm：分
ss：秒
S：毫秒
E：星期几
D：一年中的第几天
F：一月中的第几个星期(会把这个月总共过的天数除以7)
w：一年中的第几个星期
W：一月中的第几星期(会根据实际情况来算)
a：上下午标识
k：和HH差不多，表示一天24小时制(1-24)。
K：和hh差不多，表示一天12小时制(0-11)。
z：表示时区  

一个月中的第几个星期，  F   这个出来的结果，不靠谱（经过测试），后面的那个  W  靠谱。
```



```java
/**
 * Date对象转字符串
 */
public class Format {
    public static void main(String[] args) {
        Date ss = new Date();
        System.out.println("一般日期输出：" + ss);
        System.out.println("时间戳：" + ss.getTime());
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format0.format(ss.getTime());//这个就是把时间戳经过处理得到期望格式的时间
        System.out.println("格式化结果0：" + time);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        time = format1.format(ss.getTime());
        System.out.println("格式化结果1：" + time);
    }
    
输出：
一般日期输出：Mon Sep 09 19:59:32 CST 2019
时间戳：1568030372435
格式化结果0：2019-09-09 19:59:32
格式化结果1：2019年09月09日 19时59分32秒
    
    
/**
 * 字符串转Date对象
 */
public class TimeTest02 {
    public static void main(String[] args) throws ParseException {
        String dateTime = "2019-09-09 20:09:30";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(dateTime);
        System.out.println(date);
    }
}
    
输出：Mon Sep 09 20:09:30 CST 2019
    

```

