# -*- coding: utf-8 -*-
import scrapy
from demoSpider.items import DemospiderItem
from demoSpider import settings
import pymysql

class NewstestSpider(scrapy.Spider):
    name = 'NewsTest'
    allowed_domains = ['baijiahao.baidu.com']

    # 连接到远程数据库
    mydatabase = pymysql.connect(
        host=settings.MYSQL_HOST,
        port=3306,
        db=settings.MYSQL_DBNAME,
        user=settings.MYSQL_USER,
        passwd=settings.MYSQL_PASSWD,
        charset='utf-8',
        use_unicode=True
    )

    # 从服务器远程数据库中获取新闻url
    mycursor = mydatabase.cursor()
    mycursor.execute("SELECT id, url FROM new")
    myresult = mycursor.fetchall()     # fetchall() 获取所有记录
    
    # 获取start_urls列表
    start_urls = []
    for id, url in myresult:
        start_urls.append(url)
    
    id = 1

    def parse(self, response):
        # 从html中提取出所有的正文内容
        content = response.xpath('/html/body/div[@id = "detail-page"]/div[@id="content-container"]', encoding='UTF-8')
        content = content.xpath('//span[@class="bjh-p"]/text()', encoding="UTF-8")
        
        # 将正文的所有句子连接成字符串
        content_string = ""
        for item in content.extract():
            if item != "":
                content_string += item

        # 将获取到的字符串存入item中
        item = DemospiderItem()
        item['id'] = self.id
        item['content'] = content_string
        
        self.id += 1
        yield item