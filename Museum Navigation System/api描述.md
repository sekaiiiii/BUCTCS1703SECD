#### 用户登录/注册/修改api说明

IP 地址49.233.81.150:9090

###### Get 示例49.233.81.150:9090/api/login?name=abc&psw=def

###### post示例 49.233.81.150:9090/api/Login body：{"name":"abc","psw":"def"}

| ip         | 49.233.81.150:9090 | 方式 | 参数1 | 参数2 | body                         | 返回值                                           |
| ---------- | ------------------ | ---- | ----- | ----- | ---------------------------- | ------------------------------------------------ |
| api/test   | 返回666            | GET  | 无    | 无    | 无                           |                                                  |
| api/login  | 返回值见后面       | GET  | name  | psw   | 无                           | 没有注册 返回-2，密码错误 返回-1 登录成功  返回0 |
| api/insert | 返回值见后面       | GET  | name  | psw   | 无                           | 插入成功返回1，已存在用户返回-1 其他返回0        |
| api/update | 返回值见后面       | GET  | name  | psw   | 无                           | 更改成功返回1，已存在用户返回-1  其他返回0       |
| api/Login  | 返回值见后面       | POST | 无    | 无    | {"name":"账号","psw":"密码"} | 没有注册 返回-2，密码错误 返回-1 登录成功  返回0 |
| api/Insert | 返回值见后面       | POST | 无    | 无    | {"name":"账号","psw":"密码"} | 插入成功返回1，已存在用户返回-1 其他返回0        |
| api/Update | 返回值见后面       | POST | 无    | 无    | {"name":"账号","psw":"密码"} | 更改成功返回1，已存在用户返回-1  其他返回0       |

okhhtp请求示例（仅在测试环境中有效）：

```java
public static final MediaType JSON
        = MediaType.get("application/json; charset=utf-8");
OkHttpClient client = new OkHttpClient();
private String post(String url, String json) throws IOException {
    RequestBody body = RequestBody.create(json, JSON);
    Request request = new Request.Builder()
            .url(url)
            .post(body)
            .build();
    try (Response response = client.newCall(request).execute()) {
        return response.body().string();
    }
}
public void httptest() throws IOException, JSONException {
        HashMap<String,String> json = new HashMap<String,String>();
        json.put("name","abc");
        json.put("psw","123456");
        Gson gson=new Gson();
        String r=gson.toJson(json);
        System.out.println(r);
        String result=post("http://49.233.81.150:9090/api/Login",r);
        System.out.println(result);
    }
//0 登录成功标志
```

以下为app中网络请求的示例

安卓网络请求要求异步且9.0版本后要求https传输，需要做以下准备工作

   1.导入 `implementation("com.squareup.okhttp3:okhttp:4.4.0")`

2. 在AndroidMainifest中的Android子目录下设置android:usesCleartextTraffic="true"

3. 建立空活动，并在xml中布局（什么办法都可以）

   布局示例

   ![image-20200402163400235](D:\BUCTCS1703SECD\Museum Navigation System\image-20200402163400235.png)

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".DisplayMessageActivity">

    <Button
        android:id="@+id/button4"
        android:layout_width="150dp"
        android:layout_height="54dp"
        android:layout_marginStart="40dp"
        android:onClick="onClick"
        android:text="登录"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toStartOf="@+id/button5"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/editText3"
        app:layout_constraintVertical_bias="0.551" />

    <Button
        android:id="@+id/button5"
        android:layout_width="98dp"
        android:layout_height="54dp"
        android:onClick="onClick1"
        android:text="注册"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toEndOf="@+id/button4"
        app:layout_constraintTop_toBottomOf="@+id/editText4"
        app:layout_constraintVertical_bias="0.341" />

    <EditText
        android:id="@+id/editText3"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginTop="32dp"
        android:ems="10"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toEndOf="@+id/textView2"
        app:layout_constraintTop_toBottomOf="@+id/textView" />

    <EditText
        android:id="@+id/editText4"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:ems="10"
        android:inputType="textPassword"
        app:layout_constraintBottom_toTopOf="@+id/button4"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toEndOf="@+id/textView3"
        app:layout_constraintTop_toBottomOf="@+id/editText3" />

    <TextView
        android:id="@+id/textView2"
        android:layout_width="83dp"
        android:layout_height="35dp"
        android:text="@string/label_login"
        android:textAppearance="@style/TextAppearance.AppCompat.Large"
        app:layout_constraintBottom_toBottomOf="@+id/editText3"
        app:layout_constraintEnd_toStartOf="@+id/editText3"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="@+id/editText3" />

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="52dp"
        android:text="登录界面"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.542"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/textView3"
        android:layout_width="86dp"
        android:layout_height="34dp"
        android:text="@string/label_password"
        android:textAppearance="@style/TextAppearance.AppCompat.Large"
        app:layout_constraintBottom_toTopOf="@+id/button4"
        app:layout_constraintEnd_toStartOf="@+id/editText4"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/textView2" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

4 在activity里新建函数（public，参数为View view），并在layout的选项里绑定

![image-20200402163520274](D:\BUCTCS1703SECD\Museum Navigation System\image-20200402163520274.png)

5 在函数里进行网络请求和ui更新（注意要新建线程）

设定json传输的参数

```java
public static final MediaType JSON
        = MediaType.get("application/json; charset=utf-8");
```

请求详情

```java
OkHttpClient client = new OkHttpClient();
HashMap<String,String> map=new HashMap<>();
EditText e1=(EditText)findViewById(R.id.editText3);
EditText e2=(EditText)findViewById(R.id.editText4);
map.put("name",e1.getText().toString());
map.put("psw",e2.getText().toString());
Gson gson=new Gson();//Gson的导入方法见上
String json=gson.toJson(map);
RequestBody body = RequestBody.create(json, JSON);
Request request = new Request.Builder()
        .url("http://49.233.81.150:9090/api/Login")
        .post(body)
        .build();
Call call = client.newCall(request);//设置回调
call.enqueue(new Callback() {
    @Override
    public void onFailure(Call call, IOException e) {
        //...
        Log.e(TAG, "onFailure: ",e);
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if(response.isSuccessful()){
            final String result = response.body().string();
            //处理UI需要切换到UI线程处理
            if(result.equals("0")){
                Intent intent=new Intent(DisplayMessageActivity.this,SuccessLogin.class);
                startActivity(intent);
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(result.equals("-2")){
                            commonDialog("用户不存在！");
                        }else if(result.equals("-1")){
                            commonDialog("密码错误！");
                        }
                    }
                });//设立提示框或者切换到其他页面
            }
        }
    }
});
```

6 toast（子线程里需要looper环绕，具体方法请自行百度）

```java
Toast.makeText(你的类名.this,你想打印的东西,Toast.LENGTH_SHORT).show();
```

7 活动间跳转

```java
Intent intent=new Intent(现在的类.this,要跳转的类.class);
startActivity(intent);
```

8 打印日志 自己学