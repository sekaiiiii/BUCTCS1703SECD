import pandas as pd
import numpy as np

stopwords = pd.read_csv("./jieba/stopwords.txt" 
                      ,index_col=False
                      ,quoting=3
                      ,sep="\t"
                      ,names=['stopword']
                      ,encoding='utf-8') # quoting=3 全不引用 

for i in range(0, stopwords.shape[0]):
    print(stopwords.iloc[i, 0], type(stopwords.iloc[i, 0]))
f = lambda x: x not in stopwords
print(f('%'))