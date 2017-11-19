CRUD
====
[安装使用图文说明](https://mp.weixin.qq.com/s?__biz=MzI1MTAwMTI2NA==&mid=2656402150&idx=1&sn=0ae818d2984e51e22cb79f54c81a7d42&scene=21#wechat_redirect)

CRUD is Really Urgly coDed -- 快速原型系统与通用后台

默认账号admin 密码admin

支持导入xls文件，直接生成表格，若表格已存在则导入数据。

支持查看界面，双击修改数据。


支持超文本编辑、文件上传。

新增权限管理，guest用户，显示内容单页含二维码。

数据库配置文件在WEB-INF/db.prop

![image](https://github.com/zhblue/crud/blob/master/crud/crud.png)

约定如下：

1、每个表必须有id，自增类型

2、每个表id后的第一个字符型字段被当做外键关联的显示值

3、外键一律以表名_id进行命名。

datadic表用于翻译英文表名列名到中文,建表语句在db.sql。

除config/datadic/privilege/user四张系统表以外，样例表可以删除。
符合上述原则设计表，自动实现菜单、各表增删改，用于快速建立小型系统原型。

新增用户没有权限，权限表里增加记录，rightstr格式为[表名/类]权限/方法。

如：
[config]read为读取config表权限，[]admin为管理员权限。

[com.newsclan.crud.Tools]update 表示允许调用com.newsclan.crud.Tools.update方法。

正常连接数据库后，新建符合上述要求的数据表，刷新页面会自动出现新的菜单，点击可以展开数据进行增删改。权限可以为insert/read/update/delete/upload等

需要中文含义，在数据字典表中进行添加即可，刷新页面立刻生效。

date/timestamp类型的自动会激活日期时间控件。

text类型自动以CKEditor进行编辑，支持上传图片与附件。

以_file结尾的varchar字段可以上传文件，并记录路径。

_id结尾的外键，自动显示为下拉选择。

在config/配置中增加记录，以“报表”两字结尾的记录将出现在报表菜单中。value里可以自定义sql，START_DATE END_DATE用于日期筛选，USER_ID用于用户筛选。



![image](https://github.com/zhblue/crud/blob/master/crud/demo1.png)
![image](https://github.com/zhblue/crud/blob/master/crud/demo2.png)
![image](https://github.com/zhblue/crud/blob/master/crud/demo3.png)
![image](https://github.com/zhblue/crud/blob/master/crud/demo4.png)
