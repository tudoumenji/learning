@PathVariable(required = false) 不起作用的解决办法



不能允许为空，但是可以变通。最近遇到个required=false没用的情况。如下：

```
`@RequestMapping``(value = ``"/student/{id}"``, RequestMethod.GET)``public` `Student student(``@PathVariable``(required = ``false``) Integer id) {``    ``//......``    ``return` `student;``}`
```

按照上面的代码，是希望如果不传id，Integer id的id就为null，可以查询所有学生信息。但是发现，/student这个请求是过不来的，而且会报错。

试了n次，突然发现，其实/student和/student/{id}是两个不同的url，当然是不能走同一个请求的呀。于是想到@RequestMapping是可以处理多个url的。于是将上面的@RequestMapping改成下面这样：

```
`@RequestMapping``(value = {``"/student/{id}"``, ``"/student"``}, RequestMethod.GET)`
```

这样就是可以的，此时的required = false就可以起作用了。

我觉得springmvc处理这块的逻辑就是：如果required = false，而你没有传这个参数，那么它会去找这个参数去掉之后的替代url (/student)，如果发现有替代的url，就可以处理这个请求，如果没有找到，就抛出异常不去处理。

所以其实上面的方法也可以拆成两个方法。

```
`@RequestMapping``(value = ``"/student/{id}"``, RequestMethod.GET)``public` `Student student(``@PathVariable` `int` `id) {``}``@RequestMapping``(value = ``"/student"``, RequestMethod.GET)``public` `Student studentAll() {``}`
```
