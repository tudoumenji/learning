#### 常用命令

```
GET key 返回key的value。如果key不存在，返回特殊值nil。如果key的value不是string，就返回错误，因为GET只处理string类型的values。

SET key value [EX seconds] [PX milliseconds] [NX|XX]    将键key设定为指定的“字符串”值。

APPEND key value 如果 key 已经存在，并且值为字符串，那么这个命令会把 value 追加到原来值（value）的结尾。 如果 key 不存在，那么它将首先创建一个空字符串的key，再执行追加操作，这种情况 APPEND 将类似于 SET 操作。

LPUSH key value [value ...]   添加list集合

SADD key member [member ...]  添加set集合

LRANGE key start stop  返回存储在 key 的列表里指定范围内的元素。 start 和 end 偏移量都是基于0的下标，即list的第一个元素下标是0（list的表头），第二个元素下标是1，以此类推。偏移量也可以是负数，表示偏移量是从list尾部开始计数。 例如， -1 表示列表的最后一个元素，-2 是倒数第二个，以此类推。

```



#### springboot_redis

```
spring-boot-starter-data-redis
配置：
spring.redis.host=118.24.44.169
```

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot01CacheApplicationTests {
	@Autowired
	EmployeeMapper employeeMapper;

	@Autowired
	StringRedisTemplate stringRedisTemplate;  //操作k-v都是字符串的

	@Autowired
	RedisTemplate redisTemplate;  //k-v都是对象的

	@Autowired
	RedisTemplate<Object, Employee> empRedisTemplate;  //配置了json序列化机制的RedisTemplate


	/**
	 * Redis常见的五大数据类型
	 *  String（字符串）、List（列表）、Set（集合）、Hash（散列）、ZSet（有序集合）
	 *  stringRedisTemplate.opsForValue()[String（字符串）]
	 *  stringRedisTemplate.opsForList()[List（列表）]
	 *  stringRedisTemplate.opsForSet()[Set（集合）]
	 *  stringRedisTemplate.opsForHash()[Hash（散列）]
	 *  stringRedisTemplate.opsForZSet()[ZSet（有序集合）]
	 */
	@Test
	public void test01(){
		//给redis中保存数据
	    //stringRedisTemplate.opsForValue().append("msg","hello");
//		String msg = stringRedisTemplate.opsForValue().get("msg");
//		System.out.println(msg);

//		stringRedisTemplate.opsForList().leftPush("mylist","1");
//		stringRedisTemplate.opsForList().leftPush("mylist","2");
	}

	
	//测试保存对象
	
	//1、redisTemplate.opsForValue().set("emp-01",empById);
	//如果直接redisTemplate保存对象，默认使用jdk序列化机制，应将实体类implements Serializable序列化，序列化后的数据才能保存到redis中
	//但是默认序列化后的对象存入redis，存入的将是很长很乱的二进制码，很难看，所以可以转换成json保存
	//
	//2、将数据以json的方式保存
	//(1)手动用ObjectMapper将对象转为json
	//(2)empRedisTemplate.opsForValue().set("emp-01",empById)
	//empRedisTemplate：改变RedisTemplate默认的序列化规则，有JDK序列化转为JSON序列化
	@Test
	public void test02(){
		Employee empById = employeeMapper.getEmpById(1);
		
		empRedisTemplate.opsForValue().set("emp-01",empById);
	}



	@Test
	public void contextLoads() {

		Employee empById = employeeMapper.getEmpById(1);
		System.out.println(empById);

	}

}
```

(可选)改变RedisTemplate默认的序列化规则，有JDK序列化转为JSON序列化

```java
@Configuration
public class MyRedisConfig {

    @Bean
    public RedisTemplate<Object, Employee> empRedisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Employee> template = new RedisTemplate<Object, Employee>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Employee> ser = new Jackson2JsonRedisSerializer<Employee>(Employee.class);
        template.setDefaultSerializer(ser);
        return template;
    }
    @Bean
    public RedisTemplate<Object, Department> deptRedisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Department> template = new RedisTemplate<Object, Department>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Department> ser = new Jackson2JsonRedisSerializer<Department>(Department.class);
        template.setDefaultSerializer(ser);
        return template;
    }

}
```

