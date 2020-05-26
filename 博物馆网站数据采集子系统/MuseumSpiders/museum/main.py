from scrapy import cmdline

import time
import os

#while True:
    #ntime = time.time()
    #os.system("scrapy crawl p -o {}.json".format(ntime))
    #os.system("python ontime.py")
    #time.sleep(100)  #每隔一天运行一次 24*60*60=86400s

#cmdline.execute("scrapy crawl z".split())
cmdline.execute("scrapy crawl museum_46 -o museum.json".split())