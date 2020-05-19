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
// url: /api/group2/
// url: /api/group2/start_scrapy
router.use("/start_scrapy", require("./start_scrapy.js"));


module.exports = router;