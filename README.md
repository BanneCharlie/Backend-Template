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
- ENUM类  自定义错误码  -->  ResultCodeEnum   √
- 封装通用响应类  -->  Result   √
- Spring MVC   
  - 全局异常处理器 -->  GlobalExceptionHandler 自定义异常 BusinessException √
  - 全局跨域处理  -->   CorsConfig  √
  - 全局请求拦截器  -->  GlobalInterceptor   InterceptorConfig  √
- Swagger + Knife4j 接口文档  --> Knife4jConfig  √
  - @Api + @ApiOperation使用  访问localhost:9090/doc.html
- JWT令牌生成 登录身份验证   --> JWTUtil √
  - 身份验证成功后,将当前用户的Id存放到 ThreadLocal中方便当前整个线程的使用
  - 将生成的JWT令牌传递给前端后,需要前端为每个需要校验的请求添加JWT令牌在请求头中的Authorization中;
- Redis中Key - Value值的序列化和反序列化 --> RedisConfig √
- 记录日志 存储在当前文件夹下,规定日志的记录格式 -->  创建@Logging注解  LogAspect  √
  - 记录下@Logging注解标注的方法被哪些用户使用,给出Warn警告
- Easy Excel 表格处理 
- OSS 阿里云图片存储
- 自定义权限注解 + 全局校验 (暂定处理)

*业务功能 :*
- 用户的登录  注册   √
- 查询 √
- @Logging 更新 √  @Logging 删除 √  @Logging 添加 √  @Logging注解进行监视操作
