/*
* author:谢奇
* create_day:2020-04-20
* modified_day:2020-04-20
* function:路由配置
*/
"use strict"

const express = require("express");

const router = express.Router();


//路由配置
router.use("/api",require("./api/router"));
router.use("/file",require("./file.js"));
module.exports = router;