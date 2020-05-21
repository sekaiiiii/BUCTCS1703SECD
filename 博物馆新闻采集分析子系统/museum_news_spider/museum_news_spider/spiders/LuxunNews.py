import scrapy
from museum_news_spider.items import MuseumNewsSpiderItem
from scrapy import Spider, Request

URL = "http://www.luxunmuseum.com.cn/guanxun/list_30_{page}.htm"
prefixURL = "http://www.luxunmuseum.com.cn/"


class LuXunMuseum(scrapy.Spider):
    name = "LuxunNews"
    allowed_domains = ['luxunmuseum.com.cn']
    page = 1
    start_urls = [URL.format(page=page)]

    def parse(self, response):
        news_body = response.xpath("//div[@class='content_czyd']")[0]
        news_list = news_body.xpath(".//div[@class='list_list']")
        for news in news_list:
            title = news.xpath("./div/dt/a/text()")
            time = news.xpath("./div/dt/span/text()")
            content = news.xpath("./div/dd/a/text()")
            href = news.xpath("./div/dt/a/@href")
            if len(title) == 0 or len(time) == 0 or len(content) == 0 or len(href) == 0:
                continue
            title = title[0].extract()
            time = time[0].extract()
            content = content[0].extract()
            href = prefixURL + href[0].extract()
            author = "北京鲁迅博物馆"
            description = "1"
            tag = 1
            item = MuseumNewsSpiderItem()
            item['title'] = title
            item['author'] = author
            item['time'] = time
            item['description'] = description
            item['content'] = content
            item['url'] = href
            item['tag'] = tag
            yield item

        print('page = {}'.format(self.page))
        if self.page <= 6:
            self.page += 1
            new_url = URL.format(page=self.page)
            print(new_url)
            yield Request(new_url, callback=self.parse, dont_filter=True)
