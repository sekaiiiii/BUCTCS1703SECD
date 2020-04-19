# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html

import pymysql
from logging import log

class MuseumnewsPipeline(object):
    def process_item(self, item, spider):
        return item

'''
存储数据到 txt 文件
'''
class TxtPipeline(object):
    def __init__(self):
        self.fp = None
    
    # 开始爬虫
    def open_spider(self, spider):
        self.fp = open('./data.txt', 'w', encoding='utf-8')

    # 专门处理item对象
    def process_item(self, item, spider):
        title = item['title']
        author = item['author']
        time = item['time']
        content = item['content']
        href = item['href']

        contents = '{}\n{}\n{}\n{}\n{}\n\n'.format(title, author, time, content, href)
        self.fp.write(contents)
        return item
    
    # 爬虫结束
    def close_spider(self, spider):
        self.fp.close()


'''
存储数据到数据库
'''
class MysqlPipeline(object):
    def __init__(self):
        self.connect = None
        self.cursor = None

    def open_spider(self, spider):
        self.connect = pymysql.Connect(
            host='localhost',
            port=3306,
            user='root',
            password='mysql',
            db='group2-zjf-news',
            charset='utf8mb4'
        )
    
    def process_item(self, item, spider):
        title = item['title']
        author = item['author']
        time = item['time']
        content = item['content']
        href = item['href']
        self.cursor = self.connect.cursor()

        try:
            self.cursor.execute(
                "select title from museumnews where title ='{}'".format(title)
            )
            repetiton = self.cursor.fetchone()
            if repetiton:
                pass
            else:
                self.cursor.execute(
                    "insert into museumnews(title, author, time, content, href) values ('{}','{}','{}','{}','{}')".format(
                        title, author, time, content, href
                    )
                )
                self.connect.commit()
        except Exception as error:
            log(error)      # 出现错误打印日志

        return item

    def close_spider(self, spider):
        self.cursor.close()
        self.connect.close()
