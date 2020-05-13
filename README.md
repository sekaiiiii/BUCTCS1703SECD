# BUCT计科1703班4组软件工程课设仓库

## 使用须知
设计及周记请移步wiki

本readme用于记录开发进度

2020.5.6

完成登录，退出登录，注册功能，完成详细信息浏览页面搭建，跳转到高德地图功能

## 再次重申，布局请使用约束布局！！！！

2020.5.13

加入eventbus及service，现设计网络请求标准如下

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