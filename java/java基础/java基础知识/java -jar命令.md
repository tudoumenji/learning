https://blog.csdn.net/csdner999/article/details/39232115

现在我们来体验一下jar里manifest文件的作用，如果现在我们有一个Java 应用程序打包在myapplication.jar中， main class为 com.example.myapp.MyAppMain ，那么我们可以用以下的命令来运行 
java -classpath myapplication.jar com.example.myapp.MyAppMain
这显然太麻烦了，现在我们来创建自己的manifest文件，如下：
Manifest-Version: 1.0
Created-By: JDJ example
Main-Class: com.example.myapp.MyAppMain
这样我们就可以使用如下的命令来运行程序了：（明显简单多了，也不会造成无谓的拼写错误）
java -jar myapplication.jar