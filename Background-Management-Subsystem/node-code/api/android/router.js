/*
 * author:谢奇
 * create_day:2020-04-20
 * modified_day:2020-05-07
 * function:路由配置
 */
"use strict"

const express = require("express");

const router = express.Router();


//路由列表
router.use("/login", require("./login.js"));
router.use("/want_register", require("./want_register.js"));
router.use("/register", require("./register.js"));
router.use("/logout", require("./logout.js"));
router.use("/set_user_info", require("./set_user_info.js"));
router.use("/set_user_password", require("./set_user_password"));

router.use("/get_login_state", require("./get_login_state.js"));

router.use("/get_museum_info", require("./get_museum_info.js"));
router.use("/get_collection_info", require("./get_collection_info.js"));
router.use("/get_exhibition_info", require("./get_exhibition_info.js"));
router.use("/get_education_activity_info", require("./get_education_activity_info.js"));
router.use("/get_new_info", require("./get_new_info.js"));
router.use("/get_explain_info", require("./get_explain_info.js"));
router.use("/get_myself_explain", require("./get_myself_explain"));
router.use("/get_museum_comment", require("./get_museum_comment"));


router.use("/upload_explain", require("./upload_explain.js"));
router.use("/comment", require("./comment.js"));

router.use("/del_explain", require("./del_explain.js"));
router.use("/del_comment", require("./del_comment.js"));

router.use("/feedback", require("./feedback.js"));

//获取版本号
router.use("/get_version_num", require("./get_version_num.js"));

//测试接口
router.use("/get_position", require("./get_position.js"));

module.exports = router;