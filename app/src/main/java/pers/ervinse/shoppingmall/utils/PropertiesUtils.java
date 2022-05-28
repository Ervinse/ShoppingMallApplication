package pers.ervinse.shoppingmall.utils;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    private static final String TAG = "PropertiesUtils";

    Context mContext;

    public static String getUrl(Context mContext){
        String ip = PropertiesUtils.getIP(mContext);
        String port = PropertiesUtils.getPort(mContext);
        return "http://" + ip + ":" + port;
    }

    public static String getIP(Context mContext){

        Properties properties = getProperties(mContext);
        return properties.getProperty("IP");
    }

    public static String getPort(Context mContext){

        Properties properties = getProperties(mContext);
        return properties.getProperty("port");
    }

    private static Properties getProperties(Context mContext){
        Properties properties = new Properties();
        try {
            InputStream inputStream = mContext.getAssets().open("config.properties");
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "配置文件加载失败");
        }
        return properties;
    }
}
