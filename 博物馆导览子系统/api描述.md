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

okhhtp请求示例：

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