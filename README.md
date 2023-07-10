# spring-boot-starter-at

### 更新记录

##### 1.0.0

- 文件上传下载功能

​       自动注册上传下载组件接口，上传文件到服务所在服务器，并提供访访问路径。

​       application.properties 提供配置如下：

​       sfile.uploadPath=/root/data    //文件上传路径

​       sfile.visitPath=/sfile      //url访问路径

```java
    @PostMapping("/at/file/upload")
    public String upload(@RequestParam MultipartFile uploadFile) {
      //注册的上传接口，返回可直接访问的地址，注：有shiro等组件需要放开path权限
    }  
```

##### 1.0.1

-  http请求日志

  打印请求入参，包含header、body、result，result可以运行时配置

  curl  ip:port/at/global/result/open?open=true   //开启result打印


##### 1.0.2

- curl日志功能

  接口问题排查时，可能经常需要前端发一个请求的curl，方便后端重现问题。该功能可以自行获取curl日志。

  服务端地址，经过nginx时可能会改写或者前端直接传非最终ip地址，所以支持直接配置

  at.trace.server-host: 127.0.0.1

  ```shell
  curl -X GET -H "content-length:148" -H "host:localhost:8039" -H "content-type:application/json" -H "connection:keep-alive" -H "accept-encoding:gzip, deflate, br" -H "user-agent:apifox/1.0.0 (https://www.apifox.cn)" -H "accept:*/*" -H "Content-Type:application/json" "http://127.0.0.1:8039/index"
  ```

##### 1.0.3

- 异常处理

  请求异常，统一封装


##### 1.0.4.1

- bug修复
- swagger自动配置
- AtAdvisor支持自定义扩展
  通过AtHandlerInterceptor，自定义advisor前后操作

#### 引入依赖
```xml
<dependency>
    <groupId>site.zfei</groupId>
    <artifactId>spring-boot-starter-at</artifactId>
    <version>1.0.4.1</version>
</dependency>
```






