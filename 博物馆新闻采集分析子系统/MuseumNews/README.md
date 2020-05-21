#### 文件目录：
```bash
│  data.txt         // 存储爬取的数据
│  list.txt         // 文件目录
│  README.md
│  scrapy.cfg
│  
└─MuseumNews
    │  items.py                 // 提取要爬取的字段
    │  middlewares.py           // 下载中间件和爬虫中间件，可以设置下载时所用的代理
    │  pipelines.py             // 管道文件，用于对数据进行处理或保存
    │  settings.py              // 设置文件，例如User-Agent，headers，下载等
    │  __init__.py              // python 文件包需要
    │  
    ├─spiders       // 该目录下存放爬虫文件
       │  newspider.py          // 百度新闻爬取
       |  CapitalNews.py        // 首都博物馆新闻爬取
       |  LuxunNews.py          // 鲁迅博物馆新闻爬取
       |  MilitaryNews.py       // 中国革命军事博物馆新闻爬取
       |  NatureNews.py         // 自然博物馆新闻爬取
       |  ScienceNews.py        // 中国科学技术博物馆新闻爬取
       |  ToBoNews.py           // 中国地质博物馆新闻爬取
       │  __init__.py
```

#### 项目概览：
根据博物馆关键词和时间范围，爬取百度新闻. 此项目是爬取博物馆新闻，爬取内容为：新闻标题、新闻作者、新闻时间、新闻内容简介、新闻链接.

解析页面用的是xpath. 爬取的内容分别存在 data.txt 文件 和 数据库中.

#### 重点是newspider.py爬虫
这个爬虫的几种运行方式说明：
- `scrapy crawl newspider` :默认输入爬取的是 **博物馆** 新闻 在所有时间范围内
- `scrapy crawl newspider -a startTime=2020-05-17 -a endTime=2020-05-18`：爬取的是 **博物馆** 在指定时间内的新闻
- `scrapy crawl newspider -a museum=故宫博物馆`：爬取的是 **故宫博物馆** 在所有时间范围的新闻
- `scrapy crawl newspider -a museum=故宫博物馆 -a startTime=2020-05-17 -a endTime=2020-05-18`: 爬取的是 **故宫博物馆** 在指定时间范围内的新闻

运行时参数为 `museum  startTime  endTime`不要写错，博物馆名称不用加引号，时间格式为：`%Y-%m-%d`

#### 注意问题：
- 解决获取百度新闻页面时重定向问题，会把网页重定向到 www.baidu.com 
- 设置多个User-Agent，然后随机选取进行伪装
- 查看 settings.py 文件里面的设置，很重要
- xpath 获得节点后要 加上 extract() 转为字符类型