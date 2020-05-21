# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy

class UItem(scrapy.Item):
    # define the fields for your item here like:
     id = scrapy.Field()
     name = scrapy.Field()
     establishment_time = scrapy.Field()
     time = scrapy.Field()
     introduction = scrapy.Field()
     visit_info = scrapy.Field()
     attention = scrapy.Field()
     #exhibition_score = scrapy.Field()
