```java
@ControllerAdvice
public class ExceptionHandle extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(HttpServletRequest request, Exception e) {

        //request.getRequestURI(): 发送过来的请求 request.getParameterMap():获取请求参数
        String requestURI = String.valueOf(request.getRequestURL());
        //e.getClass().getName():具体除了什么错误
        String exceptionType = e.getClass().getName();
        //e.getStackTrace()[0]:错误类、方法、行号
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        String resultMessage = requestURI + "------>" + exceptionType + "------>" + stackTraceElement.toString();
        //输出结果：http://localhost:8080/students------>java.lang.NullPointerException------>com.example.demo01.controller.controller.students(controller.java:51)
        logger.error(resultMessage, e);
        /*也可返回对象
        Student student = new Student();
        student.setId(1);*/
        return resultMessage;
    }
}
```



全局处理类优先级最高
