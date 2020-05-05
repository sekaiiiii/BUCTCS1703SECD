## 05.02更新

- 实现了从数据库中获取新闻url列表的功能
- 实现了将url中的正文通过爬虫保存到本地的功能
- items.json存放保存到本地的新闻正文
- news.html为进行html解析时的测试html代码

## 05.05更新

- 修改了爬虫的settings，添加了数据库的全局配置以及爬虫的发爬虫设置
- 修改了pipeline.py，去除了某些xpath解析错误导致正文为空的数据项
- 更新了items.json文件