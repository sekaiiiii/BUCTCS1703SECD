/*
* author:谢奇
* create_day:2020-04-20
* modified_day:2020-04-20
* function:路由配置,将web端的请求和android端请求分别交由不同路由组件
*/
"use strict"

const express = require("express");

const router = express.Router();

//路由配置
router.use("/android",require("./android/router.js"));
router.use("/web",require("./web/router.js"));

module.exports = router;