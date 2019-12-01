# [Mybatis 中的转义字符](https://www.cnblogs.com/dato/p/7028723.html)

 记录以下mybatis中的转义字符，方便以后自己看一下

| \&lt;   | <    | 小于   |
| ------- | ---- | ------ |
| \&gt;   | >    | 大于   |
| \&amp;  | &    | 与     |
| \&apos; | '    | 单引号 |
| \&quot; | "    | 双引号 |

需要注意的是分号是必不可少的。 比如 a > b 我们就写成  a \&gt; b

 

当然啦， 我们也可以用另外一种，就是<![CDATA[ ]]>符号。 在mybatis中这种符号将不会解析。 比如

```
<![CDATA[ when min(starttime)<='12:00' and max(endtime)<='12:00' ]]>    
```

 
