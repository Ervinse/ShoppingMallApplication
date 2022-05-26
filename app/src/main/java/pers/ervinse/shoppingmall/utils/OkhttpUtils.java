package pers.ervinse.shoppingmall.utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpUtils {

    private static OkHttpClient okHttpClient = new OkHttpClient();

    public static String doGet(String url) throws IOException {
        Request request=new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        Response resp = call.execute();
        return resp.body().string();
    }
    public static String doPost(String url,String json) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json);
        Request request = new Request.Builder().post(requestBody).url(url).build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        return response.body().string();

    }

}
