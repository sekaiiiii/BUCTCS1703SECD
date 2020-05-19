# -*- coding: utf-8 -*-
import scrapy
from demoSpider.items import DemospiderItem
from demoSpider import settings
import pymysql

class NewstestSpider(scrapy.Spider):
    name = 'NewsTest'
    allowed_domains = [
        'baijiahao.baidu.com',
        'new.qq.com',
        'news.ifeng.com',
        'news.sina.com.cn',
        'thepaper.cn',
        'people.com.cn',
        '3g.163.com',
        'news.163.com', 
        'xinhuanet.com',
        'gmc.org.cn',
        'jb.mil.cn',
        'luxunmuseum.com.cn',
        'cstm.cdstm.cn',
        'www.capitalmuseum.org.cn',
        'www.bmnh.org.cn'
    ]
    
    # 连接到远程数据库
    mydatabase = pymysql.connect(
        host=settings.MYSQL_HOST,
        port=3306,
        db=settings.MYSQL_DBNAME,
        user=settings.MYSQL_USER,
        passwd=settings.MYSQL_PASSWD,
        charset='utf8',
        use_unicode=True
    )

    # 从服务器远程数据库中获取新闻url
    mycursor = mydatabase.cursor()
    mycursor.execute("SELECT id, url FROM new")
    myresult = mycursor.fetchall() 
    mycursor.close()    
    
    # 获取start_urls列表
    start_urls = []
    for id, url in myresult:
        start_urls.append(url)

    def getNewsID(self, url):
        mycursor = self.mydatabase.cursor()
        sql = "select id from new where url='{}'".format(url)
        mycursor.execute(sql)
        id = mycursor.fetchone()
        mycursor.close()
        return id[0]

    def parse(self, response):
        url = response.url
        if "baijiahao" in url: 
            yield scrapy.Request(url, callback=self.parse_baijiahao_contents)
        elif "new.qq.com" in url:
            yield scrapy.Request(url, callback=self.parse_tencent_contents)
        elif "news.ifeng.com" in url:
            yield scrapy.Request(url, callback=self.parse_ifeng_contents)
        elif "news.163.com" in url:
            yield scrapy.Request(url, callback=self.parse_163_news_contents)
        elif "3g.163.com" in url:
            yield scrapy.Request(url, callback=self.parse_3g_163_contents)
        elif "thepaper.cn" in url:
            yield scrapy.Request(url, callback=self.parse_pengpai_contents)
        elif "news.sina.com.cn" in url:
            yield scrapy.Request(url, callback=self.parse_sina_contents)
        elif "paper.people.com.cn" in url:
            yield scrapy.Request(url, callback=self.parse_paper_people_contents)
        elif "xinhuanet.com" in url:
            yield scrapy.Request(url, callback=self.parse_xinhuanet_contents)
        elif "bmnh.org.cn" in url:
            yield scrapy.Request(url, callback=self.parse_bmnh_contents)
        elif "capitalmuseum" in url:
            yield scrapy.Request(url, callback=self.parse_capital_museum_contents)
        elif "cstm.cdstm.cn" in url:
            yield scrapy.Request(url, callback=self.parse_cstm_contents)
        elif "luxunmuseum" in url:
            yield scrapy.Request(url, callback=self.parse_luxunmuseum_contents)
        elif "jb.mil.cn" in url:
            yield scrapy.Request(url, callback=self.parse_military_museum_contents)
        elif "gmc" in url:
            yield scrapy.Request(url, callback=self.parse_gmc_contents)
        else:
            item = DemospiderItem()
            cursor = self.mydatabase.cursor()
            sql = "select content from new where url='{}'".format(url)
            cursor.execute(sql)
            result = cursor.fetchone()
            cursor.close()

            id = self.getNewsID(url)
            if id != []:
                item['news_id'] = id
            else:
                item['news_id'] = -1
            item['content'] = result[0]
            yield item

    def parse_baijiahao_contents(self, response):
        # 从html中提取出所有的正文内容
        content = response.xpath('/html/body/div[@id = "detail-page"]/div[@id="content-container"]', encoding='UTF-8')
        content = content.xpath('//span[@class="bjh-p"]/text()', encoding="UTF-8").extract()
        
        # 将正文的所有句子连接成字符串
        content_string = ""
        for item in content:
            if item != "":
                content_string += item.replace('\xa0', '').replace('\xa9', '')

        # 去掉正文中的换行符和空格
        content_string = content_string.replace('\n', "")
        content_string = content_string.replace(" ", "")
        content_string = content_string.replace("\u3000", "")
        
        # 将获取到的字符串存入item中
        item = DemospiderItem()
        item['news_id'] = self.getNewsID(response.url)
        item['content'] = content_string
        
        self.id += 1
        yield item

    def parse_tencent_contents(self, response):
        content = response.xpath('/html/body', encoding="UTF-8")
        content = content.xpath('//p[@class="one-p"]/text()').extract()

        content_string = ""
        for item in content:
            if item != "":
                content_string += item.replace('\xa0', '').replace('\xa9', '')

        content_string = content_string.replace('\n', "")
        content_string = content_string.replace(" ", "")
        content_string =  content_string.replace("\u3000", "")

        item = DemospiderItem()
        item['news_id'] = self.getNewsID(response.url)
        item['content'] = content_string

        yield item

    def parse_ifeng_contents(self, response):
        content = response.xpath("//div[@class='text-3zQ3cZD4']", encoding="UTF-8")
        content = content.xpath("//p/text()").extract()

        content_string = ""
        for item in content:
            if item != "":
                content_string += item.replace('\xa0', '').replace('\xa9', '')

        content_string = content_string.replace('\n', "")
        content_string = content_string.replace(" ", "")
        content_string =  content_string.replace("\u3000", "")

        item = DemospiderItem()
        item['news_id'] = self.getNewsID(response.url)
        item['content'] = content_string

        yield item

    def parse_163_news_contents(self, response):
        content = response.xpath('//div[@class="post_body"]', encoding = "UTF-8")
        content = content.xpath('//p/text()').extract()

        content_string = ""
        for item in content:
            if item != "":
                content_string += item.replace('\xa0', '').replace('\xa9', '')

        content_string = content_string.replace('\n', "")
        content_string = content_string.replace(" ", "")
        content_string =  content_string.replace("\u3000", "")
        
        item = DemospiderItem()
        item['news_id'] = self.getNewsID(response.url)
        item['content'] = content_string

        yield item

    def parse_pengpai_contents(self, response):
        content = response.xpath('//*[@id="root"]/div/div[3]/div[1]/div[1]/div[3]/div/div[1]', encoding="UTF-8")
        content = content[0].xpath('//p/text()').extract()

        content_string = ""
        for item in content:
            if item != "":
                content_string += item.replace('\xa0', '').replace('\xa9', '')
        
        content_string = content_string.replace('\n', "")
        content_string = content_string.replace(" ", "")
        content_string =  content_string.replace("\u3000", "")

        item = DemospiderItem()
        item['news_id'] = self.getNewsID(response.url)
        item['content'] = content_string

        yield item
    
    def parse_3g_163_contents(self, response):
        content = response.xpath('//div[@class="article-content"]/div[@class="content"]', encoding="UTF-8")
        content = content.xpath('//p/text()').extract()

        content_string = ""
        for item in content:
            if item != "":
                content_string += item.replace('\xa0', '').replace('\xa9', '')

        content_string = content_string.replace('\n', "")
        content_string = content_string.replace(" ", "")
        content_string =  content_string.replace("\u3000", "")

        item = DemospiderItem()
        item['news_id'] = self.getNewsID(response.url)
        item['content'] = content_string

        yield item
    
    def parse_sina_contents(self, response):
        content = response.xpath('//div[@class="article-content-left"]/div[@class="article"]', encoding="UTF-8")
        content = content.xpath('//p/text()').extract()

        content_string = ""
        for item in content:
            if item != "":
                content_string += item.replace('\xa0', '').replace('\xa9', '')

        content_string = content_string.replace('\n', "")
        content_string = content_string.replace(" ", "")
        content_string = content_string.replace("\u3000", "")
        
        item = DemospiderItem()
        item['news_id'] = self.getNewsID(response.url)
        item['content'] = content_string

        yield item

    def parse_paper_people_contents(self, response):
        content = response.xpath('//div[@class="content"]/div[@class="right_c"]', encoding="UTF-8")
        content = content.xpath('//p/text()').extract()

        content_string = ""
        for item in content:
            if item != "":
                content_string += item.replace('\xa0', '').replace('\xa9', '')

        content_string = content_string.replace('\n', "")
        content_string = content_string.replace(" ", "")
        content_string = content_string.replace("\u3000", "")
        
        item = DemospiderItem()
        item['news_id'] = self.getNewsID(response.url)
        item['content'] = content_string

        yield item

    def parse_xinhuanet_contents(self, response):
        content = response.xpath('//div[@id="p-detail"]', encoding="UTF-8")
        content = content.xpath('//p/text()').extract()

        content_string = ""
        for item in content:
            if item != "":
                content_string += item.replace('\xa0', '').replace('\xa9', '')

        content_string = content_string.replace('\n', "")
        content_string = content_string.replace(" ", "")
        content_string =  content_string.replace("\u3000", "")
        
        item = DemospiderItem()
        item['news_id'] = self.getNewsID(response.url)
        item['content'] = content_string

        yield item

    def parse_bmnh_contents(self, response):
        content = response.xpath('//div[@class="content_singler"]/div[@class="single_block"]', encoding = "UTF-8")
        content = content.xpath('//p')
        content = content.xpath('//span/text()').extract()

        content_string = ""
        for item in content:
            if item != "":
                content_string += item.replace('\xa0', '').replace('\xa9', '')

        content_string = content_string.replace('\n', "")
        content_string = content_string.replace(" ", "")
        content_string = content_string.replace("\u3000", "")
        
        item = DemospiderItem()
        item['news_id'] = self.getNewsID(response.url)
        item['content'] = content_string

        yield item

    def parse_capital_museum_contents(self, response):
        content = response.xpath("//span[@class='wcontent']")
        content = content.xpath("//p/text()").extract()

        content_string = ""
        for item in content:
            if item != "":
                content_string += item.replace('\xa0', '').replace('\xa9', '')

        content_string = content_string.replace('\n', "")
        content_string = content_string.replace(" ", "")
        content_string = content_string.replace("\u3000", "")
        content_string = content_string.replace("\xa0", "").replace("\r", "")

        item = DemospiderItem()
        item['news_id'] = self.getNewsID(response.url)
        item['content'] = content_string

        yield item

    def parse_cstm_contents(self, response):
        content = response.xpath('//div[@class="fen-newright"]')
        content = content.xpath('//p/text()').extract()

        content_string = ""
        for item in content:
            if item != "":
                content_string += item.replace('\xa0', '').replace('\xa9', '')

        content_string = content_string.replace('\n', "")
        content_string = content_string.replace(" ", "")
        content_string = content_string.replace("\u3000", "")
        content_string = content_string.replace("\xa0", "").replace("\r", "")

        item = DemospiderItem()
        item['news_id'] = self.getNewsID(response.url)
        item['content'] = content_string

        yield item

    def parse_luxunmuseum_contents(self, response):
        content = response.xpath('//div[@class="content_nr"]')
        content = content.xpath('//span/text()').extract()

        content_string = ""
        for item in content:
            if item != "":
                content_string += item.replace('\xa0', '').replace('\xa9', '')

        content_string = content_string.replace('\n', "")
        content_string = content_string.replace(" ", "")
        content_string = content_string.replace("\u3000", "")
        content_string = content_string.replace("\xa0", "").replace("\r", "")

        item = DemospiderItem()
        item['news_id'] = self.getNewsID(response.url)
        item['content'] = content_string

        yield item

    def parse_military_museum_contents(self, response):
        content = response.xpath('//div[@class="TRS_Editor"]/div[@class="TRS_Editor"]')
        content = content.xpath('//p/text()').extract()

        content_string = ""
        for item in content:
            if item != "":
                content_string += item.replace('\xa0', '').replace('\xa9', '')

        content_string = content_string.replace('\n', "")
        content_string = content_string.replace(" ", "")
        content_string = content_string.replace("\u3000", "")
        content_string = content_string.replace("\xa0", "").replace("\r", "").replace("\t", "")

        item = DemospiderItem()
        item['news_id'] = self.getNewsID(response.url)
        item['content'] = content_string

        yield item

    def parse_gmc_contents(self, response):
        content = response.xpath('//div[@class="article-cont"]')
        content = content.xpath('//p/span/text()').extract()

        content_string = ""
        for item in content:
            if item != "":
                content_string += item.replace('\xa0', '').replace('\xa9', '')

        content_string = content_string.replace('\n', "")
        content_string = content_string.replace(" ", "")
        content_string = content_string.replace("\u3000", "")
        content_string = content_string.replace("\xa0", "").replace("\r", "")

        item = DemospiderItem()
        item['news_id'] = self.getNewsID(response.url)
        item['content'] = content_string

        yield item