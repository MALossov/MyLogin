<!--
 * @Description: SIMPLE LOGIN PAGE DESCRIPTION
 * @Author: MALossov
 * @Date: 2022-02-09 21:25:42
 * @LastEditTime: 2022-02-09 21:36:44
 * @LastEditors: MALossov
 * @Reference: 
-->
# MyLogin

## 简单介绍
- 包含：
  - 2个页面的简单**登录注册一体**的前端页面
  - 后端：1个Servlet类和一个JDBC类能够保证数据库的读取
  - 备注：数据库建立的脚本在Rescourse下。
- 缝了挺多东西的，不保证后续增删查改的稳定性
- 总之就是非常捡漏，但能有一些提示的小功能：
  - 用户名密码错误
  - 填了空值的判定
  - 邮箱格式正确与否的判定

## 运行方法
运行命令生成war：
> mvn clean package
然后*Mylogin.war*文件扔TomCat上就行，访问地址为:**/MyLogin**
## 运行的环境
- iDea写的
- JDK 17
- MySQL 8.0+
- Maven 3.8.4
- TomCat 8
  - 一定不要用10
  
## 贡献者
- **MALossov**：为了一个项目的入学考试缝合两天。
- **Racheal233**:没有您的督促鼓励和告知我不可能完成这个项目。（踩这个坑）