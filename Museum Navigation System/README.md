## 计科1703软件工程4组代码更新及教程日志

### 1 新增服务OnOpenGetMessage

简单来说，eventbus是一种应用内广播的简易版，可以把数据在activity，service，fragment之间传递，并且代码极易简介

目前已经在webhelper里封装了post，get和带cookie的请求以及获取cookie等，可按注释自行查阅

（必须在子线程）

OnOpenGetMessage封装了一个线程池，通过eventbus通知服务启动线程池然后开始网络请求

### 2 代码演示

1 必须要为按需的String字符串创建一个类

例如（注意 string必为final）

```java
public class PlayMessage {
    public final String msg;

    public PlayMessage(String msg) {
        this.msg = msg;
    }
}
```

2 在你所在的fragment里注册和解注册eventbus

例如 在onviewcreate里注册

```java
EventBus.getDefault().register(this);
```

在ondestory里解注册

```java
EventBus.getDefault().unregister(this);
```

在你需要申请网络请求的地方

```java
EventBus.getDefault()
        .post(new
                CommandRequest
                ("http://192.144.239.176:8080/api/android/get_education_activity_info"));
```

这里的commandrequest发送后会按http地址进行网络请求并发送

`EventBus.getDefault().post(new ResultMessage(res));`

这里的即为网络请求结果。

如果有按需的，比如post什么的记得提醒我，还没写post的

最后在fragment里创建函数接受网络请求结果

注意，必须带上@**Subscribe**的注解

```
@Subscribe
public void GetLoginState(loginstatemessage msg){
//在这里实现你的逻辑
//不声明所在线程则为子线程，更新ui需要handler等手段
}
```

### 3 关于接口

接口已全部写入string.xml，使用getResources().getString(R.string.loginurl)来使用接口

### 4 关于viewmodel和livedata

暂时认为只有需要fragment分发数据时共用，例如fragment套viewpager



2020.5.14

### 新增带cookie的post和get接口

只有按需返回数据时才应调用这些接口，如果需要使用返回体内的header还是需要自己做的

```java
public class WebRequestMessage {
    public final String url;
    public final int requestcode;
    public final String cookie;
    public final RequestBody body;
    // 100为带cookie的get请求，200为post请求，300为带cookie的post请求，400为普通的get请求
    public WebRequestMessage(String url, int requestcode, String cookie, RequestBody body) {
        this.url = url;
        this.requestcode = requestcode;
        this.cookie = cookie;
        this.body = body;
    }
}
```

新增请求信息体，按需发送

请求100 返回 ResultMessage

请求200 返回 loginstatemessage

300 和400 返回 PostResultMessage