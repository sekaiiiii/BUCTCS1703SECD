import scrapy
from u.items import CollectionItem
from urllib.parse import urljoin
from urllib import parse

museum_id = 4
base_url = 'http://www.capitalmuseum.org.cn/jpdc/'
#首都博物馆
class Collection_4(scrapy.Spider):
    name = 'd'
    allowed_domains = ["www.capitalmuseum.org.cn"]
    start_urls = [
        'http://www.capitalmuseum.org.cn/jpdc/jpdc.htm'
    ]

    def parse(self, response):
        href_list = response.xpath("//a/@href").getall()
        #href_list = response.xpath("//a/@href").get()
        href_set = []
        [href_set.append(href) for href in href_list if not href in href_set]
        for href in href_set:
            url = base_url + href
            yield  scrapy.Request(url=url,callback=self.get_parse,meta={'href':href_set})

    def get_parse(self, response):
        url = base_url + response.xpath("//a/@href").get()
        yield  scrapy.Request(url = url,callback=self.get_information)

    def get_information(self,response):
        item = CollectionItem()
        id = response.url.split('/')[-1].split('_')[-1].split('.')[0]
        item['id'] = int(str(museum_id + 100) + id)
        item['name'] = response.xpath("//td/text()").getall()[2]
        content = response.xpath("//p/text()").getall()
        item['content'] =  ''.join(content).replace('\u3000','').replace('\xa0','')
        item['material'] = response.xpath("//font[@class = 'b1title']/text()").get()
        item['tag'] = ""

        item['museum_id'] = museum_id
        img_url = base_url + 'content'
        item['img_url'] = img_url +  str(response.xpath("//img/@src").get()).replace('../..','')
        yield item

        next_url  = response.xpath("//a[@id = 'FounderPrevious']/@href").get()
        if next_url:
            #next_url = base_url.urljoin(str(next_url.split('/')[1:]))
            #url = base_url + 'content'
            #next_url = parse.urljoin(url,next_url)
            yield response.follow(next_url, callback=self.get_information)







