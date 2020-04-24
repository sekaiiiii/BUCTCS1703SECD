/*
* author:谢奇
* create_day:2020-04-20
* modified_day:2020-04-20
* function:路由配置
*/
"use strict"

const express = require("express");
const pool = require("../../tool/pool.js");


const router = express.Router();

router.all("/",function(req,res){
    pool.query("select * from role",function(err,data,fileds){
        if(err){
            console.error(err);
            return;
        }
        console.log(data);
    })
})

module.exports = router;