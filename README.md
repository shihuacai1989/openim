## 开源分布式即时通讯服务，与openim-Android客户端配合使用，完整的最低运行要求：
* openim-chatserver服务：可运行多个实例，负责与终端设备（手机）建立长连接，收发消息
* openim-esb服务：总线服务，终端设备从esb服务中获取chatserver服务的连接地址，esb提供了chatserver内外网服务的地址
* zookeeper服务：chatserver在启动时，自动向zookeeper注册chatserver相关的信息，目前暂时只包含chatserver的内外网连接地址，esb服务监听zookeeper中节点的变化，实时更新chatserver服务信息
* openim-manager:后台管理服务，负责及时消息的持久化、分发、用户管理等
* 消息队列服务：目前暂用rabbitmq，后期可能会替换为hornetQ或其他的分布式消息队列系统，消息队列服务作为openim-manager与openim-chatserver之间信息交换的桥梁
* mongodb服务：负责消息的持久话，用户数据的存储
* 缓存服务：暂未实现，目前openim-manager部分功能采用了内存缓存，以后会完善缓存系统，加入memcached或者redis等
* openim-fileserver：暂未实现，负责多媒体消息的存储和发布，主要作为图片等多媒体消息的后台服务，发送图片等多媒体消息时，先上传到该平台，返回终端图片的唯一标识，再已不同消息发送出去

## 登录
* {"type":1,"loginId":"user1"}
* {"type":1,"loginId":"user2"}
* {"type":1,"loginId":"user3"}

## user1发送消息
{"type":2,"to":"user2","msg":"user1Touser2Msg"}

## mongo express
* npm config set registry http://registry.npmjs.org/ (--gwf影响https连接，npm默认为https://registry.npmjs.org/--)
* npm install -g mongo-express
* cd C:\Users\shihuacai\AppData\Roaming\npm\node_modules\mongo-express
* 复制或者把config.default.js改名为 config.js的新文件.并填写MongoDB的连接等你想更改的信息。
* node app
* http://localhost:8081/ 登录：admin/pass
