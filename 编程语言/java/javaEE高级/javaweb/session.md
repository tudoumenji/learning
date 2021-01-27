**课程介绍****:** 

学习 Cookie 之后，解决了不用发送请求的数据共享问题。Cookie 是 

浏览器端的数据存储技术，本节课重点介绍另外一门重要的数据存储 

技术，session 技术。 

**Session** **学习****:** 

问题：

Request 对象解决了一次请求内的不同 Servlet 的数据共享问 

题，那么一个用户的不同请求的处理需要使用相同的数据怎么办呢? 

解决：

使用 session 技术。 

原理：

用户使用浏览器第一次向服务器发送请求，服务器在接受到请 

求后，调用对应的 Servlet 进行处理。在处理过程中会给用户创建 

一个 session 对象，用来存储用户请求处理相关的公共数据，并将 

此 session 对象的 JSESSIONID 以 Cookie 的形式存储在浏览器中 

(临时存储，浏览器关闭即失效)。用户在发起第二次请求及后续请 

求时，请求信息中会附带 JSESSIONID，服务器在接收到请求后， 

调用对应的 Servlet 进行请求处理，同时根据 JSESSIONID 返回其 

对应的 session 对象。 

特点：Session 技术是依赖 Cookie 技术的服务器端的数据存储技术。 

由服务器进行创建 

每个用户独立拥有一个 session 

默认存储时间为 30 分钟 

作用：

解决了一个用户的不同请求的数据共享问题。 

使用：

创建 Session 对象 

存储数据到 session 对象 

获取 session 对象 

获取数据从 session 对象 

如果获取 session 中不存在的数据返回 null。 

注意：

只要不关闭浏览器，并且 session 不失效的情况下，同一个用 

户的任意请求在项目的任意Servlet中获取到的都是同一个session 

对象。 

作用域： 

一次会话 

案例：

登录练习使用，详见源码





```java
package com.bjsxt.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * session技术学习:
 * 		问题：
 * 			一个用户的不同请求处理的数据共享怎么办？
 * 		解决：
 * 			使用session技术
 * 		原理：
 * 			用户第一次访问服务器，服务器会创建一个session对象给此用户，并将
 * 			该session对象的JSESSIONID使用Cookie技术存储到浏览器中，保证
 * 			用户的其他请求能够获取到同一个session对象，也保证了不同请求能够获取到
 * 			共享的数据。
 * 		特点：
 * 			存储在服务器端
 * 			服务器进行创建
 * 			依赖Cookie技术
 * 			一次会话
 * 			默认存储时间是30分钟
 *		作用：
 *			解决了一个用户不同请求处理的数据共享问题
 *		使用：
 *			创建session对象/获取session对象
				HttpSession hs=req.getSession();
				如果请求中拥有session的标识符也就是JSESSIONID，则返回其对应的session队形
				如果请求中没有session的标识符也就是JSESSIONID，则创建新的session对象，并将其JSESSIONID作为从cookie数据存储到浏览器内存中
 * 				如果session对象是失效了，也会重新创建一个session对象，并将其JSESSIONID存储在浏览器内存中。
 * 			设置session存储时间
 * 				hs.setMaxInactiveInterval(int seconds);
 * 				注意：
 * 					在指定的时间内session对象没有被使用则销毁，如果使用了则重新计时。
 * 			设置session强制失效
 * 				hs.invalidate();
 * 			存储和获取数据
 * 				存储：hs.setAttribute(String name,Object value);
 * 				获取：hs.getAttribute(String name) 返回的数据类型为Object
 * 				注意：
 * 					存储的动作和取出的动作发生在不同的请求中，但是存储要先于取出执行。
 * 			使用时机:
 * 				一般用户在登陆web项目时会将用户的个人信息存储到Sesion中，供该用户的其他请求使用。
 * 			总结：
 * 				session解决了一个用户的不同请求的数据共享问题，只要在JSESSIONID不失效和session对象不失效的情况下。
 * 				用户的任意请求在处理时都能获取到同一个session对象。
 * 			作用域：
 * 				一次会话
 * 				在JSESSIONID和SESSION对象不失效的情况下为整个项目内。
 * 			session失效处理：
 * 				将用户请求中的JSESSIONID和后台获取到的SESSION对象的JSESSIONID进行比对，如果一致
 * 				则session没有失效，如果不一致则证明session失效了。重定向到登录页面，让用户重新登录。
 * 		注意：
 * 			JSESSIONID存储在了Cookie的临时存储空间中，浏览器关闭即失效。
 * 
 * @author MyPC
 *
 */
public class SessionServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//设置请求编码格式
		req.setCharacterEncoding("utf-8");
		//设置响应编码格式
		resp.setContentType("text/html;charset=utf-8");
		//获取请求信息
			String name="张三";
		//处理请求信息
			//创建session对象
			HttpSession hs=req.getSession();
			//设置session的存储时间
				//hs.setMaxInactiveInterval(5);
			System.out.println(hs.getId());
			//设置session强制失效
				//hs.invalidate();
			//存储数据
				hs.setAttribute("name",name);
		//响应处理结果
			//直接响应
			resp.getWriter().write("session学习");
			//请求转发
			//重定向
	}
}

```

