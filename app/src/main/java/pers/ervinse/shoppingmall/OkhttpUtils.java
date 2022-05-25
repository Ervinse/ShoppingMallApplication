package pers.ervinse.shoppingmall;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpUtils {

    public OkhttpUtils() {
    }

    private static OkHttpClient okHttpClient = new OkHttpClient();


    public static String doGet(String url) throws IOException {

        //通过Request.Builder构建一个Request请求实例request
        Request request = new Request.Builder()
                .url(url)
                .build();
        //通过client.newCall(request)创建一个Call的实例
        Call call = okHttpClient.newCall(request);
        //Call的实例调用execute方法发送同步请求
        Response resp = call.execute();
        //请求返回的response转换为String类型返回
        return resp.body().string();
    }

    public static String doPost(String url, String json) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json);
        Request request = new Request.Builder().post(requestBody).url(url).build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        return response.body().string();

    }

}
