from scrapy import cmdline

import time
import os

while True:
    os.system("scrapy crawl p -o {}.json".format(time.time()))
    time.sleep(100)  #每隔一天运行一次 24*60*60=86400s

#cmdline.execute("scrapy crawl p".split())
#cmdline.execute("scrapy crawl p -o collection101.json".split())