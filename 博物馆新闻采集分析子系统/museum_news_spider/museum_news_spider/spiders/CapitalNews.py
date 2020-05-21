import scrapy
from museum_news_spider.items import MuseumNewsSpiderItem
from scrapy import Spider, Request

URL = "http://www.capitalmuseum.org.cn/zjsb/sbkx_{page}.htm"
prefixURL = "http://www.capitalmuseum.org.cn/zjsb/"


class CapitalNews(scrapy.Spider):
    name = "CapitalNews"
    allowed_domains = ['www.capitalmuseum.org.cn']
    page = 2
    start_urls = [URL.format(page=page)]

    def parse(self, response):
        news_body = response.xpath("//td[@height='450']")[0]
        news_list = news_body.xpath(".//table[@width='85%']")
        for news in news_list:
            info = news.xpath(".//text()")
            if len(info) == 0:
                continue
            title = info[1].extract()
            time = info[0].extract().replace("\xa0", "")
            content = title
            href = prefixURL + news.xpath(".//@href")[0].extract()
            author = "首都博物馆"
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
        if self.page < 71:
            self.page += 1
            new_url = URL.format(page=self.page)
            print(new_url)
            yield Request(new_url, callback=self.parse, dont_filter=True)
