import scrapy
from u.items import CollectionItem
from urllib.parse import urljoin

class Collection(scrapy.Spider):
    name = 'p'
    #allowed_domains = ["dpm.org.cn/collection"]
    #start_urls =

    def start_requests(self):
        min_page = 227009
        max_page = 227020
        #227598
        base_url = "https://www.dpm.org.cn/collection/ceramic"
        for page in range(min_page, max_page + 1):
            url = base_url + str("/") + str(page)
            #url = urljoin(base_url,str(page))
            page += 1
            yield scrapy.Request(url=url, callback=self.parse, meta={'id': page-1})

    def parse(self, response):
        item = CollectionItem()
        base_url = "https://img.dpm.org.cn"
        item['id'] = int('1' + str(response.meta['id']))
        item['name'] = response.xpath("//span[@class = 'span1']/text()").get()
        #introduction = response.xpath("//div[@class = 'content_edit']/p/span[not(@class = 'lemma-item')]")

        content_text   = response.xpath("//div[@class = 'content_edit']/*[not(name = 'span')]/text()").getall()
        content = " ".join(content_text).replace('\u3000','')

        item['content'] = content
        item['material'] = ""
        #tem['tag'] = "陶瓷"
        item['museum_id'] = 1
        img_url = response.xpath("//div[@class = 'pic']/img/@data-img").get()
        item['img_url'] = urljoin(base_url,img_url)
        #item[collection_id] = image_guid
        yield item



