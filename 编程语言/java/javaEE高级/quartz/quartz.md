https://www.jianshu.com/p/ce4c4400eea2



学习视频地址https://www.imooc.com/learn/846



在线*Cron*表达式生成器    http://cron.qqe2.com/



springboot scheduled并发配置

https://www.cnblogs.com/kangoroo/p/7286133.html

```java
@Configuration
@EnableScheduling
public class ScheduleConfiguration implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(destroyMethod="shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }
}
```

123
