import scrapy
from museum_news_spider.items import MuseumNewsSpiderItem
from scrapy import Spider, Request

URL = "http://www.bmnh.org.cn/zhkx/gnyw/list_{page}.shtml"
prefixURL = "http://www.bmnh.org.cn"

class ScienceNews(scrapy.Spider):
    name = "NatureNews"
    allowed_domains = ['bmnh.org.cn']
    page = 2
    start_urls = [URL.format(page=page)]

    def parse(self, response):
        news_body = response.xpath("//div[@class='content_singler']")[0]
        news_list = news_body.xpath(".//p[@class='content_single_list']")
        for news in news_list:
            title = news.xpath("./a/span/text()")
            time = news.xpath("./span[@class='content_single_listDate']/text()")
            href = news.xpath(".//@href")
            if len(title) == 0 or len(time) == 0 or len(href) == 0:
                continue
            title = title[0].extract()
            time = time[0].extract()
            content = title
            href = prefixURL + href[0].extract()
            author = "北京自然博物馆"
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
        if self.page < 49:
            self.page += 1
            new_url = URL.format(page=self.page)
            print(new_url)
            yield Request(new_url, callback=self.parse, dont_filter=True)
