import scrapy
from u.items import UItem

#以id为46的苏州博物馆为例,编码格式为utf-8
class museum_46(scrapy.Spider):
    name = 'museum_46'       #命名museum_id,类名也要更新
    start_urls = [
        # 苏州博物馆           #博物馆名字及时更新
        'https://baike.baidu.com/item/%E8%8B%8F%E5%B7%9E%E5%8D%9A%E7%89%A9%E9%A6%86/1629584'
    ]

    def parse(self, response):
        item = UItem()
        item['id'] = 46
        name = response.xpath("//h1")             #name这个xpath基本不用修改，这个是通用的
        item['name'] = name.xpath('string(.)').get()  #提取各标签文本并连接成字符串
        item['establishment_time'] = ""   #这行不用动，先全空
        open_time = response.xpath("/html/body/div[3]/div[2]/div/div[2]/div[79]")                #这个是用相对路径，直接像introduction那样复制绝对路径也行
        item['time'] = open_time.xpath("string(.)").get()
        introduction = response.xpath("/html/body/div[3]/div[2]/div/div[2]/div[4]") #复制xpath，这是绝对路径
        item['introduction'] = introduction.xpath("string(.)").get()
        #response.xpath('//div[@class="lemma-summary"]//div[@class="para"]//text()').getall()
        visit_info = response.xpath("/html/body/div[3]/div[2]/div/div[2]/div[81]")
        item['visit_info'] = visit_info.xpath("string(.)").get()    #参观信息交通方面
        attention = response.xpath('//div[80]')     #注意事项
        item['attention'] = attention.xpath("string(.)").get()
        yield item