const express = require("express");
const path = require("path");
const session = require("express-session");
const body_parser = require('body-parser');

//创建app实例
const app = express();



//设置app监听端口
app.listen(8080, function () {
    console.log("服务器已经在本地" + 8080 + "端口打开");
})

//app加载静态资源
app.use(express.static(path.join(__dirname, "dist")));
app.use(express.static(path.join(__dirname, "uploads")));

//app挂载bodyparser中间件
app.use(body_parser.urlencoded());
app.use(body_parser.json());

//为app挂载路由
app.use("/", require("./api/router.js"));