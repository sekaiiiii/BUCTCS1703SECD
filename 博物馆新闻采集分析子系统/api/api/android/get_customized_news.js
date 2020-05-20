/*
* author: Jeffrey
* create_day: 2020-05-07
* modified_day:2020-05-07
* function:已登录用户通过该接口查看数据库中博物馆指定时间的新闻，进行数据定制服务
*/

'use strict'
const express = require('express');
const async = require('async');
const pool = require('../../tool/pool.js');
const return_obj = require('../../tool/return_obj.js');
const router = express.Router();

//参数检查
router.get("/", function (req, res, next) {
    let id_reg = new RegExp("^\\d+$");
    let page_reg = new RegExp("^\\d+$");
    let ppn_reg = new RegExp("^\\d+$");
    let tag_reg = new RegExp("^\\d+$");
    var start_date_reg = new RegExp("/^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/");
    var end_date_reg = new RegExp("/^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/");
    if (req.query.id != undefined) {
        if (!id_reg.test(req.query.id)) {
            return next(new Error("101"));
        }
    }
    if (req.query.page != undefined) {
        if (!page_reg.test(req.query.page)) {
            if (req.query.page < 1) {
                return next(new Error("101"));
            }
        }
    }
    if (req.query.ppn != undefined) {
        if (!ppn_reg.test(req.query.ppn)) {
            if (req.query.ppn < 0) {
                return next(new Error("101"));
            }
        }
    }
    if (req.query.tag != undefined) {
        if (!tag_reg.test(req.query.tag)) {
            if (req.query.ppn < 0) {
                return next(new Error("101"));
            }
        }
    }
    if (req.query.start_date != undefined) {
        if (!tag_reg.test(req.query.tag)) {
            // if (req.query.ppn < 0) {
            //     return next(new Error("101"));
            // }
        }
    }
    if (req.query.end_date != undefined) {
        if (!tag_reg.test(req.query.tag)) {
            // if (req.query.ppn < 0) {
            //     return next(new Error("101"));
            // }
        }
    }
    next();
})

//查询查询
router.get("/", function (req, res, next) {
    async.waterfall([
        function structureSQL(done) {
            let sql = `
            select 
                new.id as id,
                new.title as title,
                new.author as author,
                new.time as time,
                new.description as description,
                new.content as content,
                new.url as url,
                new.tag as tag,
                museum_has_new.museum_id
            from 
                new left join museum_has_new on museum_has_new.new_id = new.id 
            where 
                new.id >= 1
                ${req.query.id ? `and museum_has_new.museum_id = ? ` : ``}
                ${req.query.tag ? `and new.tag = ? ` : ``}
                ${req.query.start_date ? `and new.time > ?` : ``}
                ${req.query.end_date ? `and new.time < ?` : ``}
            order by
                new.time asc
            limit ?
            offset ?
            `;

            //参数构造
            let param_list = [];

            if (req.query.id) {
                param_list.push(req.query.id);
            }
            if (req.query.tag) {
                param_list.push(req.query.tag);
            }
            if (req.query.start_date) {
                param_list.push(req.query.start_date)
            }
            if (req.query.end_date) {
                param_list.push(req.query.end_date)
            }

            let offset = 0; //偏移
            let limit = 10; //每页显示多少

            if (req.query.ppn != undefined) {
                limit = req.query.ppn * 1;
            }
            if (req.query.page != undefined) {
                offset = (req.query.page - 1) * limit;
            }

            param_list.push(limit);
            param_list.push(offset);

            done(null, sql, param_list);

        },
        function getNewList(sql, param_list, done) {
            pool.query(sql, param_list, function (err, new_list, fileds) {
                if (err) {
                    console.error(err);
                    return done(new Error("200"));
                };
                done(null, new_list);
            });
        }
    ],
        function (err, new_list) {
            if (err) {
                return next(err);
            }
            res.send(return_obj.success({
                msg: "获取新闻信息成功",
                data: new_list
            }))
        }
    );//async.waterfall...
})


//错误处理
router.use("/", function (err, req, res, next) {
    console.error(err);
    switch (err.message) {
        case "100":
            res.send(return_obj.fail("100", "缺少必要的参数"));
            break;
        case "101":
            res.send(return_obj.fail("101", "传入参数格式有误"));
            break;
        case "200":
            res.send(return_obj.fail("200", "调用数据库接口出错"));
            break;
        case "400":
            res.send(return_obj.fail("400", "没有检索到藏品"));
            break;
        default:
            res.send(return_obj.fail("500", "出乎意料的错误"));
            break;
    }
})
module.exports = router;