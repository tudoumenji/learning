## 前言

用springboot开发的过程中，我们会用到@ConfigurationProperties注解，主要是用来把properties或者yml配置文件转化为bean来使用的，而@EnableConfigurationProperties注解的作用是@ConfigurationProperties注解生效。 如果只配置@ConfigurationProperties注解，在IOC容器中是获取不到properties配置文件转化的bean的，当然在@ConfigurationProperties加入注解的类上加@Component也可以使交于springboot管理。

## 举个栗子

**第一步**:创建一个类TestConfigurationProperties

```
@ConfigurationProperties(prefix = "properties")
public class TestConfigurationProperties {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
复制代码
```

**注意**:得加上set和get方法
 **第二步**:创建TestAutoConfiguration类

```
@Configuration
@EnableConfigurationProperties(TestConfigurationProperties.class)
public class TestAutoConfiguration {

    private TestConfigurationProperties testConfigurationProperties;

    public TestAutoConfiguration(TestConfigurationProperties testConfigurationProperties) {
        this.testConfigurationProperties = testConfigurationProperties;
    }

    @Bean
    public User user(){
        User user = new User();
        user.setName(testConfigurationProperties.getName());
        return user;
    }
}
复制代码
```

**注意**:得创建一个有参构造方法
 **第三步**:配置文件加入属性

```
properties.name=test
复制代码
```

**第四步**:跑一下，打印出User这个类

```
@RestController
@RequestMapping("/api/test")
@Slf4j
public class TestController {
    @Autowired
    TestConfigurationProperties testConfigurationProperties;

    @Autowired
    User user;

    @RequestMapping(value = "/testConfigurationProperties")
    public String testConfigurationProperties() {
        log.info("test testConfigurationProperties.............{}", testConfigurationProperties.getName());
        log.info("user:{}", user);
        return "SUCCESS";
    }
}
复制代码
```

控制台输出：

```
2019-04-21/16:11:36.638||||||||^_^|[http-nio-8088-exec-1] INFO  com.stone.zplxjj.controller.TestController 37 - test testConfigurationProperties.............test
2019-04-21/16:11:36.639||||||||^_^|[http-nio-8088-exec-1] INFO  com.stone.zplxjj.controller.TestController 38 - user:User(id=null, name=test)
复制代码
```

## @EnableConfigurationProperties是怎么加载的

通过查看@EnableConfigurationProperties的注解：

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(EnableConfigurationPropertiesImportSelector.class)
public @interface EnableConfigurationProperties {

	/**
	 * Convenient way to quickly register {@link ConfigurationProperties} annotated beans
	 * with Spring. Standard Spring Beans will also be scanned regardless of this value.
	 * @return {@link ConfigurationProperties} annotated beans to register
	 */
	Class<?>[] value() default {};

}
复制代码
```

通过分析自动配置可以知道，肯定是这个类EnableConfigurationPropertiesImportSelector起的作用：

```
private static final String[] IMPORTS = {
			ConfigurationPropertiesBeanRegistrar.class.getName(),
			ConfigurationPropertiesBindingPostProcessorRegistrar.class.getName() };

	@Override
	public String[] selectImports(AnnotationMetadata metadata) {
		return IMPORTS;
	}
复制代码
```

selectImports方法返回了这两个类：ConfigurationPropertiesBeanRegistrar和ConfigurationPropertiesBindingPostProcessorRegistrar，是何时加载的，我们只需要看这个类ConfigurationPropertiesBeanRegistrar即可：

```
public static class ConfigurationPropertiesBeanRegistrar
			implements ImportBeanDefinitionRegistrar {

		@Override
		public void registerBeanDefinitions(AnnotationMetadata metadata,
				BeanDefinitionRegistry registry) {
			getTypes(metadata).forEach((type) -> register(registry,
					(ConfigurableListableBeanFactory) registry, type));
		}
        //找到加入这个注解@EnableConfigurationProperties里面的value值，其实就是类class
		private List<Class<?>> getTypes(AnnotationMetadata metadata) {
			MultiValueMap<String, Object> attributes = metadata
					.getAllAnnotationAttributes(
							EnableConfigurationProperties.class.getName(), false);
			return collectClasses((attributes != null) ? attributes.get("value")
					: Collections.emptyList());
		}

		private List<Class<?>> collectClasses(List<?> values) {
			return values.stream().flatMap((value) -> Arrays.stream((Object[]) value))
					.map((o) -> (Class<?>) o).filter((type) -> void.class != type)
					.collect(Collectors.toList());
		}
      //注册方法：根据找到的类名name和type，将加入注解@ConfigurationProperties的类加入spring容器里面
		private void register(BeanDefinitionRegistry registry,
				ConfigurableListableBeanFactory beanFactory, Class<?> type) {
			String name = getName(type);
			if (!containsBeanDefinition(beanFactory, name)) {
				registerBeanDefinition(registry, name, type);
			}
		}
    //找到加入注解@ConfigurationProperties的类的名称，加入一定格式的拼接
		private String getName(Class<?> type) {
			ConfigurationProperties annotation = AnnotationUtils.findAnnotation(type,
					ConfigurationProperties.class);
			String prefix = (annotation != null) ? annotation.prefix() : "";
			return (StringUtils.hasText(prefix) ? prefix + "-" + type.getName()
					: type.getName());
		}

		private boolean containsBeanDefinition(
				ConfigurableListableBeanFactory beanFactory, String name) {
			if (beanFactory.containsBeanDefinition(name)) {
				return true;
			}
			BeanFactory parent = beanFactory.getParentBeanFactory();
			if (parent instanceof ConfigurableListableBeanFactory) {
				return containsBeanDefinition((ConfigurableListableBeanFactory) parent,
						name);
			}
			return false;
		}

		private void registerBeanDefinition(BeanDefinitionRegistry registry, String name,
				Class<?> type) {
			assertHasAnnotation(type);
			GenericBeanDefinition definition = new GenericBeanDefinition();
			definition.setBeanClass(type);
			registry.registerBeanDefinition(name, definition);
		}

		private void assertHasAnnotation(Class<?> type) {
			Assert.notNull(
					AnnotationUtils.findAnnotation(type, ConfigurationProperties.class),
					() -> "No " + ConfigurationProperties.class.getSimpleName()
							+ " annotation found on  '" + type.getName() + "'.");
		}

	}
复制代码
```

## 结语

另外还有这个类：ConfigurationPropertiesBindingPostProcessorRegistrar，刚刚没有分析，看了下源码，其实他做的事情就是将配置文件当中的属性值赋予到加了@ConfigurationProperties的注解的类的属性上，具体就不分析了，有兴趣自己可以阅读，入口知道了，就简单了

本人也开通了微信公众号：stonezplxjj和个人博客：[www.zplxjj.com](https://link.juejin.im?target=http%3A%2F%2Fwww.zplxjj.com)，更多文章欢迎关注公众号：

作者：张珮磊想静静

链接：https://juejin.im/post/5cbeaa26e51d45789024d7e2

来源：掘金

著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
