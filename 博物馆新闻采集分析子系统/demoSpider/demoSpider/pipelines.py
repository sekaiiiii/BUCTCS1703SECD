# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html

import jieba
import pandas as pd
from scrapy.exceptions import DropItem

class DemospiderPipeline(object):
    # 从文件中读入并定义语气词
    def __init__(self):
        super().__init__()
        stopwords_csv = pd.read_csv("./jieba/stopwords.txt" 
                      ,index_col=False
                      ,quoting=3
                      ,sep="\t"
                      ,names=['stopword']
                      ,encoding='utf-8') # quoting=3 全不引用  

        self.stopwords = []
        for i in range(0, stopwords_csv.shape[0]):
            self.stopwords.append(stopwords_csv.iloc[i, 0])

    # 数据分析与清理
    def process_item(self, item, spider):
        # 从爬取的item中去掉空字符串
        if item['content']:
            # 将文本的句子进行分词
            word_list = jieba.lcut(item['content'])   

            # 分词后去掉语气词、空格和数字
            l = []
            for word in word_list:
                if not word.isdigit() and not word.isspace() and word not in self.stopwords :
                    l.append(word)
            
            item['content'] = l
            return item
        else:
            raise DropItem("Missing content in %s" % item)
