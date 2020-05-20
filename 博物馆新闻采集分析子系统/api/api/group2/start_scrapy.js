/*
 * author:谢奇
 * create_day:2020-05-07
 * modified_day:2020-05-07
 * function:已登录用户发评论
 */
'use strict'
const express = require('express');
const pool = require('../../tool/pool.js'); // mysql请求的库
const path = require("path");
const { exec } = require('child_process');


const router = express.Router();

//验证登录
//router.post("/", verify_login);

//参数检查
router.get("/", function (req, res, next) {
    next();
})

//运行helloworld脚本
//处理业务代码
router.get("/", function (req, res, next) {
    exec(`python ${path.join(__dirname, "..", "..", "pyscript", "helloworld.py")}`, (err, stdout, stderr) => {
        if (err) {
            return res.send(err);
        }
        return res.send({
            stdout: stdout,
            stderr: stderr
        });

    })
});

//错误处理
router.use("/", function (err, req, res, next) {

})

module.exports = router;
