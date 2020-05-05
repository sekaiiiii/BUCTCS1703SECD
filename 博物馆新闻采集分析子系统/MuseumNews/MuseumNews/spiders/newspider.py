# -*- coding: utf-8 -*-
import scrapy
from MuseumNews.items import MuseumnewsItem
from scrapy import Spider, Request

URL = 'https://www.baidu.com/s?tn=news&rtt=4&bsst=1&cl=2&wd=%E5%8D%9A%E7%89%A9%E9%A6%86&medium=0&x_bfe_rqs=03E80&tngroupname=organic_news&newVideo=12&rsv_dl=news_b_pn&pn={page}'

class NewspiderSpider(scrapy.Spider):
    name = 'newspider'
    allowed_domains = ['baidu.com']
    page = 0
    start_urls = [URL.format(page = page * 10)]

    def parse(self, response):
        news_list = response.xpath('//div[@class="result"]')
        # print(news_list)
        if not news_list:
            return
        for news in news_list:
            href = news.xpath('./h3[@class="c-title"]/a/@href').extract()
            href = "".join(href).replace("\n", "").replace(" ", "")
            
            title = news.xpath('./h3[@class="c-title"]/a/text()').extract()
            title = "".join(title).replace("\n", "").replace(" ", "")

            content = news.xpath('./div/div[2]/text()').extract()
            content = "".join(content).replace("\n", "").replace(" ", "")

            author_time = news.xpath('./div/div[2]/p[@class="c-author"]/text()').extract()
            author_time = "".join(author_time).replace("\n", "").replace(" ", "").split()
            author = ""
            time = ""
            if author_time:     # 有些新闻没有作者和时间
                author = author_time[0]
                time = author_time[1]

            item = MuseumnewsItem()
            item['title'] = title
            item['author'] = author
            item['time'] = time
            item['content'] = content
            item['href'] = href
            yield item

        print('page = {}'.format(self.page))
        if self.page < 5:
            self.page += 1
            new_url = URL.format(page=self.page * 10)
            print(new_url)
            yield Request(new_url, callback=self.parse, dont_filter=True)
