# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class DemospiderItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    news_id = scrapy.Field()
    content = scrapy.Field()
    content_list = scrapy.Field()
    museum_id = scrapy.Field()
    tag = scrapy.Field()