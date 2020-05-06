# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html

import jieba
import pandas as pd
from scrapy.exceptions import DropItem
import jieba.analyse as analyse
import codecs
from decimal import Decimal

class DemospiderPipeline(object):
    # 从文件中读入并定义语气词
    def __init__(self):
        # 继承之前的构造函数
        super().__init__()

        # 从文件中读入停用词
        stopwords_csv = pd.read_csv("./jieba/stopwords.txt" 
                      ,index_col=False
                      ,quoting=3
                      ,sep="\t"
                      ,names=['stopword']
                      ,encoding='utf-8') # quoting=3 全不引用  

        # 构建停用词列表
        self.stopwords = []
        for i in range(0, stopwords_csv.shape[0]):
            self.stopwords.append(stopwords_csv.iloc[i, 0])
        
        # 构建情感词词典
        self.word_dict = {}

        # 从文件中读入情感词
        with open("./BosonNLP_sentiment_score/BosonNLP_sentiment_score.txt", mode='r', encoding='UTF-8') as f:
            lines = f.readlines()
            for line in lines: 
                index = line.find(" ")
                word = line[0:index]
                score = line[index+1:]
                if not score.isspace():
                    if score[0] == '-':
                        score = - float(score[1:])
                    else:
                        score = float(score)
                    self.word_dict[word] = score
       
    # 根据输入的单词得到情感词典中的分数
    def getScore(self, word):
        tf_idf = word[1]
        word = word[0]
        return tf_idf * self.word_dict.get(word, 0)       

    # 数据分析与清理
    def process_item(self, item, spider):
        # 从爬取的item中去掉空字符串
        if item['content']:
            # 将文本的句子进行分词
            word_list = jieba.lcut(item['content'])   

            # 分词后去掉语气词、空格和数字
            l1 = []
            for word in word_list:
                if not word.isdigit() and not word.isspace() and word not in self.stopwords :
                    l1.append(word)

            # 提取出TF-IDF的语气词频
            tf_idf_keyword_list = analyse.extract_tags(item['content'], topK=50, withWeight=True, allowPOS=())

            # 去掉空语气词并保留其语气词词频，并计算其情感得分
            l2 = []
            tf_idf_score = 0
            for word in tf_idf_keyword_list:
                if not word[0].isdigit() and not word[0].isspace() and word[0] not in self.stopwords :
                    l2.append(word)
                    tf_idf_score += self.getScore(word)

            # 根据TextRank()算法提取出关键词
            TextRank_keyword_list = analyse.textrank(item['content'], topK=50, withWeight=True)

            # 去掉空语气词并保留其语气词词频，并计算其情感得分
            l3 = []
            TextRank_score = 0
            for word in TextRank_keyword_list:
                if not word[0].isdigit() and not word[0].isspace() and word[0] not in self.stopwords :
                    l3.append(word)
                    TextRank_score += self.getScore(word)


            item['content'] = l1
            item['tf_idf_keywords'] = l2
            item['tf_idf_score'] = tf_idf_score
            item['TextRank_keywords'] = l3
            item['TextRank_score'] = TextRank_score
            return item
        else:
            raise DropItem("Missing content in %s" % item)