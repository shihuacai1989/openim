开源im系统

# 登录
{"type":1,"loginId":"user1"}
{"type":1,"loginId":"user2"}
{"type":1,"loginId":"user3"}

# user1发送消息
{"type":2,"to":"user2","msg":"user1Touser2Msg"}

# mongo express

* npm config set registry http://registry.npmjs.org/ (--gwf影响https连接，npm默认为https://registry.npmjs.org/--)
* npm install -g mongo-express
* cd C:\Users\shihuacai\AppData\Roaming\npm\node_modules\mongo-express
* 复制或者把config.default.js改名为 config.js的新文件.并填写MongoDB的连接等你想更改的信息。
* node app
* http://localhost:8081/ 登录：admin/pass