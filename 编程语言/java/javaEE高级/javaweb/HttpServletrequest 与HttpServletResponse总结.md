## [HttpServletrequest 与HttpServletResponse总结](https://www.cnblogs.com/liuyandeng/p/5336649.html)

　　如果说DOM是javascript与HTML的桥梁,那么servlet就是前端与后端的桥梁,HttpServletRequest和HttpServletResponse就是之间的信使,好了,废话不多说!

### 常用接口

1．HttpServletRequest

HttpServletRequest接口最常用的方法就是获得请求中的参数，这些参数一般是客户端表单中的数据。同时，HttpServletRequest接口可以获取由客户端传送的名称，也可以获取产生请求并且接收请求的服务器端主机名及IP地址，还可以获取客户端正在使用的通信协议等信息。下表是接口HttpServletRequest的常用方法。

说明：HttpServletRequest接口提供了很多的方法。

接口HttpServletRequest的常用方法

| 方    法                        | 说    明                                                     |
| ------------------------------- | ------------------------------------------------------------ |
| getAttributeNames()             | 返回当前请求的所有属性的名字集合                             |
| getAttribute(String name)       | 返回name指定的属性值                                         |
| getCookies()                    | 返回客户端发送的Cookie                                       |
| getsession()                    | 返回和客户端相关的session，如果没有给客户端分配session，则返回null |
| getsession(boolean create)      | 返回和客户端相关的session，如果没有给客户端分配session，则创建一个session并返回 |
| getParameter(String name)       | 获取请求中的参数，该参数是由name指定的                       |
| getParameterValues(String name) | 返回请求中的参数值，该参数值是由name指定的                   |
| getCharacterEncoding()          | 返回请求的字符编码方式                                       |
| getContentLength()              | 返回请求体的有效长度                                         |
| getInputStream()                | 获取请求的输入流中的数据                                     |
| getMethod()                     | 获取发送请求的方式，如get、post                              |
| getParameterNames()             | 获取请求中所有参数的名字                                     |
| getProtocol()                   | 获取请求所使用的协议名称                                     |
| getReader()                     | 获取请求体的数据流                                           |
| getRemoteAddr()                 | 获取客户端的IP地址                                           |
| getRemoteHost()                 | 获取客户端的名字                                             |
| getServerName()                 | 返回接受请求的服务器的名字                                   |
| getServerPath()                 | 获取请求的文件的路径                                         |

 

2．HttpServletResponse

在Servlet中，当服务器响应客户端的一个请求时，就要用到HttpServletResponse接口。设置响应的类型可以使用setContentType()方法。发送字符数据，可以使用getWriter()返回一个对象。下表是接口HttpServletResponse的常用方法。

接口HttpServletResponse的常用方法

| 方    法                             | 说    明                                 |
| ------------------------------------ | ---------------------------------------- |
| addCookie(Cookie cookie)             | 将指定的Cookie加入到当前的响应中         |
| addHeader(String name,String value)  | 将指定的名字和值加入到响应的头信息中     |
| containsHeader(String name)          | 返回一个布尔值，判断响应的头部是否被设置 |
| encodeURL(String url)                | 编码指定的URL                            |
| sendError(int sc)                    | 使用指定状态码发送一个错误到客户端       |
| sendRedirect(String location)        | 发送一个临时的响应到客户端               |
| setDateHeader(String name,long date) | 将给出的名字和日期设置响应的头部         |
| setHeader(String name,String value)  | 将给出的名字和值设置响应的头部           |
| setStatus(int sc)                    | 给当前响应设置状态码                     |
| setContentType(String ContentType)   | 设置响应的MIME类型                       |

# **由来**

　　Web服务器收到一个http请求，会针对每个请求创建一个HttpServletRequest和HttpServletResponse对象，向客户端发送数据找HttpServletResponse,从客户端取数据找HttpServletRequest.

　　HTTP 协议是基于请求-响应的协议，客户端请求一个文件，服务器对该请求进行响应。HTTP 使用 TCP 协议，默认使用 80 端口。最初的 HTTP 协议版本是 HTTP/0.9，后被 HTTP/1.0 替代。目前使用的版本是 HTTP/1.1，

在 HTTP 协议中，总是由主动建立连接、发送 HTTP 请求的客户端来初始化一个事务。服务器不负责连接客户端，或创建一个到客户端的回调连接（callback connection）。

# **HttpServletRequest** 

　　公共接口类HttpServletRequest继承自ServletRequest.客户端浏览器发出的请求被封装成为一个HttpServletRequest对象。所有的信息包括请求的地址，请求的参数，提交的数据，上传的文件客户端的ip甚至客户端操作系统都包含在其内。

一个 HTTP 请求包含以下三部分：

**a.****请求地址**(URL)

**b.请求头**(Request headers)

**c.实体数据**(Entity body)

举例如下

POST /examples/default.jsp HTTP/1.1

Accept: text/plain; text/html

Accept-Language: en-gb

Connection: Keep-Alive

Host: localhost

User-Agent: Mozilla/4.0 (compatible; MSIE 4.01; Windows 98)

Content-Length: 33

Content-Type: application/x-www-form-urlencoded

Accept-Encoding: gzip, deflate

lastName=Franks&firstName=Michael

 

　　每个 HTTP 请求都会有一个请求方法，HTTP1.1 中支持的方法包括，GET、POST、HEAD、OPTIONS、

PUT、DELETE 和 TRACE。互联网应用中最常用的是 GET 和 POST。

　　URI 指明了请求资源的地址，通常是从网站更目录开始计算的一个相对路径，因此它总是以斜线 “/”开头的。URL 实际上是 URI 的一种类型,请求头（header）中包含了一些关于客户端环境和请求实体（entity）的有用的信息。例如，客户端浏览器所使用的语言，请求实体信息的长度等。每个请求头使用 CRLF（回车换行符，“\r\n”）分隔。注意请求头的格式：

请求头名+英文空格+请求头值

 

## **常用方法**

**1.获得客户机信息**

getRequestURL方法返回客户端发出请求时的完整URL。

getRequestURI方法返回请求行中的资源名部分。

getQueryString 方法返回请求行中的参数部分。

getRemoteAddr方法返回发出请求的客户机的IP地址

getRemoteHost方法返回发出请求的客户机的完整主机名

getRemotePort方法返回客户机所使用的网络端口号

getLocalAddr方法返回WEB服务器的IP地址。

getLocalName方法返回WEB服务器的主机名

getMethod得到客户机请求方式

getServerPath()获取请求的文件的路径

 **2.获得客户机请求头**

getHeader(string name)方法 
getHeaders(String name)方法 
getHeaderNames方法 

 **3. 获得客户机请求参数(客户端提交的数据)**
getParameter(name)方法 获取请求中的参数，该参数是由name指定的
getParameterValues（String name）方法 获取指定名称参数的所有值数组。它适用于一个参数名对应多个值的情况。如页面表单中的复选框，多选列表提交的值。

getParameterNames方法 返回一个包含请求消息中的所有参数名的Enumeration对象。通过遍历这个Enumeration对象，就可以获取请求消息中所有的参数名。

getCharacterEncoding() 返回请求的字符编码方式

getAttributeNames()返回当前请求的所有属性的名字集合赋值:setAttribute()

getAttribute(String name) 返回name指定的属性值

getsession()返回和客户端相关的session，如果没有给客户端分配session，则返回null

getParameterMap():返回一个保存了请求消息中的所有参数名和值的Map对象。Map对象的key是字符串类型的参数名，value是这个参数所对应的Object类型的值数组

RequestDispatcher.forward 方法的请求转发过程结束后，浏览器地址栏保持初始的URL地址不变。方法在服务器端内部将请求转发给另外一个资源，浏览器只知道发出了请求并得到了响应结果，并不知道在服务器程序内部发生了转发行为。

request.setCharacterEncoding("utf-8");

getReader() 获取请求体的数据流

getInputStream() 获取请求的输入流中的数据

通过输入输出流获取 ：getInputStream（） 和 getReader（）

在读取的时候通过流对象.read（）方法读取

Eg:

StringBuffer receiveMessage = new StringBuffer();

Scanner scanner = new Scanner(request.getInputStream(), "GBK");

```
while (scanner.hasNext()) {
```

receiveMessage.append(scanner.next());

}

scanner.close();

String json =receiveMessage.toString()

```
JSONObject obj = new JSONObject(json);
```

openId = obj.get("openid").toString();

## **出现乱码的原因和解决**

1． java程序中默认的是中文字符----unicode

2． 系统会把在java程序中的unicode字符按照某种字符集编码的方式转换成字节数组，再通过浏览器输出，浏览器在输出的时候要进行解码，只有在这两种方式一样的情况下，才不会出现乱码。

注:(1)某种字符编码是用reponse对象去设置的，而且必须是在out.println之前使用，要不会出现错误，会抛找不到设置的字符编码而出错。

```
     设置编码的两种方式：

    response.setContentType("text/html;charset=utf-8");

    request.setCharacterEncoding("utf-8");

  (2)浏览器会把字节数组转换成字符
```

1. 系统默认的编码方式为ISO8859-1,如果没有指定字符编码，则输出的都是乱码，而且ISO8859-1不支持中文，所以不管浏览器在解码的时候用的是什么字符集编码，在浏览器上的都是乱码。

**解决办法如下**

**Post方式提交出现乱码**

request.setCharacterEncoding("UTF-8");

请求中之所以会产生乱码，就是因为服务器和客户端沟通的编码不一致造成的，因此解决的办法是：在客户端和服务器之间设置一个统一的编码，之后就按照此编码进行数据的传输和接收。

　	由于客户端是以UTF-8字符编码将表单数据传输到服务器端的，因此服务器也需要设置以UTF-8字符编码进行接收，要想完成此操作，服务器可以直接使用从ServletRequest接口继承而来的"setCharacterEncoding(charset)"方法进行统一的编码设置。使用request.setCharacterEncoding("UTF-8");设置服务器以UTF-8的编码接收数据后，此时就不会产生中文乱码问题了

**Get方式提交出现乱码**

对于以get方式传输的数据，request即使设置了以指定的编码接收数据也是无效的,默认的还是使用ISO8859-1这个字符编码来接收数据，客户端以UTF-8的编码传输数据到服务器端，而服务器端的request对象使用的是ISO8859-1这个字符编码来接收数据，服务器和客户端沟通的编码不一致因此才会产生中文乱码的。

解决办法：在接收到数据后，先获取request对象以ISO8859-1字符编码接收到的原始数据的字节数组，然后通过字节数组以指定的编码构建字符串，解决乱码问题。代码如下：

String name = request.getParameter("name");//接收数据

name =new String(name.getBytes("ISO8859-1"), "UTF-8") ;//获取request对象以ISO8859-1字符编码接收到的原始数据的字节数组，然后通过字节数组以指定的编码构建字符串，解决乱码问题

# **HttpServletResponse**

　　HttpServletResponse继承了ServletResponse接口，并提供了与Http协议有关的方法，这些方法的主要功能是设置[HTTP状态码](http://baike.baidu.com/view/1790469.htm)和管理Cookie。HttpServletResponse对象代表服务器的响应。这个对象中封装了向客户端发送数据、发送响应头，发送响应状态码的方法

HttpServletResponse对象可以向客户端发送三种类型的数据:

a.**响应头****(**Response headers**)**

**b.状态码****(**Protocol—Status code—Description**)**

**c****.****实体****数据****(**Entity body **)**

举例如下：

HTTP/1.1 200 OK

Server: Microsoft-IIS/4.0

Date: Mon, 5 Jan 2004 13:13:33 GMT

Content-Type: text/html

Last-Modified: Mon, 5 Jan 2004 13:13:12 GMT

Content-Length: 112

<html><head><title>HTTP Response Example</title></head>....</html>

## **常用方法**

addHeader(String name,String value)  将指定的名字和值加入到响应的头信息中

encodeURL(String url)  编码指定的URL

sendError(int sc)  使用指定状态码发送一个错误到客户端

setDateHeader(String name,long date  将给出的名字和日期设置响应的头部

setHeader(String name,String value)  将给出的名字和值设置响应的头部

setStatus(int sc)  给当前响应设置状态码

HttpServletResponse.sendRedirect 方法对浏览器的请求直接作出响应，响应的结果就是告诉浏览器去重新发出对另外一个URL的访问请求；方法调用者与被调用者使用各自的request对象和response对象，它们属于两个独立的访问请求和响应过程。

 response.setContentType("text/html;charset=utf-8");

setContentType(String ContentType)  设置响应的MIME类型 ,页面的设置文本类型,获取或设置输出流的 HTTP MIME 类型。输出流的 HTTP MIME 类型。默认值为“text/html”。

MIME类型就是设定某种扩展名的文件用一种应用程序来打开的方式类型，当该扩展名文件被访问的时候，浏览器会自动使用指定应用程序来打开。多用于指定一些客户端自定义的文件名，以及一些媒体文件打开方式。

使用输出流输出一张图片的时候,比如做验证码图片的时候 如果在Firefox中直接浏览验证码是乱码,放在<img>里面则不会

这时候就要事先指定Response.ContentType = "image/jpeg";//设定MIME类型

response.setHeader(“Refresh”,”2;url=”http://www.baidu.com”); 页面的刷新

消息实体内容 通过输出流对象进行设置,用以下两个方法：

Response.getOutputStream()  字节输出流对象

Response.getWriter()   字符的输出流对象

 **getOutputStream和getWriter方法的比较**

（1）getOutputStream方法用于返回Servlet引擎创建的字节输出流对象，Servlet程序可以按字节形式输出响应正文。

（2）getWriter方法用于返回Servlet引擎创建的字符输出流对象，Servlet程序可以按字符形式输出响应正文。

（3）getOutputStream和getWriter这两个方法互相排斥，调用了其中的任何一个方法后，就不能再调用另一方法。要不会出现错误java.lang.IllegalStateException

（4）getOutputStream方法返回的是字节输出流对象的类型为ServletOutputStream，它可以直接输出字节数组中的二进制数据。

（5）getWriter方法将Servlet引擎的数据缓冲区包装成PrintWriter类型的字符输出流对象后返回，PrintWriter对象可以直接输出字符文本内容。

（6）Servlet程序向ServletOutputStream或PrintWriter对象中写入的数据将被Servlet引擎获取，Servlet引擎将这些数据当作响应消息的正文，然后再与响应状态行和各响应头组合后输出到客户端。

（7）Serlvet的service方法结束后，Servlet引擎将检查getWriter或getOutputStream方法返回的输出流对象是否已经调用过close方法，如果没有，Servlet引擎将调用close方法关闭该输出流对象。

注：out.close();系统会自己释放，但一般写上

**选择getOutputStream和getWrite方法的要点**

（1）PrintWriter对象输出字符文本内容时，它内部还是将字符串转换成了某种字符集编码的字节数组后再进行输出，使用PrintWriter对象的好处就是不用编程人员自己来完成字符串到字节数组的转换。

（2）使用ServletOutputStream对象也能输出内容全为文本字符的网页文档，但是，如果网页文档内容是在Servlet程序内部使用文本字符串动态拼凑和创建出来的，则需要先将字符文本转换成字节数组后输出。

（3）如果一个网页文档内容全部为字符文本，但是这些内容可以直接从一个字节输入流中读取出来，然后再原封不动地输出到客户端，那么就应该使用ServletOutputStream对象直接进行输出，而不要使用PrintWriter对象进行输出。   

## **向客户端写入中文**

**使用OutputStream向客户端写入中文:**

String data = "中国";

OutputStream stream = response.getOutputStream();//获取一个向Response对象写入数据的流,当tomcat服务器进行响应的时候，会将Response中的数据写给浏览器

stream.write(data.getBytes("UTF-8"));//此时在html页面会出现乱码,这是因为：服务器将"中国"按照UTF-8码表进行编码，得到对应的码值假设是98,99,服务器将码值发送给浏览器.浏览器默认按照GB2312进行解码,在GB2312码表中对应的字符已不是"中国"

**正确代码如下:**

response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8

 String data = "中国";

 OutputStream stream = response.getOutputStream();

stream.write(data.getBytes("UTF-8"));

**使用PrintWriter向客户端写入中文:**

PrintWriter writer = response.getWriter();

writer.write("中国");

//同样会出现乱码，这是因为我们将"中国"写入response对象时,tomcat服务器为了将数据通过网络传输给浏览器，必须进行编码,由于没有指定编码方式，默认采用ISO8859-1,当浏览器接收到数据后，根据GBK解码必然出现乱码

 **正确代码如下:**

response.setCharacterEncoding("UTF_8");//设置Response的编码方式为UTF-8

response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8,其实设置了本句，也默认设置了Response的编码方式为UTF-8，但是开发中最好两句结合起来使用 ,设置响应头，控制浏览器以指定的字符编码编码进行显示，

 //response.setContentType("text/html;charset=UTF-8");同上句代码作用一样

 PrintWriter writer = response.getWriter();

writer.write("中国");

　　**在获取PrintWriter输出流之前首先使用"response.setCharacterEncoding(charset)"设置字符以什么样的编码输出到浏览器，如：response.setCharacterEncoding("UTF-8");设置将字符以"UTF-8"编码输出到客户端浏览器，然后再使用response.getWriter();获取PrintWriter输出流，这两个步骤不能颠倒**

## **使用Response实现文件下载:**

　文件下载功能是web开发中经常使用到的功能，使用HttpServletResponse对象就可以实现文件的下载

文件下载功能的实现思路：

　　1.获取要下载的文件的绝对路径

　　2.获取要下载的文件名

　　3.设置content-disposition响应头控制浏览器以下载的形式打开文件

　　4.获取要下载的文件输入流

　　5.创建数据缓冲区//缓冲区解释见下文

　　6.通过response对象获取OutputStream流

　　7.将FileInputStream流写入到buffer缓冲区

8.使用OutputStream将缓冲区的数据输出到客户端浏览器

案例1

private void downloadFileByOutputStream(HttpServletResponse response){

```
     //1.获取要下载的文件的绝对路径

     String realPath = this.getServletContext().getRealPath("/download/1.JPG");

     //2.获取要下载的文件名

     String fileName = realPath.substring(realPath.lastIndexOf("\\")+1);

     //3.设置content-disposition响应头控制浏览器以下载的形式打开文件

     response.setHeader("content-disposition", "attachment;filename="+fileName);

     //4.根据文件路径获取要下载的文件输入流

    InputStream in = new FileInputStream(realPath);

     int len = 0;

     //5.创建数据缓冲区

     byte[] buffer = new byte[1024];

     //6.通过response对象获取OutputStream流

     OutputStream out = response.getOutputStream();

     //7.将FileInputStream流写入到buffer缓冲区         while ((len = in.read(buffer)) > 0) {

     //8.使用OutputStream将缓冲区的数据输出到客户端浏览器

         out.write(buffer,0,len);

     }

     in.close();

 }
```

案例2

@RequestMapping("/download")

public void download(HttpServletRequest req,HttpServletResponse res){

String fileName = "plcdmb.xls";//要下载的文件名

 	//1.获取要下载的文件的绝对路径

String realPath = req.getSession().getServletContext().getRealPath("/wbms/download");

File file=new File(realPath+"/"+fileName); //设置content-disposition响应头控制浏览器以下载的形式打开文件

```
    res.setCharacterEncoding("utf-8");

    res.setContentType("application/octet-stream");

    res.setHeader("Content-Disposition", "attachment;fileName="+ URLEncoder.encode("批量出单模板.xls", "UTF-8"));

    InputStream inputStream=new FileInputStream(file);根据路径获取要下载的文件输入流 

    OutputStream out = res.getOutputStream();

    byte[] b=new byte[1024];  //创建数据缓冲区

        int length;  

        while((length=inputStream.read(b))>0){  把文件流写到缓冲区里

        	out.write(b,0,length);  

        }  

        out.flush();

        out.close();

        inputStream.close();
```

 

}

**在编写下载文件功能时，要使用OutputStream流，避免使用PrintWriter流，因为OutputStream流是字节流，可以处理任意类型的数据，而PrintWriter流是字符流，只能处理字符数据，如果用字符流处理字节数据，会导致数据丢失。**

## **其他**

**在jsp嵌套的java代码中执行js**

<%

//保存登录信息

Cookie[] cookies=request.getCookies();//从request中获得cookie对象的集合  

String phone="";//电话号

String state="";//

if(cookies!=null){  

```
for(int i=0;i<cookies.length;i++){  

    if(cookies[i].getName().equals("state")){  

        state=cookies[i].getValue();

           if(state.equals("cont_failed")){

           out.write("<script language='javascript'> alert('hello'); </script>;");

           }                                                           

    }  

}                                                                 
```

}

 %>

**getWriter()****输出js代码的案例**

1.res.getWriter().write("<script language=\"javascript\">location.href='"+req.getContextPath()+"/wbms/ecm//init.action';</script>");//在原页面输出

res.getWriter().flush();

2.response.getWriter().write("<script language='javascript'>alert('请上传正确格式的文件！！！');window.history.back();</script>");

3.res.getWriter().flush();

如果不使用这种形式,传值用request.setAttribute()来传值跳转用重定向或者转发页面取值可以用jstl的$()取值可以在input标签的value中使用$()取值

# **ServletRequest****与****ServletResponse**

**ServletRequest**

代表一个HTTP请求，请求在内存中是一个对象，这个对象是一个容器，可以存放请求参数和属性。

请求对象何时被创建，当通过URL访问一个JSP或者Servlet的时候，也就是当调用Servlet的service()、doPut()、doPost()、doXxx()方法时候的时候，执行Servlet的web服务器就自动创建一个ServletRequest和ServletResponse的对象，传递给服务方法作为参数。

请求对象由Servlet容器自动产生，这个对象中自动封装了请求中get和post方式提交的参数，以及请求容器中的属性值，还有http头等等。当Servlet或者JSP得到这个请求对象的时候，就知道这个请求时从哪里发出的，请求什么资源，带什么参数等等。通过请求对象，可以获得Session对象和客户端的Cookie。请求需要指定URL，浏览器根据URL生成HTTP请求并发送给服务器.

**ServletResponse**

也是由容器自动创建的，代表Servlet对客户端请求的响应，响应的内容一般是HTML，而HTML仅仅是响应内容的一部分。请求中如果还包含其他资源会依次获取，如页面中含有图片，会进行第二个http请求用来获得图片内容。

相应对象有以下功能：

1、向客户端写入Cookie

2、重写URL

3、获取输出流对象，向客户端写入文本或者二进制数据

4、设置响应客户端浏览器的字符编码类型

5、设置客户端浏览器的MIME类型。

# **一个简单的 servlet 容器**

Servlet 接口需要实现下面的 5 个方法：

public void init(ServletConfig config) throws ServletException

public void service(ServletRequest request, ServletResponse response) throws ServletException,

java.io.IOException

public void destroy()

public ServletConfig getServletConfig()

public java.lang.String getServletInfo()

在某个 servlet 类被实例化之后，init 方法由 servlet 容器调用。servlet 容器只调用该方法一次，调用后则可以执行服务方法了。在 servlet 接收任何请求之前，必须是经过正确初始化的。

当一个客户端请求到达后，servlet 容器就调用相应的 servlet 的 service 方法，并将 Request 和 Response对象作为参数传入。在 servlet 实例的生命周期内，service 方法会被多次调用。

在将 servlet 实例从服务中移除前，会调用 servlet 实例的 destroy 方法。一般情况下，在服务器关闭前，会发生上述情况，servlet 容器会释放内存。只有当 servlet 实例的 service 方法中所有的线程都退出或执行超时后，才会调用 destroy 方法。当容器调用了destroy 方法后，就不会再调用 service 方法了。

下面从 servlet 容器的角度观察 servlet 的开发。在一个全功能 servlet 容器中，对 servlet 的每个 HTTP 请求来说，容器要做下面几件事：

1.当第一次调用 servlet 时，要载入 servlet 类，调用 init 方法（仅此一次）；

2.针对每个 request 请求，创建一个 Request 对象和一个 Resposne 对象；

3.调用相应的 servlet 的 service 方法，将 Request 对象和 Response 对象作为参数传入；

4.当关闭 servlet 时，调用 destroy 方法，并卸载该 servlet 类。

## **输出缓冲区**

**1．输出缓冲区的介绍**

（1）Servlet程序输出的HTTP消息的响应正文首先被写入到Servlet引擎提供的一个输出缓冲区中，直到输出缓冲区被填满或者Servlet程序已经写入了所有的响应内容，缓冲区中的内容才会被Servlet引擎发送到客户端。

（2）使用输出缓冲区后，Servlet引擎就可以将响应状态行、各响应头和响应正文严格按照HTTP消息的位置顺序进行调整后再输出到客户端。

（3）如果在提交响应到客户端时，输出缓冲区中已经装入了所有的响应内容，Servlet引擎将计算响应正文部分的大小并自动设置Content-Length头字段。

（4）如果在提交响应到客户端时，输出缓冲区中装入的内容只是全部响应内容的一部分，Servlet引擎将使用HTTP1.1的chunked编码方式（通过设置Transfer-Encoding头字段来指定）传输响应内容。

**输出缓冲区的有关方法**  

```
  System.out.println(response.getBufferSize());    //读取缓存区的大小

  response.setBufferSize(1024);  //设置缓冲区的的大小,会小与你设置的值

  System.out.println(response.getBufferSize());

  int len=response.getBufferSize();  //填满缓冲区

  for(int i =0;i<len;i++){

     System.out.println("w");

  }
```

技术改变世界
