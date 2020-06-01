# Eblog

## 系统架构

前端

​	FreeMarker

​	Layui

后端

​	Spring Boot

​	Redis

​	MyBatis Plus

​	P6spy

​	Tio + WebSocket

​	ModelMapper

​	RabbitMQ

​	Elasticsearch

​	Shiro

​	Kaptcha

​	Hutool

## 基本讲解

其中Hutool（快速进行时间加减）、P6spy（对账户密码进行加密）和ModelMapper（Bean和Bean之间的转换，主要通讯时使用）都是拿来做工具类的；

采用MP快速开发后台信息；

采用Shiro进行用户鉴权，区分超级管理员，主要为超级管理员拥有对数据初始化和全部文章的管理功能；

Tio + WebSocket提供聊天室功能，可以为用户提供一个便捷的聊天渠道；

Redis对热门文章和搜索热词管理；

RabbitMQ对消息推送进行保证，一但程序运行，自动传输未完成的文章上传；

Elasticsearch为博客网站提供检索功能，后续可以做判断，如果没有，跳出本站，去百度信息。

## 快速启动

其实没什么特别要求，如果需要检索功能，请安装Elasticsearch和RabbitMQ功能，如果没有这两个组件，可以注释掉，不然定时任务会起冲突。

当然，你也可以关闭定时任务，以达到启动目的。

以下为组件的默认配置：

Elasticsearch

```yaml
spring:
	data:
  	  elasticsearch:
     	 cluster-name: eblog #elasticsearch.yml 文件中的 cluster.name，不是节点名称
     	 cluster-nodes: localhost:9300
     	 repositories:
      	  enabled: true
```

RabbitMQ：

```yaml
spring:
	rabbitmq:
   	 username: admin
   	 password: admin
   	 host: localhost
   	 port: 5672
```

im：

这个需要与tio服务一致

```yaml
im:
  server:
    port: 9326
```

file：

定义默认头像上传路径i

```yaml
file:
  upload:
    dir: ${user.dir}/upload
```

## 使用

和大部分博客功能一样，游客可以浏览博文，评论留言则需要账户，另外只有管理员账户才可以操作初始化检索数据，然后就是管理员账户设为id为6的用户，后续你可以在判断处进行新增。