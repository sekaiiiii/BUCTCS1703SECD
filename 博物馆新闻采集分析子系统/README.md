# 博物馆新闻采集分析子系统

通过程序能从主要的新闻网站上爬取博物馆相关的新闻信息，进行加工处理。主要包括以下功能：

- 数据获取：爬取主要的新闻网站中的博物馆相关新闻（如百度新闻）。可以支持爬取指定时间范围内的新闻，如1年内的新闻，半年内的新闻等。
- 数据加工：对于爬取的信息进行过滤和加工，抽取需要的内容。例如，抽取新闻的发布时间、新闻的标题、新闻的内容、新闻涉及的博物馆等。
- 数据分析：对于加工好的新闻，分析是正面新闻还是负面信息。可采用已有的可直接调用的服务和代码实现。
- 数据定制服务：可以根据指定的某一家博物馆，获取该博物馆的指定时间的新闻，并进行加工分析，得到该博物馆指定时间内的主要新闻，正面新闻和负面新闻。



## 两个子项目

- MuseumNews为实现新闻简略信息爬取的项目
- demoSpider为实现新闻详细文本爬取并进行文本分析的爬虫项目

## 开发情况

- 具体开发情况详见周记
    - [博物馆新闻采集分析子系统开发周记](https://github.com/Ice-Jeffrey/BUCTCS1703SECD/blob/master/%E5%8D%9A%E7%89%A9%E9%A6%86%E6%96%B0%E9%97%BB%E9%87%87%E9%9B%86%E5%88%86%E6%9E%90%E5%AD%90%E7%B3%BB%E7%BB%9F/%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3/%E9%A1%B9%E7%9B%AE%E5%BC%80%E5%8F%91%E5%91%A8%E8%AE%B0.md)

## 最终交付项目

- 数据获取、数据加工、数据分析功能，合并到了一个Scrapy项目中，即museum_news_spider项目中
- api中封装了数据定制api/group2/get_new_info.js文件，使用node.js架构，通过后端接口向前端返回json格式的数据，以完成数据定制服务。


## 数据库表的具体形式

  新闻表-new

  | 序号 | 字段    | 类型         | 默认值 | 是否NULL | PK   | FK   | 描述     |
  | ---- | ------- | ------------ | ------ | -------- | ---- | ---- | -------- |
  | 1    | id      | int(11)      |        |          | 是   | 是   | 新闻id   |
  | 2    | title   | varchar(255) |        |          | 否   | 否   | 新闻标题 |
  | 3    | author  | varchar(255) |        |          | 否   | 否   | 新闻作者 |
  | 4    | time    | datetime     |        |          | 否   | 否   | 新闻时间 |
  | 5    | content | longtext     |        |          | 否   | 否   | 新闻摘要 |
  | 6    | url     | longtext     |        |          | 否   | 否   | 新闻url  |
  | 7    | tag     | int(11)      | 1      |          | 否   | 否   | 新闻正负 |


  新闻与博物馆关系表-museum_has_new

  | 序号 | 字段      | 类型    | 默认值 | 是否NULL | PK   | FK   | 描述     |
  | ---- | --------- | ------- | ------ | -------- | ---- | ---- | -------- |
  | 1    | new_id    | int(11) |        | 否       | 是   | 是   | 新闻id   |
  | 2    | museum_id | int(11) |        | 否       | 是   | 是   | 博物馆id |


## 使用说明

#### 5.1.1 newspider.py爬虫

- 该爬虫用来从网页爬取新闻

- 本地运行：
  - `scrapy crawl newspider` :默认输入爬取的是 **博物馆** 新闻 在所有时间范围内
  - `scrapy crawl newspider -a startTime=2020-05-17 -a endTime=2020-05-18`：爬取的是 **博物馆** 在指定时间内的新闻
  - `scrapy crawl newspider -a museum=故宫博物馆`：爬取的是 **故宫博物馆** 在所有时间范围的新闻
  - `scrapy crawl newspider -a museum=故宫博物馆 -a startTime=2020-05-17 -a endTime=2020-05-18`: 爬取的是 **故宫博物馆** 在指定时间范围内的新闻

- 服务器运行
  - `curl http://192.144.239.176:6800/schedule.json -d project=museum_news_spider -d spider=newspider` :默认输入爬取的是 **博物馆** 新闻 在所有时间范围内
  - `curl http://192.144.239.176:6800/schedule.json -d project=museum_news_spider -d spider=newspider -d startTime=2020-05-17 -d endTime=2020-05-18`：爬取的是 **博物馆** 在指定时间内的新闻
  - `curl http://192.144.239.176:6800/schedule.json -d project=museum_news_spider -d spider=newspider -d museum=故宫博物馆`：爬取的是 **故宫博物馆** 在所有时间范围的新闻
  - `curl http://192.144.239.176:6800/schedule.json -d project=museum_news_spider -d spider=newspider -d museum=故宫博物馆 -d startTime=2020-05-17 -d endTime=2020-05-18`: 爬取的是 **故宫博物馆** 在指定时间范围内的新闻

> 运行时参数为 `museum  startTime  endTime`不要写错，博物馆名称不用加引号，时间格式为：`%Y-%m-%d`

> startTime和endTime可以指定其中一个，也可以全部指定

#### 5.1.2 NewsTest.py爬虫

- 该爬虫用来提取新闻原文，从中筛选出与该新闻有关的博物馆，同时进行新闻文本分析

- 本地运行：
  - `scrapy crawl NewsTest`: 从数据库中取出新闻原文，并进行文本分析
  - `scrapy crawl -o output.json`: 从数据库中取出新闻原文进行文本分析，将结果输出到output.json文件中

- 服务器运行
  - `curl http://192.144.239.176:6800/schedule.json -d project=museum_news_spider -d spider=NewsTest`: 从数据库中取出新闻原文，并进行文本分析
  

### 5.1.3 服务器爬虫项目操作（仅供开发人员参考）

  - `curl http://192.144.239.176:6800/listjobs.json?project=museum_news_spider`: 获取服务器上**museum_news_spider**项目中正在运行的爬虫信息
  - `curl http://192.144.239.176:6800/cancel.json -d project=museum_news_spider -d job=8c9f5d769b8711eaab2a5254000fd2ba`: 停止服务器中**museum_news_spider**项目中id为**8c9f5d769b8711eaab2a5254000fd2ba**的爬虫
  - `curl http://192.144.239.176:6800/listspiders.json?project=museum_news_spider`: 获取服务器上**museum_news_spider**中的所有爬虫项目名称
  - `curl http://192.144.239.176:6800/delete.json -d project=MuseumNews`: 删除服务器上名字为**museum_news_spider**中的爬虫项目

### 5.2 接口调用

- 浏览器输入或postman请求：`http://192.144.239.176:8080/api/group2/get_new_info?id=1&start_date=2000-10-01&end_date=2020-05-21&tag=2&page=1&ppn=1000`
    - id代表数据库中博物馆的id
    - start_date与end_date为日期
    - tag为新闻的正负，0代表负面，1代表中性，2代表正面
    - ppn代表分页请求时每页的新闻条数，默认为10
    - page代表分页式显示信息的页数，默认为1
    - 所有参数均为可选参数