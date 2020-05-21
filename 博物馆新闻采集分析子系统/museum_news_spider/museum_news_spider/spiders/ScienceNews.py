import scrapy
from museum_news_spider.items import MuseumNewsSpiderItem
from scrapy import Spider, Request

URL = "http://cstm.cdstm.cn/e/action/ListInfo/index.php?page={page}&classid=90&totalnum=227"
prefixURL = "http://cstm.cdstm.cn"


class ScienceNews(scrapy.Spider):
    name = "ScienceNews"
    allowed_domains = ['cstm.cdstm.cn']
    page = 0
    start_urls = [URL.format(page=page)]

    def parse(self, response):
        news_body = response.xpath("//div[@class='fen-right float-l']")[0]
        news_list = news_body.xpath(".//ul[@class='fen-right-list']")
        for news in news_list:
            title = news.xpath("./li/span/a/text()")
            time = news.xpath("./li/span[@class='fen-right-time']/text()")
            href = news.xpath("./li/span/a/@href")
            if len(title) == 0 or len(time) == 0 or len(href) == 0:
                continue
            title = title[0].extract()
            time = time[0].extract()
            content = title
            href = prefixURL + href[0].extract()
            author = "中国科学技术馆"
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
        if self.page <= 45:
            self.page += 1
            new_url = URL.format(page=self.page)
            print(new_url)
            yield Request(new_url, callback=self.parse, dont_filter=True)
