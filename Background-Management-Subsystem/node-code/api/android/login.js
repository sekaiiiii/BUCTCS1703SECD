/*
* author:谢奇
* create_day:2020-04-20
* modified_day:2020-04-25
* function:路由配置
*/
"use strict"

const express = require("express");
const pool = require("../../tool/pool.js");
const verify_login = require('../../middleware/verify_login.js')
const verify_no_login = require('../../middleware/verify_no_login.js');
const return_obj = require('../../tool/return_obj.js');
const crypto = require("crypto");

const router = express.Router();

//使用登录状态验证中间件
router.post("/", verify_no_login);

//进行参数检查
router.post("/", function (req, res, next) {
    if ("name" in req.body && "password" in req.body) {
        var name_reg = new RegExp("^[\u4E00-\u9FA5A-Za-z0-9_]{2,18}$")
        var password_reg = new RegExp("^[a-zA-Z0-9]{6,18}$")
        if (name_reg.test(req.body.name) && password_reg.test(req.body.password)) {
            next();
        } else {
            res.send(return_obj.fail("101", "输入参数有误"));
        }
    } else {
        res.send(return_obj.fail("100", "缺少必要的参数"))
    }
})

//查询数据库
router.post("/", function (req, res) {
    var sql = `
    select  user.id,
            user.name,
            user.password,
            user.no_comment,
            user.no_upload_explain,
            user.mail_address,
            role.admin_permission,
            role.root_permission
    from user,role
    where user.role_id = role.id
    and role.name = 'user'
    and user.name = ?
    `
    pool.query(sql, [req.body.name], function (err, user_list, fileds) {
        if (err) {
            console.error(err);
            res.send(return_obj.fail("200", "调用数据库接口错误"));
            return;
        }
        //检查用户存在性
        if (user_list.length == 0) {
            res.send(return_obj.fail("104", "用户不存在"));
            return;
        }
        //检查密码正确性
        var password_md5 = crypto.createHash("md5").update(req.body.password).digest("hex");
        if (password_md5 == user_list[0].password) {
            //设置session
            req.session.is_login = true;
            req.session.uid = user_list[0].id;
            req.session.name = user_list[0].name;
            req.session.no_comment = user_list[0].no_comment;
            req.session.no_upload_explain = user_list[0].no_upload_explain;
            req.session.mail_address = user_list[0].mail_address;
            req.session.root_permission = user_list[0].root_permission;
            req.session.admin_permission = user_list[0].admin_permission;

            res.send(return_obj.success({
                "msg": "登录成功"
            }));
        } else {
            res.send(return_obj.fail("105", "用户账号密码不匹配"))
        }
    })
})

module.exports = router;