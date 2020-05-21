/*
 * author:谢奇
 * create_day:2020-04-20
 * modified_day:2020-05-05
 * function:配置服务器监听端口，全局中间件。
 */
"use strict"

//引入相关包
const express = require("express");
const path = require("path");
const session = require("express-session");
//引入配置文件
const app_conf = require("./config/app_conf.js");
const session_conf = require("./config/session_conf.js");
const body_parser = require('body-parser');
//创建app实例
const app = express();

//设置app监听端口
app.listen(app_conf.port, function () {
    console.log("服务器已经在本地" + app_conf.port + "端口打开");
})

//为app挂载session中间件
app.use(session(session_conf));

//app加载静态资源
app.use(express.static(path.join(__dirname, "dist")));
app.use(express.static(path.join(__dirname, "uploads")));

//app挂载bodyparser中间件
app.use(body_parser.urlencoded());
app.use(body_parser.json());

// app.use("*", function (req, res, next) {
//     console.log(req.baseUrl);
//     console.log("query----------");
//     console.log(req.query);
//     console.log("body-----------");
//     console.log(req.body);
//     next();
// });

//为app挂载路由
app.use("/", require("./router.js"));