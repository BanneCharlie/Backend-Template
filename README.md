*主流框架 :*
- Spring Boot 2.7.X 
- Spring AOP 面向切面
- Spring 事务注解
- SpringMVC   处理请求
- Mybatis + PageHelper   Mybatis-Plus 数据访问
- XXL-Job  定时任务  todo

*数据存储 :*
- Mysql数据库
- Redis 缓存数据库
- ElasticSearch 搜索引擎
- 腾讯云 阿里云 Minio对象存储  todo

*工具类 :*
- Hutool 工具库
- Gson 解析库
- Apache Commons Lang3 工具类
- Lombok 注解 
- JWT 令牌  用于加密数据传输

*业务特性 :*
- Spring MVC   
  - 全局异常处理器 -->  GlobalExceptionHandler 自定义异常 BusinessException √
  - 全局跨域处理  -->   CorsConfig  √
  - 全局请求拦截器  -->  GlobalInterceptor   InterceptorConfig  √
- ENUM类  自定义错误码  -->  ResultCodeEnum   √
- 封装通用响应类  -->  Result   √
- Swagger + Knife4j 接口文档  --> Knife4jConfig  √
  - @Api + @ApiOperation使用  访问localhost:9090/doc.html  
- 自定义权限注解 + 全局校验 (暂定处理) 
- JWT令牌生成 登录身份验证   --> JWTUtils  
- Redis中Key - Value值的序列化和反序列化
- Easy Excel 表格处理 

*业务功能 :*
- 用户的登录  注册   更新   删除   添加  查询
