https://blog.csdn.net/qq_31424825/article/details/78127378

#### Thymeleaf_vue_springboot

javascript访问model中的数据，需thymeleaf进行内联th:inline，[[${对象}]]进行取值。

注意：目前测试[[${对象}]]中的对象为string字符串，可用json

```
<script th:inline="javascript">
    ...
    var username = [[${session.user.name}]];
    ...
</script>
```

java代码

```java
package com.example.springbootsamplehelloworld;
 
 
import java.util.HashMap;
import java.util.Map;
 
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
 
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
 
 
 
 
@Controller
public class GreetingController {
 
 
 
 
     ObjectMapper mapper = new ObjectMapper();  
     String json = "";  
 
 
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name,String password) throws JsonProcessingException {
 
 
   <!-- 接受前端传递过来的数据 -->
        Map<String, String>bjson =new HashMap<String, String>();
        bjson.put("name", name);
        bjson.put("password", password);
        json = mapper.writeValueAsString(bjson);  
 
 
        System.out.println(json);
        return "greeting";
    }
 
 
    @RequestMapping("/user")  
    public String user(Model model){  
 
 
        User user = new User("nick","nick@qq.com");
 
 
        model.addAttribute("user", user);
        model.addAttribute("name", "elaine");
 
 
 
 
        return "user";  
    }
 
 
    @RequestMapping("/")  
    public String index(Model model){  
        return "index";  
    }
 
 
}
```

----------------------------------------------

前端页面代码

```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width"/>
    <title>Getting Started: Serving Web Content</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.1.8/vue.min.js"></script>
    <script src="//cdn.bootcss.com/vue-resource/1.0.3/vue-resource.min.js"></script>
    <style type="text/css">
    body{
       text-align:center;
    }
    </style>
</head>
<body>
    <h2 style="margin-top:50px">LOGIN IN </h2>
 
 
    <div id="datashow">{{datashow}}</div>
    <input type="text" id="username" style="margin-top:30px" v-model="username" /><br></br>
    <input type="password" id="password" style="margin-top:15px" v-model="password"/><br></br>
    <button id="logindiv" class="button" v-on:click="loginbtn" style="margin-top:20px">login in</button>
 
 
</body>
 
 
    <script th:inline="javascript">
 
 
Vue.http.options.emulateJSON = true; 
Vue.http.options.emulateHTTP = true;
 
 
    var name = [[${name}]]; 
    var user = [[${user}]]; 
    <!-- 将后端传输过来的值展示在datashow -->
      var showvm = new Vue({
        el:'#datashow',
          data:{
            datashow:name,
          }
      })
 
 
<!-- 接受username值 -->
      var uservm = new Vue({
        el:'#username',
          data:{
              username:'',
          }
      })
<!-- 接受password值 -->
  var pswvm = new Vue({
    el:'#password',
    data:{
        password:'',
    }
  })
      var logindiv = new Vue({
       el: '#logindiv',
       data: {
       name: 'Vue.js'
      },
     // 在 `methods` 对象中定义方法
      methods: {
      loginbtn: function (event) {
      // `this` 在方法里指当前 Vue 实例
       <!-- 打印pswvm.password -->
       console.log(pswvm.password);
 
 
    <!-- 将name:uservm.username,password:pswvm.password传输到后台，调用greeting -->
   <!-- Vue post -->
    this.$http.post("/greeting?", 
            {name:uservm.username,password:pswvm.password}
       )
       .then(function (response) { 
       }).catch(function (response)
        { console.error(response); 
        });
 
 
     }
    }
   })
// 也可以用 JavaScript 直接调用方法
// logindiv.loginbtn() // => 'Hello Vue.js!'
 
 
    </script>
 
 
</html>

```

