"use strict"

const mysql = require("mysql");
const mysql_conf = require("../config/mysql_conf.js");

let pool = mysql.createPool(mysql_conf);

module.exports = pool;