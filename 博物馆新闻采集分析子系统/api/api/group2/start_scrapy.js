/*
 * author:Jeffrey
 * create_day:2020-05-20
 * modified_day:2020-05-20
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
    exec(`cd ~/Project/SE-group2/Museum_News_Collection/museum_news_spider`)
    exec(`/home/ubuntu/.local/bin/scrapyd-deploy`)
    
    command = `curl http://localhost:6800/schedule.json -d project=museum_news_spider -d spider=${req.query.spider}`
    if(req.query.museum)
        command += `-d museum=${req.query.museum}`
    if(req.query.startTime)
        command += `-d startTime=${req.query.startTime}`
    if(req.query.endTime)
        command += `-d endTime=${req.query.endTime}`
    exec(command, (err, stdout, stderr) => {
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
