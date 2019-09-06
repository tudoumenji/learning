JPA：https://www.mkyong.com/spring-boot/spring-boot-spring-data-jpa-oracle-example/

主键生成策略：https://blog.csdn.net/canot/article/details/51455967



```xml
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>com.oracle</groupId>
            <artifactId>ojdbc14</artifactId>
            <version>10.2.0.3.0</version>
        </dependency>
```



```
spring: 
  datasource:
    url: jdbc:oracle:thin:@//0.0.0.0:0/xxx    //ip:port/database
    username: xxx
    password: xxx
    driver-class-name: oracle.jdbc.driver.OracleDriver
  jpa:
    show-sql: true		//显示语句
```



```java
  	
	//主键设置：strategy为SEQUENCE，generator和name对应就行，sequenceName找下原生语句，	allocationSize步长为1,数据类型一般为Long
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "aaa")
    @SequenceGenerator(name="aaa", sequenceName="OperationLogNew_id_SEQ",allocationSize = 1)
    private Long id;
```



oracle语句(过滤、分页、排序)（最好不要带末尾的分号）

```sql
select * from (SELECT a.*,ROWNUM rowno FROM (
                  SELECT t.* FROM DB.table t
                  WHERE aaa = 1 and bbb = 2
                  and ccc = 'xxx'
                  ORDER BY ccc DESC
              ) a WHERE ROWNUM <= 3 ) b where b.rowno >= 4
```



