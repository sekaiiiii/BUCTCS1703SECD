import scrapy
from MuseumNews.items import MuseumnewsItem
from scrapy import Spider, Request

URL = "http://www.gmc.org.cn/toboalerts/p/{page}.html"
prefixURL = "http://www.gmc.org.cn"


class ToBoNewsSpyder(scrapy.Spider):
    name = "ToBoNews"
    allowed_domains = ['gmc.org.cn']
    page = 1
    start_urls = [URL.format(page=page)]

    def parse(self, response):
        news_lists = response.xpath("//div[@class='con2']")[0]
        news_list = news_lists.xpath(".//div[@class='li']")
        for news in news_list:
            title = news.xpath("./a/div/div[@class='t18']/text()")
            time = news.xpath("./a/div/div[@class='time']/text()")
            content = news.xpath("./a/div/div[@class='p']/text()")
            href = news.xpath("./a/@href")
            if len(title) == 0 or len(time) == 0 or len(content) == 0 or len(href) == 0:
                continue
            title = title[0].extract()
            time = time[0].extract()
            content = content[0].extract()
            href = prefixURL + href[0].extract()
            author = "中国地质博物馆"
            description = "1"
            tag = 1
            item = MuseumnewsItem()
            item['title'] = title
            item['author'] = author
            item['time'] = time
            item['description'] = description
            item['content'] = content
            item['url'] = href
            item['tag'] = tag
            yield item

        print('page = {}'.format(self.page))
        if self.page < 20:
            self.page += 1
            new_url = URL.format(page=self.page)
            print(new_url)
            yield Request(new_url, callback=self.parse, dont_filter=True)
