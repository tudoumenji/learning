Student student = mapper.readValue(jsonString, Student.class); 
是将string串转化为对象

jsonString = mapper.writeValueAsString(student); 
是将对象转化为json格式的字符串。
