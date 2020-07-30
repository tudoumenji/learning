https://blog.csdn.net/u014799292/article/details/90167096



在java多线程操作中， BlockingQueue<E> 常用的一种方法之一。在看jdk内部尤其是一些多线程，大量使用了blockinkQueue 来做的。 

借用jdk api解释下：

BlockingQueue 方法以四种形式出现，对于不能立即满足但可能在将来某一时刻可以满足的操作，这四种形式的处理方式不同：第一种是抛出一个异常，第二种是返回一个特殊值（null 或 false，具体取决于操作），第三种是在操作可以成功前，无限期地阻塞当前线程，第四种是在放弃前只在给定的最大时间限制内阻塞。下表中总结了这些方法：

|      | 抛出异常                                                     | 特殊值                                                       | 阻塞                                                         | 超时                                                         |
| ---- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 插入 | [`add(e)`](https://blog.csdn.net/wei_ya_wen/article/details/19344939) | [`offer(e)`](https://blog.csdn.net/wei_ya_wen/article/details/19344939) | [`put(e)`](https://blog.csdn.net/wei_ya_wen/article/details/19344939) | [`offer(e, time, unit)`](https://blog.csdn.net/wei_ya_wen/article/details/19344939) |
| 移除 | [`remove()`](https://blog.csdn.net/wei_ya_wen/article/details/19344939) | [`poll()`](https://blog.csdn.net/wei_ya_wen/article/details/19344939) | [`take()`](https://blog.csdn.net/wei_ya_wen/article/details/19344939) | [`poll(time, unit)`](https://blog.csdn.net/wei_ya_wen/article/details/19344939) |
| 检查 | [`element()`](https://blog.csdn.net/wei_ya_wen/article/details/19344939) | [`peek()`](https://blog.csdn.net/wei_ya_wen/article/details/19344939) | 不可用                                                       | 不可用                                                       |

ArrayBlockingQueue介绍

ArrayBlockingQueue是一个阻塞式的队列，继承自AbstractBlockingQueue,间接的实现了Queue接口和Collection接口。底层以数组的形式保存数据(实际上可看作一个循环数组)。常用的操作包括 add，offer，put，remove，poll，take，peek。

根据 ArrayBlockingQueue 的名字我们都可以看出，它是一个队列，并且是一个基于数组的阻塞队列。

ArrayBlockingQueue 是一个有界队列，有界也就意味着，它不能够存储无限多数量的对象。所以在创建 ArrayBlockingQueue 时，必须要给它指定一个队列的大小。

我们先来熟悉一下 ArrayBlockingQueue 中的几个重要的方法。

- add(E e)：把 e 加到 BlockingQueue 里，即如果 BlockingQueue 可以容纳，则返回 true，否则报异常 
- offer(E e)：表示如果可能的话，将 e 加到 BlockingQueue 里，即如果 BlockingQueue 可以容纳，则返回 true，否则返回 false 
- put(E e)：把 e 加到 BlockingQueue 里，如果 BlockQueue 没有空间，则调用此方法的线程被阻断直到 BlockingQueue 里面有空间再继续
- poll(time)：取走 BlockingQueue 里排在首位的对象，若不能立即取出，则可以等 time 参数规定的时间,取不到时返回 null 
- take()：取走 BlockingQueue 里排在首位的对象，若 BlockingQueue 为空，阻断进入等待状态直到 Blocking 有新的对象被加入为止 
- remainingCapacity()：剩余可用的大小。等于初始容量减去当前的 size

ArrayBlockingQueue 使用场景。

- 先进先出队列（队列头的是最先进队的元素；队列尾的是最后进队的元素）
- 有界队列（即初始化时指定的容量，就是队列最大的容量，不会出现扩容，容量满，则阻塞进队操作；容量空，则阻塞出队操作）
- 队列不支持空元素

 

保存数据的结构

```java
/** The queued items */final Object[] items;
```

可以看到，是一个Object的数组。

全局锁

```java
/** Main lock guarding all access */final ReentrantLock lock;
```

注视也说明了，这是一个掌管所有访问操作的锁。全局共享。都会使用这个锁。

ArrayBlockingQueue 进队操作采用了加锁的方式保证并发安全。源代码里面有一个 while() 判断：

```java
public void put(E e) throws InterruptedException {    checkNotNull(e); // 非空判断    final ReentrantLock lock = this.lock;    lock.lockInterruptibly(); // 获取锁    try {        while (count == items.length) {            // 一直阻塞，知道队列非满时，被唤醒            notFull.await();        }        enqueue(e); // 进队    } finally {        lock.unlock();    }}public boolean offer(E e, long timeout, TimeUnit unit)    throws InterruptedException {    checkNotNull(e);    long nanos = unit.toNanos(timeout);    final ReentrantLock lock = this.lock;    lock.lockInterruptibly();    try {        while (count == items.length) {        // 阻塞，知道队列不满        // 或者超时时间已过，返回false            if (nanos <= 0)                return false;            nanos = notFull.awaitNanos(nanos);        }        enqueue(e);        return true;    } finally {        lock.unlock();    }}
```

通过源码分析，我们可以发现下面的规律：

- 阻塞调用方式 put(e)或 offer(e, timeout, unit)
- 阻塞调用时，唤醒条件为超时或者队列非满（因此，要求在出队时，要发起一个唤醒操作）
- 进队成功之后，执行notEmpty.signal()唤起被阻塞的出队线程

出队的源码类似。ArrayBlockingQueue 队列我们可以在创建线程池时进行使用。

```java
new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(2));
```

PS：在进行某项业务存储操作时，建议采用offer进行添加，可及时获取boolean进行判断，如用put要考虑阻塞情况（队列的出队操作慢于进队操作），资源占用。

有兴趣的可以顺便了解下 LinkedBlockingQueue 

> 文章参考：
>
> [BlockingQueue中 take、offer、put、add的一些比较 ](https://blog.csdn.net/weixin_41704428/article/details/80374495)
>
> [ArrayBlockingQueue详解](https://blog.csdn.net/belalds/article/details/81070111)
>
> [ArrayBlockingQueue 使用](https://www.xttblog.com/?p=3686)
>
> [深入剖析java并发之阻塞队列LinkedBlockingQueue与ArrayBlockingQueue](https://blog.csdn.net/javazejian/article/details/77410889)