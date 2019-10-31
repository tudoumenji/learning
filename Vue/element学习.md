标准格式

```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>test</title>
    <!-- import CSS -->
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
</head>
<body>
<div id="app">
    <div>{{message}}</div>
</div>
</body>
<!-- import Vue before Element -->
<script src="https://unpkg.com/vue/dist/vue.js"></script>
<!-- import JavaScript -->
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<script>
    var app = new Vue({
        el: '#app',
        data: {
            message: 'Hello Vue!'
        }
    });
</script>
</html>
```



```
vueApp.$refs.firstInput.select()  =>获取vueApp对象里ref属性是firstInput的组件，执行此组件的select方法
```

vue内部使用   this.$refs.(ref=“xx”,组件的ref属性名).(组件方法)

vue外部使用   app.$refs.(ref=“xx”,组件的ref属性名).(组件方法)



element获取组件数据基本可以从事件的回调函数的参数里获取，easyui获取组件数据是从组件的方法里获取

