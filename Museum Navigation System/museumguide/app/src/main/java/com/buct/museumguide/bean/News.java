package com.buct.museumguide.bean;

import org.json.JSONObject;

import java.util.ArrayList;

public class News {
    private int id;
    private String title;
    private String author;
    private String time;
    private String description;
    private String content;
    private String url;
    private int tag;
    private String imgUrl;
    public News(int id,String title,String author,String time,String description,String content,String url,int tag, String imgUrl){
        this.id=id;
        this.title=title;
        this.author=author;
        this.time=time;
        this.description=description;
        this.content=content;
        this.url=url;
        this.tag=tag;
        this.imgUrl = imgUrl;
    }
    public News(JSONObject jsonObject) throws Exception{
        this.id=jsonObject.getInt("id");
        this.title=jsonObject.getString("title");
        this.author=jsonObject.getString("author");
        this.time=jsonObject.getString("time");
        this.description=jsonObject.getString("description");
        this.content=jsonObject.getString("content");
        this.url=jsonObject.getString("url");
        this.tag=jsonObject.getInt("tag");
//        this.imgUrl =jsonObject.getString("imgUrl") ;
        this.imgUrl = "https://pics0.baidu.com/feed/3b87e950352ac65c7cebdefe202d3e1791138ab3.jpeg?token=98e84382460deb40d2ff61047ae33aca";
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getTag() {
        return tag;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public static ArrayList<News> getTestData() {
        ArrayList<News> list = new ArrayList<>();
//        for(int i=0;i<5;++i) {
        list.add(new News(1, 1 + "溧阳看馆藏|元代梵文准提咒镜",
                "地方焦点",
                "2020-04-22 20:31:44",
                "1",
                "今天为大家带来溧阳馆藏第三十二期——元代梵文准提咒镜。铜镜直径8.2厘米,边厚0.3厘米。银锭形钮,主体纹饰为环绕镜钮两圈的梵文铭文圈,内圈为十六字梵...",
                "https://baijiahao.baidu.com/s?id=1664675906753328795&wfr=spider&for=pc",
                1,
                "https://pics0.baidu.com/feed/3b87e950352ac65c7cebdefe202d3e1791138ab3.jpeg?token=98e84382460deb40d2ff61047ae33aca"));
        list.add(new News(1, 2 + "溧阳看馆藏|元代梵文准提咒镜",
                "地方焦点",
                "2020-04-22 20:31:44",
                "1",
                "今天为大家带来溧阳馆藏第三十二期——元代梵文准提咒镜。铜镜直径8.2厘米,边厚0.3厘米。银锭形钮,主体纹饰为环绕镜钮两圈的梵文铭文圈,内圈为十六字梵...",
                "https://baijiahao.baidu.com/s?id=1664675906753328795&wfr=spider&for=pc",
                1,
                "https://img.zcool.cn/community/0148fc5e27a173a8012165184aad81.jpg"));
        list.add(new News(1, 3 + "溧阳看馆藏|元代梵文准提咒镜",
                "地方焦点",
                "2020-04-22 20:31:44",
                "1",
                "今天为大家带来溧阳馆藏第三十二期——元代梵文准提咒镜。铜镜直径8.2厘米,边厚0.3厘米。银锭形钮,主体纹饰为环绕镜钮两圈的梵文铭文圈,内圈为十六字梵...",
                "https://baijiahao.baidu.com/s?id=1664675906753328795&wfr=spider&for=pc",
                1,
                "https://img.zcool.cn/community/013c7d5e27a174a80121651816e521.jpg"));
        list.add(new News(1, 4 + "溧阳看馆藏|元代梵文准提咒镜",
                "地方焦点",
                "2020-04-22 20:31:44",
                "1",
                "今天为大家带来溧阳馆藏第三十二期——元代梵文准提咒镜。铜镜直径8.2厘米,边厚0.3厘米。银锭形钮,主体纹饰为环绕镜钮两圈的梵文铭文圈,内圈为十六字梵...",
                "https://baijiahao.baidu.com/s?id=1664675906753328795&wfr=spider&for=pc",
                1,
                "https://img.zcool.cn/community/01b8ac5e27a173a80120a895be4d85.jpg"));
//        }
        return list;
    }
    public static News getOneTestData() {
        return new News(1, 1 + "溧阳看馆藏|元代梵文准提咒镜",
                "地方焦点",
                "2020-04-22 20:31:44",
                "1",
                "今天为大家带来溧阳馆藏第三十二期——元代梵文准提咒镜。铜镜直径8.2厘米,边厚0.3厘米。银锭形钮,主体纹饰为环绕镜钮两圈的梵文铭文圈,内圈为十六字梵...",
                "https://baijiahao.baidu.com/s?id=1664675906753328795&wfr=spider&for=pc",
                1,
                "https://pics0.baidu.com/feed/3b87e950352ac65c7cebdefe202d3e1791138ab3.jpeg?token=98e84382460deb40d2ff61047ae33aca");
    }
}
