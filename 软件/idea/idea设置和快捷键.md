#### 激活码

http://idea.medeming.com/jetbrains/ 

http://idea.lanyus.com

#### 设置(尽量在welcome页面设置，对所有新建项目生效)

- edit custom VM Options：（使用 IDEA 自带菜单中的 Help -> Edit Custom VM Options，直接修改配置文件可能无效）-Xms1024m || -Xmx2048m || -XX:ReservedCodeCacheSize=960m

- 菜单栏 -> view -> appearance -> 勾选toolbar

- font ->  font:DialogInput  ,  size:18

- system settings ->  勾掉Reopen last project on startup，启动时进入选择项目界面

- editor tabs -> tabplacement : left ,  tab limit : 50  左侧显示文件tab栏

- code completion -> 勾掉match case 取消大小写敏感

- file encoding -> globle encoding : utf-8 ,  project encoding -> utf-8 , 

  default encoding for properties files :  utf-8  ,   BOM : with no BOM 

- editor -> general -> 勾选show quick documentation on mouse move  500  鼠标停留显示文档

- maven -> importing -> 勾选import maven project automatically    ,    (可选)automatically download勾选source和documentation

- editor -> general -> auto import -> 勾选add unambiguous imports on the fly , 勾选optimize imports on the fly(for current project) , 自动增加和删除maven import

- 设置快捷键Ctrl + Shift +  s为编译项目（个人习惯），代替Ctrl + F9

- annotation processors -> 勾选enable annotation processing，确保@Data等注释起作用

- 安装git   D:\software\git\Git\bin\git.exe

- 安装maven     maven home：D:/software/maven/apache-maven-3.6.1  ， setting file: D:\software\maven\apache-maven-3.6.1\conf\settings.xml

- 安装tomcat   参数-Dfile.encoding=UTF-8

- (可选)  Diagram -> java class diagram  勾选需要的选项，生成对应的UML类图

- 取消导包合并成* ->code style ->java ->imports ->class count to use import with * :500  和 names count to use static import with * :500 



#### 其他

- run dashboard 当多module时可集中控制
- 右键点击diagram , 生成UML类图，显示内容可在setting -> Diagram -> java class diagram里设置



#### 插件

- Lombok
- Translation
- Vue.js
- checkstyle
- findbugs
- alibaba p3c



#### 快捷键

https://juejin.im/post/5a90810b6fb9a063606eefe0

编辑

- Alt + Shift   智能修正错误
- Ctrl + Space   补全代码，一般默认自动补全
- Ctrl + Alt + Space   智能补全，在写函数接口和lamda表达式时特别好用
- Ctrl + P  方法参数列表
- Alt + Insert   生成与类相关的代码，例如生成构造方法、getter and setter、copyright
- Ctrl + O  该类中所有可以覆盖或者实现的方法列表
- Ctrl + Alt + T   生成具有环绕性质的代码，例如if/else、try/catch/finally
- Ctrl + Alt +  shift + T  重构，例如rename类名、方法名，重构一个方法
- Ctrl + W   选中当前单词
- Ctrl + Alt + L   格式化代码
- Ctrl + D   复制当前光标所在的代码行
- Ctrl + Y    删除当前光标所在的代码行
- Shift + Enter   新启一行
- Ctrl + /   行注释  //
- Ctrl + Shift + /   块注释   /**/
- Ctrl + Shift + U   所选择的内容进行大小写转换
- 复制当前行   1、按home/end将 光标移到当前行首/行末；2、按住shift+end/shift+home，选中光标到行末/行首；3、ctrl + c 复制

查找和替换

- Shift + Shift   find everything！！ 包括寻找类名、文件名、IDE的设置
- Ctrl + F  在当前标签页中进行查找，支持正则表达式
- Ctrl + R  在当前标签页中进行替换操作
- Ctrl + Shift + F  在所有文件中查找相应的文本
- Ctrl + Shift + R  通过路径替换（没用过）
- Alt + F7   打开当前类中的使用列表
- Ctrl + Alt + F7   打开所有的使用列表
- Ctrl + H   打开选中类的继承数结构

编译与运行

- Ctrl + Shift + F10  运行当前程序
- Ctrl + Shift +  s    编译项目。注：系统并没有此快捷键，需设置，和Ctrl + F9功能一样

调试

- F8   跳到当前代码下一行
- F9   如果有下一个断点会跳到下一个断点中；没有下一个断点，则结束本轮调试
- F7   跳入到调用的方法内部代码
- Shift + F7   Smart step into，会打开一个面板，让你选择具体要跳入的类方法，这个在复杂的嵌套代码中特别有用
- Alt + F9   让代码运行到当前光标所在处

导航

- Ctrl + Alt + 右箭头/左箭头    在曾经浏览过的代码行中来回跳
- Ctrl + E   打开曾经操作过的文件历史列表
- Ctrl + G   跳转至某一行代码
- Ctrl + 鼠标左键   跳转到当前光标所在的类定义或者接口
- Ctrl + Alt + 鼠标左键   跳转到已实现此接口或继承的类
- F11    添加为书签或者从书签中删除。添加 Favorites 面板：View -> Tool Windows -> Favorites




