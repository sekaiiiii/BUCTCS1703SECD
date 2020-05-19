import csv
import pandas as pd
import re
import jieba
import numpy as np


# 预处理程序使用说明：
# 默认第一行为列名，不进行加载。
# 每次运行完函数的时候，保存为csv文件需要填写列名header


def test_addcol(infile):
    """
    给官方给的测试集新增一列label=0。
    :param infile:
    :return:
    """
    df = pd.read_csv(infile)
    df['label'] = 0
    df.to_csv('testfiles/Test_Addlabel.csv', index=0, header=['id', 'title', 'content', 'label'])


def train_id(infile1, infile2):
    """
    官方给的训练集csv两个文件，id不一致，返回两个文件共有的id交集。
    :param infile1:
    :param infile2:
    :return:
    """
    csv_file = open(infile1, 'r', encoding='utf-8')
    csv_read = csv.reader(csv_file)
    content_id = set()

    for item in csv_read:
        if csv_read.line_num == 1:  # 去除第一行和不是32位长度的id
            continue
        if len(item[0]) != 32:
            continue
        content_id.add(item[0])

    csv_file.close()

    # print('train数据集个数:{}'.format(len(content_id)))

    csv_file = open(infile2, 'r', encoding='utf-8')
    csv_read = csv.reader(csv_file)
    label_id = set()

    for item in csv_read:
        if csv_read.line_num == 1:  # 去除第一行和不是32位长度的id
            continue
        if len(item[0]) != 32:
            continue
        label_id.add(item[0])

    csv_file.close()

    # print('label数据集个数:{}'.format(len(label_id)))

    return content_id & label_id  # 官方给的两个csv文件的交集


def train_dataset(infile1, infile2, train_id):
    """
    根据共有id，将两个文件合并为一个，列分别为id,title,content,label。
    :param infile1:
    :param infile2:
    :param train_id:
    :return:
    """
    train_dataset = []
    content = []
    label = []
    csv_file = open(infile1, 'r', encoding='utf-8')
    csv_read = csv.reader(csv_file)
    for item in csv_read:
        if csv_read.line_num == 1:  # 跳过第一行 默认是列标题
            continue
        content.append(item)
    csv_file.close()

    csv_file = open(infile2, 'r', encoding='utf-8')
    csv_read = csv.reader(csv_file)
    for item in csv_read:
        if csv_read.line_num == 1:
            continue
        label.append(item)
    csv_file.close()

    for item in train_id:
        temp = []
        for item1 in content:
            if item == item1[0]:
                for item2 in label:
                    if item == item2[0]:
                        try:
                            temp.append(item)
                            temp.append(item1[1])
                            # print(item1[1])
                            temp.append(item1[2])
                            temp.append(item2[1])
                            train_dataset.append(temp)
                        except:
                            continue
                    else:
                        continue
            else:
                continue
    return train_dataset


def train_clear(infile):
    """
    清除输入文件中的数字、英文、各种符号。
    :param infile:
    :return:
    """
    train_dataset = []

    csv_file = open(infile, 'r', encoding='utf-8')
    csv_read = csv.reader(csv_file)
    for item in csv_read:
        if csv_read.line_num == 1:  # 跳过第一行 默认是列标题
            continue

        temp1 = re.sub('[a-zA-Z0-9]', '', item[1])
        temp1 = re.sub('[\s+\.\!\/_,$%^*(+\"\'；：“”．]+|[+——！，。？?[\]《》：()|<>｜【】「」¥＂:、~@#￥%……&*（）]+', '', temp1)

        if len(item) < 4:  # 如果该条数据没有content字段，则补为0
            train_dataset.append([item[0], temp1, 0, item[3]])
            continue

        temp2 = re.sub('[a-zA-Z0-9]', '', item[2])
        temp2 = re.sub('[\s+\.\!\/_,$%^*(+\"\'；：“”．]+|[+——！，。？?[\]《》：()|<>｜【】「」¥＂:、~@#￥%……&*（）]+', '', temp2)

        train_dataset.append([item[0], temp1, temp2, item[3]])

    csv_file.close()

    return train_dataset


def stopword():
    """
    返回停用词词表
    :return:
    """
     # 从文件中读入停用词
    stopwords_csv = pd.read_csv("F:/Codes/PySpider/demoSpider/jieba/stopwords.txt",
                    index_col=False,
                    quoting=3,
                    sep="\t",
                    names=['stopword'],
                    encoding='utf-8') # quoting=3 全不引用

    # 构建停用词列表
    stopwords = []
    for i in range(0, stopwords_csv.shape[0]):
        stopwords.append(stopwords_csv.iloc[i, 0])
    return stopwords


def sent2word(infile):
    """
    使用jieba进行分词，并去除停用词。
    :param infile:
    :return:
    """
    train_dataset = []
    stopwordlist = stopword()
    csv_file = open(infile, 'r', encoding='utf-8')
    csv_read = csv.reader(csv_file)
    for item in csv_read:
        if csv_read.line_num == 1:
            continue

        seg_sentence1 = ''
        seg_sentence2 = ''
        seg_list1 = jieba.cut(item[1], cut_all=False)
        seg_list2 = []
        if len(item) < 4:
            seg_list2 = ['temp']

        else:
            seg_list2 = jieba.cut(item[2], cut_all=False)

        for word1 in seg_list1:
            if word1 not in stopwordlist:
                if word1 != '\t':
                    seg_sentence1 += word1 + ' '

        for word2 in seg_list2:
            if word2 not in stopwordlist:
                if word2 != '\t':
                    seg_sentence2 += word2 + ' '

        train_dataset.append([item[0], seg_sentence1.strip() + ' ' + seg_sentence2.strip(), item[3]])

    return train_dataset


if __name__ == '__main__':
    # 针对官方训练集的处理示例
    train_id = train_id('trainfiles/Train_DataSet.csv', 'trainfiles/Train_DataSet_Label.csv')
    train_dataset = train_dataset('trainfiles/Train_DataSet.csv', 'trainfiles/Train_DataSet_Label.csv', train_id)
    to_train = pd.DataFrame(train_dataset)
    to_train.to_csv('trainfiles/Train.csv', index=False, header=['id', 'title', 'content', 'label'])
    
    train_clear = train_clear('trainfiles/Train.csv')
    to_cleartext = pd.DataFrame(train_clear)
    to_cleartext.to_csv('trainfiles/Train_Clear.csv', index=False, header=['id', 'title', 'content', 'label'])
    
    train_word = sent2word('trainfiles/Train_Clear.csv')
    to_word = pd.DataFrame(train_word)
    to_word.to_csv('trainfiles/Train_Word.csv', index=False, header=['id', 'content', 'label'])

    pass