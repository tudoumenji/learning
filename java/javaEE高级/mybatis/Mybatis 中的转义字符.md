# Mybatis特殊字符转义

2018-11-01 16:08:54 [jacksonjj](https://me.csdn.net/userlhj) 阅读数 2031 收藏

<![CDATA[ ]]>

XML文件会在解析XML时将5种特殊字符进行转义，分别是&， <， >， “， ‘， 我们不希望语法被转义，就需要进行特别处理。
有两种解决方法：其一，使用**<![CDATA[ ]]>**标签来包含字符。其二，使用XML转义序列来表示这些字符。

```sql
<select id="userInfo" parameterType="java.util.HashMap" resultMap="user">   
     SELECT id,newTitle, newsDay FROM newsTable WHERE 1=1  
     AND  newsday <![CDATA[>=]]> #{startTime}
     AND newsday <![CDATA[<= ]]>#{endTime}  
  ]]>  
 </select>
```

在CDATA内部的所有内容都会被解析器忽略，保持原貌。所以在Mybatis配置文件中，要尽量缩小 <![CDATA[ ]]>的作用范围，来避免 等sql标签无法解析的问题。

**使用XML转义序列**
5种特殊字符的转义序列

```
特殊字符     转义序列
<           &lt;
>           &gt;
&           &amp;
"           &quot;
'           &apos;
=						=
```

上述sql也可以写成如下：

```sql
<select id="userInfo" parameterType="java.util.HashMap" resultMap="user">   
     SELECT id,newTitle, newsDay FROM newsTable WHERE 1=1  
     AND  newsday &gt; #{startTime}
     AND  newsday &gt; #{endTime}  
 </select>  
12345
```

## 推荐使用<![CDATA[ ]]>，清晰，简洁
