import scrapy
from museum_news_spider.items import MuseumNewsSpiderItem
from scrapy import Spider, Request

URL = "http://www.jb.mil.cn/zxdt/index_{page}.html"
prefixURL = "http://www.jb.mil.cn/zxdt"


class MilitaryMuseumSpyder(scrapy.Spider):
    name = "MilitaryNews"
    allowed_domains = ['jb.mil.cn']
    page = 1
    start_urls = [URL.format(page=page)]

    def parse(self, response):
        news_body = response.xpath("//div[@class='infoDynamicList']")[0]
        news_list = news_body.xpath("./ul//li")
        for news in news_list:
            title = news.xpath("./a/h3/text()")
            time = news.xpath("./a/span/text()")
            content = news.xpath("./a/p/text()")
            href = news.xpath("./a/@href")
            if len(title) == 0 or len(time) == 0 or len(content) == 0 or len(href) == 0:
                continue
            title = title[0].extract()
            time = time[0].extract()
            content = content[0].extract()
            href = prefixURL + href[0].extract()[1:]
            author = "中国人民革命军事博物馆"
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
        if self.page < 30:
            self.page += 1
            new_url = URL.format(page=self.page)
            print(new_url)
            yield Request(new_url, callback=self.parse, dont_filter=True)
