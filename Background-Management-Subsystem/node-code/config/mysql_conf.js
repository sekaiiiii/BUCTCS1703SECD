/*
* author:谢奇
* create_day:2020-04-20
* modified_day:2020-04-20
* function:nodejs mysql连接池配置
*/
"use strict"
module.exports = {
    host: "192.144.239.176",
    port: 3306,
    user: "root",
    password: "2F5gMs4jIabeFuOB",
    database: "db",
    connectionLimit: 100,
    queueLimit: 0,
    acquireTimeout:10000,
    waitForConnections:true
}