# -*- coding: utf-8 -*-
import scrapy
from MuseumNews.items import MuseumnewsItem
from scrapy import Spider, Request
import re
import datetime
import time
from _datetime import timedelta

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
            url = "".join(href).replace("\n", "").replace(" ", "")
            
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
                s_time = author_time[1]
                if s_time:
                    time = self.parse_time(s_time)
                else:
                    time = s_time

            description = "1"
            tag = 1
            item = MuseumnewsItem()
            item['title'] = title
            item['author'] = author
            item['time'] = time
            item['description'] = description
            item['content'] = content
            item['url'] = url
            item['tag'] = tag
            yield item

        print('page = {}'.format(self.page))
        if self.page < 5:
            self.page += 1
            new_url = URL.format(page=self.page * 10)
            print(new_url)
            yield Request(new_url, callback=self.parse, dont_filter=True)

    def parse_time(self, s_time):
        result_time = ''
        # 1、2017-06-15
        if re.findall(r'\d{1,4}-\d{1,2}-\d{1,2}', s_time):
            result_time = time.strftime("%Y-%m-%d %H:%M:%S", time.strptime(s_time, "%Y-%m-%d"))
        # 6天前
        elif u'天前' in s_time:
            days = re.findall(u'(\d+)天前', s_time)[0]
            result_time = (datetime.datetime.now() - timedelta(days=int(days))).strftime("%Y-%m-%d %H:%M:%S")

        # 昨天 18:03
        elif u'昨天' in s_time:
            last_time = re.findall(r'.*?(\d{1,2}:\d{1,2})', s_time)[0]
            days_ago = datetime.datetime.now() - timedelta(days=int(1))
            y_m_d = str(days_ago.year) + '-' + str(days_ago.month) + '-' + str(days_ago.day)
            _time = y_m_d + ' ' + last_time
            result_time = time.strftime("%Y-%m-%d %H:%M:%S", time.strptime(_time, "%Y-%m-%d %H:%M"))

        # 28分钟前
        elif u'分钟前' in s_time:
            minutes = re.findall(u'(\d+)分钟', s_time)[0]
            minutes_ago = (datetime.datetime.now() - timedelta(minutes=int(minutes))).strftime("%Y-%m-%d %H:%M:%S")
            result_time = minutes_ago

        # 06-29
        elif re.findall(r'\d{1,2}-\d{1,2}', s_time) and len(s_time) <= 5:
            now_year = str(datetime.datetime.now().year)
            _time = now_year + '-' + s_time
            result_time = time.strftime("%Y-%m-%d %H:%M:%S", time.strptime(_time, "%Y-%m-%d"))

        # 1小时前
        elif u'小时前' in s_time:
            hours = re.findall(u'(\d+)小时前', s_time)[0]
            hours_ago = (datetime.datetime.now() - timedelta(hours=int(hours))).strftime("%Y-%m-%d %H:%M:%S")
            result_time = hours_ago

        return result_time
