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
       │  newspider.py          // 爬虫文件，解析网页，提取字段等
       │  __init__.py
```

#### 项目概览：
根据关键词，爬取百度新闻. 此项目是爬取博物馆新闻，爬取内容为：新闻标题、新闻作者、新闻事件、新闻内容简介、新闻链接.

解析页面用的是xpath. 爬取的内容分别存在 data.txt 文件 和数据库中.

#### 注意问题：
- 解决获取百度新闻页面时重定向问题，会把网页重定向到 www.baidu.com 
- 设置多个User-Agent，然后随机选取进行伪装
- 查看 settings.py 文件里面的设置，很重要
- xpath 获得节点后要 加上 extract() 转为字符类型