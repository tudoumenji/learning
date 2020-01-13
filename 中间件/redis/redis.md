Redis

1 缓存的意义：引入缓存其实是对后台数据库起到保护的作用.使用缓存,**实质上是降低了访问真实数据库的访问频次**.缓存中的数据就是数据库中的数据.

 

2 redis

redis是一个**key-value**[**存储系统**](https://baike.baidu.com/item/存储系统)。和Memcached类似，它支持存储的value类型相对更多，包括string(字符串)、list([链表](https://baike.baidu.com/item/链表))、set(集合)、zset(sorted set --有序集合)和hash（哈希类型）。这些[数据类型](https://baike.baidu.com/item/数据类型)都支持push/pop、add/remove及取交集并集和差集及更丰富的操作，而且这些操作都是**原子性**的。在此基础上，redis支持各种不同方式的排序。与memcached一样，为了保证效率，数据都是缓存在内存中。区别的是redis会周期性的把更新的数据写入磁盘或者把修改操作写入追加的记录文件，并且在此基础上实现了master-slave(主从)同步。

读写速度:  

写入速度: 8.6万/秒

读取速度: 12万/秒

 主要功能:数据库、缓存和消息中间件

 

3 修改redis配置文件

取消IP绑定  取消保护模式	后台启动 端口号 PID文件路径 持久化文件路径 内存策略

集群配置

 

4 redis命令

**String类型**

Set/get/exists/expire（指定key的失效时间）/ttl（检查key的剩余存活时间）

**Hash类型：**一般采用hash用来保存对象信息，命令前加h

**List类型：**Redis中List集合类型采用双端循环列表的形式保存数据.

链表特点:读取速度慢,查询两端速度快,增删快,

数组特点:读取速度快,增删慢.

List集合类型中有2种常用的数据类型.1栈,2队列

栈特点:先进后出  保存数据的方向与取数据的方向相同.

队列特点:先进先出 保存数据的方向与取数据的方向相反.

易错点: 采用list类型取出数据后,数据是否还存在???  取出数据后,数据将从队列中消失.

实际业务场景:一般采用list类型当做消息队列使用.

消息队列的作用:缓解后台服务器压力.

lpush/rpop

 

5 事务控制

Multi标记一个事务开始

exec执行所有multi之后发的命令

discard丢弃所有multi之后发的命令

 

6 缓存三大问题

缓存穿透

条件:访问一个不存在的数据

说明:当访问一个不存在的数据时,因为缓存中没有这个key,导致缓存形同虚设.最终访问后台数据库.但是数据库中没有该数据所以返回null.

隐患:如果有人恶意频繁查询一个不存在的数据,可能会导致数据库负载高导致宕机.

总结:业务系统访问一个不存在的数据,称之为缓存穿透.

 

缓存击穿

条件:当缓存key失效/过期/未命中时,高并发访问该key

说明:如果给一个key设定了失效时间,当key失效时有一万的并发请求访问这个key,这时缓存失效,所有的请求都会访问后台数据库.称之为缓存击穿.

场景:微博热点消息访问量很大,如果该缓存失效则会直接访问后台数据库,导致数据库负载过高.

 

缓存雪崩

前提:高并发访问,缓存命中较低或者失效时

说明:假设缓存都设定了失效时间,在同一时间内缓存大量失效.如果这时用户高并发访问.缓存命中率过低.导致全部的用户访问都会访问后台真实的数据库.

场景:在高并发条件下.缓存动态更新时

 

7 redis分片技术

目的：实现redis内存扩容

7.1 hash一致性算法：解决数据一致性问题

![img](file:///C:\Users\y1207\AppData\Local\Temp\ksohtml4588\wps1.jpg) 

7.2 均衡性

说明:尽可能让节点中的数据均衡.引入虚拟节点概念,平衡数据.

![img](file:///C:\Users\y1207\AppData\Local\Temp\ksohtml4588\wps2.jpg) 

 

 

 

 

7.3 单调性

说明:**新增**节点后,数据可以实现自动的迁移.

![img](file:///C:\Users\y1207\AppData\Local\Temp\ksohtml4588\wps3.jpg) 

 

7.4 分散性

说明:因为分布式部署,**导致项目不能够使用全部的内存空间**.导致一个key有多个位置.

7.5 负载 

说明:负载是从另一个角度考虑分散性.导致一个位置,有多个key.

好的哈希一致性算法,要求尽可能的降低分散性和负载.

优化:尽量让项目使用全部的内存空间.

 

8  redis持久化

8.1 RDB模式（默认）

特点：1、持久化的效率最高,记录内存快照,每次保存都是最新的数据2、RDB模式占用空间小3、RDB模式保存数据是加密的.

策略：在x秒内执行y次set操作则持久化一次

 

8.2 AOF模式

特点：AOF模式是将用户的**全部操作过程**,写入文件中.

策略：当用户执行一次set操作,则持久化一次//每秒执行一次持久化//由操作系统设定.(一般不用)

 

9 redis内存策略

9.1 LRU算法：[内存管理](https://baike.baidu.com/item/内存管理/5633616)的一种页面置换算法，对于在内存中但又不用的[数据块](https://baike.baidu.com/item/数据块/107672)（内存块）叫做LRU，操作系统会根据哪些数据属于LRU而将其移出内存而腾出空间来加载另外的数据。

9.2 1.volatile-lru   设定了超时时间的数据,采用LRU算法.

2.allkeys-lru    所有的key中使用lru算法

3.volatile-random  设定了超时时间的随机删除

4.allkeys-random   所有key的随机删除

5.volatile-ttl     将要过期的数据,提前删除.

6.Noeviction       不会删除数据,但是会返回错误消息.

7.Note              由操作系统负责维护.

 

10  redis高可用（哨兵）

互相挂载，互为主从

 

11 redis集群

11.1 集群划分：

说明:redis集群可以实现内存的动态扩容,redis节点的高可用.

规范:redis中主节点数量一般都是奇数个.

规模:3个主节点/6个从节点共9个redis节点. 7000-7008

 

12  redis hash槽存储数据原理

说明: RedisCluster采用此分区，所有的键根据哈希函数(CRC16[key]&16383)映射到0－16384槽内，共16384个槽位，每个节点维护部分槽及槽所映射的键值数据.根据主节点的个数,均衡划分区间.

 算法:哈希函数: Hash()=CRC16[key]&16384按位与

![img](file:///C:\Users\y1207\AppData\Local\Temp\ksohtml4588\wps4.jpg) 

 

当向redis集群中插入数据时,首先将key进行计算.之后将计算结果匹配到具体的某一个槽的区间内,之后再将数据set到管理该槽的节点中.

如图-27所示

![img](file:///C:\Users\y1207\AppData\Local\Temp\ksohtml4588\wps5.jpg) 

 

13 springdataredis

使用RestTemplate操作redis

1.redisTemplate.opsForValue();//操作字符串

2.redisTemplate.opsForHash();//操作hash

3.redisTemplate.opsForList();//操作list

4.redisTemplate.opsForSet();//操作set

5.redisTemplate.opsForZSet();//操作有序set

6.RedisTemplate.opsForCluster()//操作集群

避免缓存穿透可使用分布式缓存锁，通过设置NX，使用完还锁

 

 

 

 

 

 

 

 

 