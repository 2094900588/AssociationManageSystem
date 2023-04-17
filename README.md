- # 社团信息管理系统项目计划书
  
  ## 一、引言
  
  1. 1	编写目的
  
  ​	为了保证该项目能够在我们团队的努力下保质保量的完成，并且能够达到目标，我们编写该项目开发计划书 ，使得项目开发人员对该软件系统的开发做一个合理、全面的规划，在整个开发过程中所有开发人员能够明白自己的任务和开发期限，我们决定以文档的形式把工作进行分解标注，使得队员之间能流畅的开发完本系统。
  
  ​	1.2	背景
  
  ​	三峡大学科技学院社团信息管理系统是对于校园内社团信息管理繁杂，没有一个合适的工具进行管理，所以本小组决定开发一个帮助社团管理者进行管理的信息系统，主要功能是实现社团的信息化管理，包括社团信息管理，社团社员信息管理，社团活动信息管理，社团管理者信息管理，社团角色信息管理等功能。
  
  项目名称：社团信息管理系统
  
  项目提出者：邓闯、李新春、李欣泽
  
  项目开发者：邓闯、李新春、李欣泽
  
  项目用户：三峡大学科技学院社团/组织/协会
  
  项目实现场所：个人电脑
  
  社团信息管理系统是学校信息管理的重要组成部分，它能够帮助学校更好的管理学校学生的信息。
  
  2. 项目概述
  
  ​	随着当代大学生的知识水平提高，多才多艺的学生越来越多，各类社团越来越受欢迎，加入的人也越来越多，为了让学校合理的管理社团，让社团合理的管理社员信息，并且由于现在信息技术越来越发达，很多学生的信息遭到泄露，所以这时社团信息管理系统是急需的工具。
  
  社团管理系统的主要实现功能如下：
  
  - 用户登录验证
  - 学校管理员可以管理社团相关信息
  - 社团管理员可以管理本社团相关信息
  - 社团管理员可以为本社团添加社团活动
  - 社团可以提交文件本社团文件，可以对文件进行增删改查等操作
  - 社团管理员可以查看本社团的社员信息操作日志
  - 学校管理员可以审批社团活动与评分
  - 学校管理员可以查看所有社团社员信息操作记录
  - 用户可以修改信息，修改密码
  
  开发时间：二个星期
  
  开发工具：IDEA+VSCode
  
  ​	2.1	工作内容
  
  ​	在本项目的开发中须进行的主要工作：进行可行性研究，制定项目开发计划书，进行需求分析，进行概要设计，进行详细设计，进行用户界面设计，编码，测试，编写文档，运行与维护，项目实施总结。
  
  ​	2.2	主要参与人员
  
  团队成员：邓闯、李新春、李欣泽
  
  ​	2.3	使用主要技术栈
  
  前端：Vue3、Element-ui 
  
  后端：SringBoot、MyBatis-plus
  
  数据库：Mysql
  
  3. ​	数据库设计
  
  ​	3.1	数据库
  
  社团信息管理系统：ams_sys
  
  ​	3.2.1	用户表(sys_user)
  
  用户编号:	id（主键）(自动增长)
  
  账号:	username（唯一）
  
  密码:	password
  
  昵称：nickname（备注如学校管理员，xx社团管理员）
  
  邮箱：email
  
  电话：phone
  
  地址：address
  
  头像：userphoto
  
  账号使用者：name
  
  账号使用者学号：studentid
  
  社团ID：clubid
  
  角色ID：roleid
  
  ​	3.2.2	社员表(sys_am)
  
  社员编号：id（主键）（自动增长）
  
  社员学号：studentid（默认是唯一的）
  
  社员名称：amname
  
  社员电话：phone
  
  社员政治面貌：status（党员，预备党员，共青团员，群众）
  
  社员加入社团时间：intotime
  
  社员是否删除：isamdelete
  
  社团ID：clubid
  
  角色ID：roleid
  
  ​	3.2.3	社团表（sys_club）
  
  社团编号：id（主键）（自动增长）
  
  社团名称：clubname
  
  社团描述：clubdesc
  
  社团创始人：clubfounder
  
  社团创建时间：clubtime
  
  社团照片：clubphoto
  
  社团积分：integral（积分按照一定规则前端显示星级）
  
  ​	3.2.4	角色表（sys_role）
  
  角色编号：id（主键）（自动增长）
  
  角色名称：rolename
  
  角色描述：roledesc
  
  ​	3.2.5	社团活动记录表（sys_option）
  
  活动编号：id（主键）（自动增长）
  
  活动名称：optionname
  
  活动描述：optiondesc
  
  活动目的：optionaim
  
  活动参与人数：optionnum
  
  活动成绩表（文件形式）：optionfile
  
  活动评分：optiongrade
  
  活动是否评分：isgrade
  
  举办活动社团ID：clubid
  
  举办活动角色ID：roleid
  
  ​	3.2.6	社员信息操作记录表（sys_operate）
  
  操作编号：id（主键）（自动增长）
  
  操作名称：operatename
  
  操作人员ID：userid
  
  操作人员名称：username
  
  操作人员角色ID：roleid
  
  操作人员角色名称：rolename
  
  操作人员社团ID：clubid
  
  操作人员社团名称：clubname
  
  操作时间：operatetime
  
  撤销删除操作：cloperate
  
  
  
  
  
  ​	