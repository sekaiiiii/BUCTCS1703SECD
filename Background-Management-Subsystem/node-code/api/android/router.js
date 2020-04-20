/*
* author:谢奇
* create_day:2020-04-20
* modified_day:2020-04-20
* function:路由配置
*/
"use strict"

const express = require("express");

const router = express.Router();


//路由列表
router.use("/login",require("./login.js"));

module.exports = router;