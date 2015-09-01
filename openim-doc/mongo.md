## mongo express
* npm config set registry http://registry.npmjs.org/ (--gfw影响https连接，npm默认为https://registry.npmjs.org/--)
* npm install -g mongo-express
* cd C:\Users\用户名\AppData\Roaming\npm\node_modules\mongo-express
* 复制或者把config.default.js改名为 config.js的新文件.并填写MongoDB的连接等你想更改的信息。
* node app
* http://localhost:8082/ 登录：admin/pass

## mongo服务
D:\mongodb\bin\mongod.exe --logpath=D:\mongodb\log\log.txt --dbpath=D:\mongodb\data\db