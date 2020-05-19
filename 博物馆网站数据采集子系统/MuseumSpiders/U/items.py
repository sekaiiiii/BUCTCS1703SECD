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
     open_time = scrapy.Field()
     introduction = scrapy.Field()
     visit_info = scrapy.Field()
     attention = scrapy.Field()
     #exhibition_score = scrapy.Field()

class CollectionItem(scrapy.Item):
     id = scrapy.Field()
     name = scrapy.Field()
     content = scrapy.Field()
     material = scrapy.Field()
     img_url = scrapy.Field()
     tag = scrapy.Field()
     museum_id = scrapy.Field()
     image_path = scrapy.Field()
     collection_id = scrapy.Field()
