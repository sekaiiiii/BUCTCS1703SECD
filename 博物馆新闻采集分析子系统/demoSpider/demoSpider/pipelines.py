# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html


class DemospiderPipeline(object):
    # 数据分析与清理
    def process_item(self, item, spider):
        # 从爬取的item中去掉空字符串
        if item['content']:
                return item
        else:
            raise DropItem("Missing content in %s" % item)
