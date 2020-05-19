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
import pymysql
from decimal import Decimal
from demoSpider import settings
from aip import AipNlp

""" 你的 APPID AK SK """
# 利用百度云提供的API接口实现情感分析
APP_ID = '19926423'
API_KEY = 'S2LsSCtA0WXMrRGg2BZG0cCy'
SECRET_KEY = 'lw1QuCYOO7IcvKtVIHopZlcFDVcsGE0x'

class DemospiderPipeline(object):
    # 从文件中读入并定义语气词
    def __init__(self):
        # 继承之前的构造函数
        super().__init__()

        # 连接到远程数据库
        self.database = pymysql.connect(
            host=settings.MYSQL_HOST,
            port=3306,
            db=settings.MYSQL_DBNAME,
            user=settings.MYSQL_USER,
            passwd=settings.MYSQL_PASSWD,
            charset='utf8',
            use_unicode=True
        )

        # 获取博物馆列表
        cursor = self.database.cursor()
        cursor.execute("select id, name from museum")
        self.museums = cursor.fetchall()
        cursor.close()

        # 使用百度文本分析api
        self.client = AipNlp(APP_ID, API_KEY, SECRET_KEY)

        # 从文件中读入停用词
        stopwords_csv = pd.read_csv("F:/Codes/PySpider/demoSpider/jieba/stopwords.txt",
                      index_col=False,
                      quoting=3,
                      sep="\t",
                      names=['stopword'],
                      encoding='utf-8') # quoting=3 全不引用

        # 构建停用词列表
        self.stopwords = []
        for i in range(0, stopwords_csv.shape[0]):
            self.stopwords.append(stopwords_csv.iloc[i, 0])
        
        # 构建情感词词典
        self.word_dict = {}

        # 从文件中读入情感词
        with open("F:/Codes/PySpider/demoSpider/BosonNLP_sentiment_score/BosonNLP_sentiment_score.txt", mode='r', encoding='UTF-8') as f:
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
            # 从文章中寻找是否提到了与某些博物馆有关的某些关系
            museums = self.museums
            for museum_id, museum_name in museums:
                # 若文本中出现了
                if museum_name in item['content']:
                    new_id = item['news_id']
                    cursor = self.database.cursor()
                    try:
                        cursor.execute("insert into museum_has_new (museum_id, new_id) values ({0}, {1})".format(museum_id, new_id))
                        self.database.commit()
                    except:
                        self.database.rollback()
                    cursor.close()

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

            # 根据TF-IDF频率算出tag
            if(tf_idf_score < 0):
                tag1 = 0
            elif(tf_idf_score < 1):
                tag1 = 1
            else:
                tag1 = 2

            # 根据TextRank()算法提取出关键词
            TextRank_keyword_list = analyse.textrank(item['content'], topK=50, withWeight=True)

            # 去掉空语气词并保留其语气词词频，并计算其情感得分
            l3 = []
            TextRank_score = 0
            for word in TextRank_keyword_list:
                if not word[0].isdigit() and not word[0].isspace() and word[0] not in self.stopwords:
                    l3.append(word)
                    TextRank_score += self.getScore(word)
            
            # 根据TextRank算出tag
            if(TextRank_score < 0):
                tag2 = 0
            elif(TextRank_score < 1):
                tag2 = 1
            else:
                tag2 = 2

            # 通过百度提供的接口方法进行情感倾向提取
            sentence = item['content'].replace('\xa0', "").replace('\xa9', '').replace('\xae', '').replace('\u2022', '').encode('gbk')
            sentence = sentence.decode('gbk')
            result = self.client.sentimentClassify(sentence)
            # 如果解析错误则填写上空值,使得程序不会出错而停止运行
            if "error_code" in result.keys():
                tag3 = -1
            else:
                data = result['items']
                items = data[0]
                tag3 = items['sentiment']
            
            # 进行投票获取最后结果
            if(tag3 != -1):
                tag = round((tag1 + tag2 + tag3)/3)
            else:
                tag = round(round(tag1 + tag2)/2)

            item['content_list'] = l1
            item['tag'] = tag

            # 更新数据库中新闻的标签
            sql = "UPDATE new set tag = {} WHERE id = '{}'".format(tag, item['news_id'])
            cursor = self.database.cursor()
            try:
                # 执行SQL语句
                cursor.execute(sql)
                # 提交到数据库执行
                self.database.commit()
            except:
                # 发生错误时回滚
                self.database.rollback()
            cursor.close()

            return item
        else:
            raise DropItem("Missing content in %s" % item)