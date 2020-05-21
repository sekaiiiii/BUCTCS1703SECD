const express = require('express')
const router = express.Router()

router.use("/get_new_info", require("./android/get_new_info.js"));
router.use("/get_customized_news", require("./android/get_customized_news.js"))
router.use("/start_scrapy", require("./group2/start_scrapy.js"))

// router.get("/", function(err, req, res, next) {
//   if(err) {
//     console.error("Error!");
//     return next();
//   }
//   else { 
//     res.send("Hello, this is a test page.");
//     return next();
//   }
// })

module.exports = router