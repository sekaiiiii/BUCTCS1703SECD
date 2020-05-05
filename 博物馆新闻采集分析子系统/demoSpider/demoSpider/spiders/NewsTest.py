# -*- coding: utf-8 -*-
import scrapy
from demoSpider.items import DemospiderItem
import mysql.connector

class NewstestSpider(scrapy.Spider):
    name = 'NewsTest'
    allowed_domains = ['baijiahao.baidu.com']

    # 连接到数据库
    mydb = mysql.connector.connect(
        host="192.144.239.176",       # 数据库主机地址
        user="root",    # 数据库用户名
        passwd="2F5gMs4jIabeFuOB",   # 数据库密码
        database="db",  # 使用的数据库
        
    )

    # 从服务器中获取新闻url
    mycursor = mydb.cursor()
    mycursor.execute("SELECT id, url FROM new")
    myresult = mycursor.fetchall()     # fetchall() 获取所有记录
    
    start_urls = []

    for id, url in myresult:
        start_urls.append(url)

    

    def parse(self, response):
        # 从html中提取出所有的正文内容
        content = response.xpath('/html/body/div[@id = "detail-page"]/div[@id="content-container"]', encoding='UTF-8')
        content = content.xpath('//span[@class="bjh-p"]/text()', encoding="UTF-8")
        
        # 将正文的所有句子连接成字符串
        content_string = ""
        for item in content.extract():
            content_string += item

        # 将获取到的字符串存入item中
        item = DemospiderItem()
        item['content'] = content_string
        yield item