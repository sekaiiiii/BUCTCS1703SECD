package com.buct.museumguide.Service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Switch;

import com.buct.museumguide.R;
import com.buct.museumguide.bean.PostResultMessage;
import com.buct.museumguide.bean.WebRequestMessage;
import com.buct.museumguide.ui.FragmentForMain.CommonList.CommonList;
import com.buct.museumguide.util.WebHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import androidx.annotation.Nullable;
import okhttp3.RequestBody;

public class OnOpenGetMessage extends Service {
    private ExecutorService fixedThreadPool;
    private Runnable command;
    private Runnable setInforunnable(String url){
       return new Runnable(){
            @Override
            public void run() {
                try {
                    String res=WebHelper.getInfo(url);
                    EventBus.getDefault().postSticky(new ResultMessage(res));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
    private Runnable setStaterunnable(String url,String cookie){
        return new Runnable(){
            @Override
            public void run() {
                try {
                    String res=WebHelper.getInfoWithCookie(url,cookie);
                    EventBus.getDefault().post(new loginstatemessage(res));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
    private Runnable postRunnable(String url, RequestBody body){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    String res=WebHelper.postInfo(url,body);
                    EventBus.getDefault().post(new PostResultMessage(res));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
    private Runnable postRunnableWithCookie(String url,String cookie,RequestBody body){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    String res=WebHelper.postWithCookie(url,body,cookie);
                    EventBus.getDefault().post(new PostResultMessage(res));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Subscribe
    public void getMuseumInfo(MuseumInfoMsg museumInfoMsg) {
        command = new Runnable() {
            @Override
            public void run() {
                try {
                    String res = WebHelper.getInfo(museumInfoMsg.url);
                    EventBus.getDefault().postSticky(new MuseumInfoResultMsg(res));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        fixedThreadPool.execute(command);
    }

    @Subscribe
    public void getExhibition(ExhibitionMsg exhibitionMsg) {
        command = new Runnable() {
            @Override
            public void run() {
                try {
                    String res = WebHelper.getInfo(exhibitionMsg.url);
                    EventBus.getDefault().postSticky(new ExhibitionResultMsg(res));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        fixedThreadPool.execute(command);
    }

    @Subscribe
    public void getCollection(CollectionMsg collectionMsg) {
        command = new Runnable() {
            @Override
            public void run() {
                try {
                    String res = WebHelper.getInfo(collectionMsg.url);
                    EventBus.getDefault().postSticky(new CollectionResultMsg(res));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        fixedThreadPool.execute(command);
    }

    @Subscribe
    public void getNews(NewsMsg newsMsg) {
        command = new Runnable() {
            @Override
            public void run() {
                try {
                    String res = WebHelper.getInfo(newsMsg.url);
                    EventBus.getDefault().postSticky(new NewsResultMsg(res));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        fixedThreadPool.execute(command);
    }

    @Subscribe
    public void getEducation(EducationMsg educationMsg) {
        command = new Runnable() {
            @Override
            public void run() {
                try {
                    String res = WebHelper.getInfo(educationMsg.url);
                    EventBus.getDefault().postSticky(new EducationResultMsg(res));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        fixedThreadPool.execute(command);
    }

    @Subscribe
    public void getComment(CommentMsg commentMsg) {
        command = new Runnable() {
            @Override
            public void run() {
                try {
                    String res = WebHelper.getInfo(commentMsg.url);
                    EventBus.getDefault().postSticky(new CommentResultMsg(res));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        fixedThreadPool.execute(command);
    }

    @Subscribe
    public void getMuseumListDefaultMsg(MuseumListDefaultMsg museumListDefaultMsg){
        command = new Runnable() {
            @Override
            public void run() {
                try {
                    String res = WebHelper.getInfo(museumListDefaultMsg.url);
                    EventBus.getDefault().postSticky(new MuseumListDefaultResultMsg(res));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        fixedThreadPool.execute(command);
    }

    @Subscribe
    public void getMuseumListTimeMsg(MuseumListTimeMsg museumListTimeMsg){
        command = new Runnable() {
            @Override
            public void run() {
                try {
                    String res = WebHelper.getInfo(museumListTimeMsg.url);
                    EventBus.getDefault().postSticky(new MuseumListTimeResultMsg(res));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        fixedThreadPool.execute(command);
    }

    @Subscribe
    public void getMuseumListNumberMsg(MuseumListNumberMsg museumListNumberMsg){
        command = new Runnable() {
            @Override
            public void run() {
                try {
                    String res = WebHelper.getInfo(museumListNumberMsg.url);
                    EventBus.getDefault().postSticky(new MuseumListNumberResultMsg(res));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        fixedThreadPool.execute(command);
    }

    @Subscribe
    public void getMuseumListCommentMsg(MuseumListCommentMsg museumListCommentMsg){
        command = new Runnable() {
            @Override
            public void run() {
                try {
                    String res = WebHelper.getInfo(museumListCommentMsg.url);
                    EventBus.getDefault().postSticky(new MuseumListCommentResultMsg(res));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        fixedThreadPool.execute(command);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        fixedThreadPool = Executors.newFixedThreadPool(5);
        System.out.println("服务被创建了");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EventBus.getDefault().post(new StateBroadCast(1));
        System.out.println("服务被启动了");
        /*博物馆列表信息*/
        String cookie=WebHelper.getCookie(this);
        command=setInforunnable("http://192.144.239.176:8080/api/android/get_museum_info");
        fixedThreadPool.execute(command);
        fixedThreadPool.execute(setStaterunnable(getResources().getString(R.string.get_login_state_url),cookie));
        return super.onStartCommand(intent, flags, startId);
    }

    public class binder extends Binder{
        public void getcommand(){
            System.out.println("555");
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("服务被绑定了");

        return new binder();
    }

    @Override
    public boolean onUnbind(Intent intent) {

        return super.onUnbind(intent);
    }
    /*只有无cookie请求的get*/
    @Subscribe
    public void get(CommandRequest msg){
        System.out.println(msg.url);
        command=setInforunnable(msg.url);
        fixedThreadPool.execute(command);
    }
    @Subscribe
    public void getRequest(WebRequestMessage msg){
        String url=msg.url;
        int code=msg.requestcode;
        System.out.println(url+" "+code);
        switch(code){
            case 100 :
                fixedThreadPool.execute(setStaterunnable(url,msg.cookie));
                //语句
                break;
            case 200 :
                //语句
                fixedThreadPool.execute(postRunnable(url,msg.body));
                break;
            case 300:
                fixedThreadPool.execute(postRunnableWithCookie(url,msg.cookie,msg.body));
                break;
            case 400:
                command=setInforunnable(msg.url);
                fixedThreadPool.execute(command);
                break;
            default :
                break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        fixedThreadPool.shutdown();
    }
    @Subscribe
    public void onReceiveUpdate(UpdateMsg result){
        command = new Runnable() {
            @Override
            public void run() {
                try {
                    String res = WebHelper.getInfo("http://192.144.239.176:8080/api/android/get_version_num");
                    EventBus.getDefault().postSticky(new UpdateResult(res));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        if(result.msg.equals("1")){
            fixedThreadPool.execute(command);
           // http://192.144.239.176:8080/api/android/get_version_num
        }
    }
}
