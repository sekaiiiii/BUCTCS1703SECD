/*
* author:谢奇
* create_day:2020-04-20
* modified_day:2020-04-20
* function:提供封装过后的数据库调用接口
*/
"use strict"

const mysql = require("mysql");
const mysql_conf = require("../config/mysql_conf.js");

let pool = mysql.createPool(mysql_conf);

module.exports = pool;

