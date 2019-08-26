/*创建表c1*/

 

CREATE TABLE c1 (

​    id INT,

​    sex VARCHAR (**10**),

​    score INT

);

 

/*插入数据*/

INSERT INTO  c1

VALUES

​    (**1001**, '男' ,**65**),

​    (**1002**, '男' ,**75**),

​    (**1003**, '女' ,**82**),

​    (**1004**, '女' ,**70**),

​    (**1005**, '男' ,**89**),

​    (**1006**, '女' ,**90**),

​    (**1007**, '男' ,**85**);

 

SELECT * FROM c1;



 

/*将性别男、女分别用0、1替换*/

 

SELECT

​    id,

​    CASE 

​        WHEN sex = '男' THEN **0**

​        WHEN sex = '女' THEN **1** END sex ,

​    score

FROM c1;

 

/*将成绩分为小于60 差，60~80 中，80~90良，90以上优*/

 

SELECT * ,

​    CASE 

​        WHEN score<**60** THEN '差'

​        WHEN score>=**60** AND score<**80** THEN '中'

​        WHEN score>=**80** AND score<**90** THEN '良'

​        WHEN score>=**90**  THEN '优' END level

FROM c1;

 

/*将成绩等级分为四列*/

SELECT id, sex,score,

​    CASE  WHEN score<**60** THEN '差' END AS level1,

​    CASE  WHEN score>=**60** AND score<**80** THEN '中' END AS level2,

​    CASE  WHEN score>=**80** AND score<**90** THEN '良' END AS level3,

​    CASE  WHEN score>=**90**  THEN '优'END  AS level4

FROM c1;

 

/*按照性别计算各成绩等级中的数量*/

 

SELECT sex,

​    SUM(CASE WHEN score<**60** THEN **1** ELSE **0** END) AS '差',

​    SUM(CASE WHEN score>=**60** AND score<**80** THEN **1** ELSE **0** END) AS '中',

​    SUM(CASE WHEN score>=**80** AND score<**90** THEN **1** ELSE **0** END) AS '良',

​    SUM(CASE WHEN score>=**90** THEN **1** ELSE **0** END) AS '优'

FROM c1

GROUP BY sex;

 
