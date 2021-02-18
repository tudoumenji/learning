https://www.jianshu.com/p/ffbf89dc2aba



# WEB服务器

只要Web上的Server都叫Web Server，但是大家分工不同，解决的问题也不同，所以根据Web Server提供的功能，每个Web Server的名字也会不一样。

按功能分类，Web Server可以分为：



```ruby
|- Web Server
        |- Http Server
        |- Application Server
            |- Servlet Container
            |- CGI Server
            |- ......
```

## Http服务器

HTTP Server本质上也是一种应用程序——它通常运行在服务器之上，绑定服务器的IP地址并监听某一个tcp端口来接收并处理HTTP请求，这样客户端（一般来说是IE, Firefox，Chrome这样的浏览器）就能够通过HTTP协议来获取服务器上的网页（HTML格式）、文档（PDF格式）、音频（MP4格式）、视频（MOV格式）等等资源。

一个HTTP Server关心的是HTTP协议层面的传输和访问控制，所以在Apache/Nginx上你可以看到代理、负载均衡等功能。

> 客户端通过HTTP Server访问服务器上存储的静态资源（HTML文件、图片文件等等）。
>
> 通过CGI/Servlet技术，也可以将处理过的动态内容通过HTTP Server分发，但是一个HTTP Server始终只是把服务器上的文件如实的通过HTTP协议传输给客户端。

HTTP Server中经常使用的是Apache、Nginx两种，HTTP Server主要用来做静态内容服务、代理服务器、负载均衡等。直面外来请求转发给后面的应用服务（Tomcat，django什么的）。



```ruby
|- Http Server
    |- Apache
    |- Nginx
```

## Application Server

Application Server 是一个应用执行的服务器。它首先需要支持开发语言的 Runtime（对于 Tomcat 来说，就是 Java），保证应用能够在应用服务器上正常运行。其次，需要支持应用相关的规范，例如类库、安全方面的特性。与HTTP Server相比，Application Server能够动态的生成资源并返回到客户端。



```ruby
|- Application Server
    |- Tomcat
    |- Jetty
```

当初在Apache Server开发时还未出现Servlet的概念，所以Apache不能内置支持Servlet。实际上，除了Apache，其他许多HTTP Server软件都不能直接支持Servlet。为了支持Servlet，通常要单独开发程序，这种程序一般称为服务器小程序容器（Servlet Container），有时也叫做服务器小程序引擎（Servlet Engine）。它是Web服务器或应用程序服务器的一部分，用于在发送的请求和响应之上提供网络服务，解码基于MIME的请求，格式化基于MIME的响应，它在Servlet的生命周期内包容和管理Servlet，是一个实时运行的外壳程序。运行时由Web服务器软件处理一般请求，并把Servlet调用传递给“容器”来处理。

比如，对于 Tomcat 来说，就是需要提供 JSP/Sevlet 运行需要的标准类库、Interface 等。为了方便，应用服务器往往也会集成 HTTP Server 的功能，但是不如专业的 HTTP Server 那么强大，所以Application Server往往是运行在 HTTP Server 的背后，执行应用，将动态的内容转化为静态的内容之后，通过 HTTP Server 分发到客户端。

Tomcat运行在JVM之上，它和HTTP服务器一样，绑定IP地址并监听TCP端口，同时还包含以下指责：



```undefined
1. 管理Servlet程序的生命周期；
2. 将URL映射到指定的Servlet进行处理；
3. 与Servlet程序合作处理HTTP请求——根据HTTP请求生成HttpServletRequest/Response对象并传递给Servlet进行处理，将Servlet中的HttpServletResponse对象生成的内容返回给浏览器；
```

所以 Tomcat 属于是一个「Application Server」，但是更准确的来说，是一个「Servlet/JSP」应用的容器（Ruby/Python 等其他语言开发的应用也无法直接运行在 Tomcat 上）。

# Tomcat工作原理

Tomcat 的结构很复杂，但是 Tomcat 也非常的模块化，找到了 Tomcat 最核心的模块，您就抓住了 Tomcat 的“七寸”。下面是 Tomcat 的总体结构图：

![img](https:////upload-images.jianshu.io/upload_images/6807865-f3747ca11e20e10c.png?imageMogr2/auto-orient/strip|imageView2/2/w/700/format/webp)

Tomcat的总体结构图

从上图可以看出Tomcat的核心是两个组件：**连接器（Connector）**和**容器（Container）**。Connector组件是负责生成请求对象和响应对象的，Tomcat默认的是HttpConnector，负责根据收到的Http请求报文生成Request对象和Response对象，并把这两个对象传递给Container，然后根据Response中的内容生成相应的HTTP报文。

Container是容器的父接口，所有子容器都必须实现这个接口，简单来说就是服务器部署的项目是运行在Container中的。Container里面的项目获取到Connector传递过来对应的的Request对象和Response对象进行相应的操作。

Connector可以根据不同的设计和应用场景进行替换。一个Container可以选择对应多个Connector。多个Connector和一个Container就形成了一个Service，有了Service就可以对外提供服务了。

Tomcat要为一个Servlet的请求提供服务，需要做四件事：

1. 创建一个request对象并填充那些有可能被所引用的Servlet使用的信息，如参数，头部、cookies、查询字符串等。一个request对象就是javax.servlet.ServletRequest或javax.servlet.http.ServletRequest接口的一个实例。
2. 创建一个response对象，所引用的servlet使用它来给客户端发送响应。一个response对象是javax.servlet.ServletResponse或javax.servlet.http.ServletResponse接口的一个实例。
3. 调用servlet的service方法，并传入request和response对象。这里servlet会从request对象取值，给response写值。
4. 根据servlet返回的response生成相应的HTTP响应报文。

既然我们已经抓到Tomcat的“七寸”，两个核心组件：连接器（Connector）和容器（Container），那这样从连接器（Connector）入手，来看下Tomcat处理HTTP请求的流程。

很多开源应用服务器都是集成tomcat作为web container的，而且对于tomcat的servlet container这部分代码很少改动。这样，这些应用服务器的性能基本上就取决于Tomcat处理HTTP请求的connector模块的性能。

#### Tomcat架构模块

![img](https:////upload-images.jianshu.io/upload_images/6807865-3d612e3255dfa29f.png?imageMogr2/auto-orient/strip|imageView2/2/w/700/format/webp)

Tomcat架构模块

1. Server(服务器)是Tomcat构成的顶级构成元素，所有一切均包含在Server中，Server的实现类StandardServer可以包含一个到多个Services;
2. 次顶级元素Service的实现类为StandardService调用了容器(Container)接口，其实是调用了Servlet Engine(引擎)，而且StandardService类中也指明了该Service归属的Server；
3. 接下来次级的构成元素就是容器(Container)：主机(Host)、上下文(Context)和引擎(Engine)均继承自Container接口，所以它们都是容器。但是，它们是有父子关系的，在主机(Host)、上下文(Context)和引擎(Engine)这三类容器中，引擎是顶级容器，直接包含是主机容器，而主机容器又包含上下文容器，所以引擎、主机和上下文从大小上来说又构成父子关系，虽然它们都继承自Container接口。
4. 连接器(Connector)将Service和Container连接起来，首先它需要注册到一个Service，它的作用就是把来自客户端的请求转发到Container(容器)，这就是它为什么称作连接器的原因。

###### Tomcat运行流程

![img](https:////upload-images.jianshu.io/upload_images/6807865-f8d3da16f906ca87.png?imageMogr2/auto-orient/strip|imageView2/2/w/700/format/webp)

Tomcat运行流程

假设来自客户的请求为：`http://localhost:8080/test/index.jsp`

> 1. 请求被发送到本机端口8080，被在那里侦听的Coyote HTTP/1.1 Connector获得；
> 2. Connector把该请求交给它所在的Service的Engine来处理，并等待Engine的回应；
> 3. Engine获得请求localhost:8080/test/index.jsp，匹配它所有虚拟主机Host；
> 4. Engine匹配到名为localhost的Host（即使匹配不到也把请求交给该Host处理，因为该Host被定义为该Engine的默认主机）；
> 5. localhost Host获得请求/test/index.jsp，匹配它所拥有的所有Context；
> 6. Host匹配到路径为/test的Context（如果匹配不到就把该请求交给路径名为""的Context去处理）；
> 7. path="/test"的Context获得请求/index.jsp，在它的mapping table中寻找对应的servlet；
> 8. Context匹配到URL PATTERN为*.jsp的servlet，对应于JspServlet类；
> 9. 构造HttpServletRequest对象和HttpServletResponse对象，作为参数调用JspServlet的doGet或doPost方法；
> 10. Context把执行完了之后的HttpServletResponse对象返回给Host；
> 11. Host把HttpServletResponse对象返回给Engine；
> 12. Engine把HttpServletResponse对象返回给Connector；
> 13. Connector把HttpServletResponse对象返回给客户browser；

#### Connector 组件

Connector 组件是 Tomcat 中两个核心组件之一，它的主要任务是负责接收浏览器的发过来的 tcp 连接请求，创建一个 Request 和 Response 对象分别用于和请求端交换数据，然后会产生一个线程来处理这个请求并把产生的 Request 和 Response 对象传给处理这个请求的线程，处理这个请求的线程就是 Container 组件要做的事了。

由于这个过程比较复杂，大体的流程可以用下面的顺序图来解释：

![img](https:////upload-images.jianshu.io/upload_images/6807865-b2163f492adc0907.png?imageMogr2/auto-orient/strip|imageView2/2/w/572/format/webp)

Connector 处理一次请求顺序图

Tomcat5 中默认的 Connector 是 Coyote，这个 Connector 是可以选择替换的。Connector 最重要的功能就是接收连接请求然后分配线程让 Container 来处理这个请求，所以这必然是多线程的，多线程的处理是 Connector 设计的核心。

当 Connector 将 socket 连接封装成 request 和 response 对象后接下来的事情就交给 Container 来处理了。

#### Servlet 容器“Container”

Container 是容器的父接口，所有子容器都必须实现这个接口，Container 容器的设计用的是典型的责任链的设计模式，它有四个子容器组件构成，分别是：Engine、Host、Context、Wrapper，这四个组件不是平行的，而是父子关系，Engine 包含 Host,Host 包含 Context，Context 包含 Wrapper。通常一个 Servlet class 对应一个 Wrapper，如果有多个 Servlet 就可以定义多个 Wrapper，如果有多个 Wrapper 就要定义一个更高的 Container 了，如 Context，Context 通常就是对应下面这个配置：



```xml
<Context  
    path="/library"
    docBase="D:\projects\library\deploy\target\library.war"
    reloadable="true" 
/>
```

###### 容器的总体设计

Context 还可以定义在父容器 Host 中，Host 不是必须的，但是要运行 war 程序，就必须要 Host，因为 war 中必有 web.xml 文件，这个文件的解析就需要 Host 了，如果要有多个 Host 就要定义一个 top 容器 Engine 了。而 Engine 没有父容器了，一个 Engine 代表一个完整的 Servlet 引擎。

当 Connector 接受到一个连接请求时，将请求交给 Container，Container 是如何处理这个请求的？这四个组件是怎么分工的，怎么把请求传给特定的子容器的呢？又是如何将最终的请求交给 Servlet 处理。下面是这个过程的时序图：

![img](https:////upload-images.jianshu.io/upload_images/6807865-3cf446b8570246ec.png?imageMogr2/auto-orient/strip|imageView2/2/w/572/format/webp)

Engine 和 Host 处理请求的时序图



作者：Rick617
链接：https://www.jianshu.com/p/ffbf89dc2aba
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。