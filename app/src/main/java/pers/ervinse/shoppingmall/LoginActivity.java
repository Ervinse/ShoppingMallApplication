package pers.ervinse.shoppingmall;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import pers.ervinse.shoppingmall.domain.Result;
import pers.ervinse.shoppingmall.domain.User;
import pers.ervinse.shoppingmall.utils.OkhttpUtils;
import pers.ervinse.shoppingmall.utils.PropertiesUtils;

/**
 * 登录页面
 */
public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    //注册请求码
    private static final int REGISTER_REQUEST_CODE = 1;

    private Context mContext;

    private EditText login_name_et, login_password_et;
    private Button user_register_btn, login_btn;

    private String userName, userPassword,userDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;

        login_name_et = findViewById(R.id.login_name_et);
        login_password_et = findViewById(R.id.login_password_et);
        user_register_btn = findViewById(R.id.user_register_btn);
        login_btn = findViewById(R.id.login_btn);


        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {

        //登录按钮监听事件
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "登录按钮响应事件");

                //获取当前输入框内容
                userName = login_name_et.getText().toString();
                userPassword = login_password_et.getText().toString();

                login();


            }
        });

        //注册按钮监听事件
        user_register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "前往注册页面事件");

                //跳转注册页面
                Intent intent = new Intent(mContext, RegisterActivity.class);
                startActivityForResult(intent, REGISTER_REQUEST_CODE);
            }
        });
    }


    /**
     * 在子线程中发送登录请求
     */
    private void login(){

        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "进入请求登录线程");


                Gson gson = new Gson();
                User user = new User(userName, userPassword);
                String userJson = gson.toJson(user);
                Log.i(TAG, "登录请求json:" + userJson);
                String responseJson = null;
                Result result = null;
                try {
                    //发送登录请求
                    String url = PropertiesUtils.getUrl(mContext);
                    responseJson = OkhttpUtils.doPost(url + "/users/login", userJson);
                    Log.i(TAG, "登录请求响应json:" + responseJson);
                    responseJson = gson.fromJson(responseJson,String.class);
                    Log.i(TAG, "登录请求响应解析数据:" + responseJson);
                    if (responseJson != null){
                        //登录成功
                        if (responseJson.equals("true")){
                            //发送请求获取当前用户名对应的简介
                            responseJson = OkhttpUtils.doGet(url + "/users/getDescription/" + userName);
                            Log.i(TAG, "获取描述请求响应json:" + responseJson);
                            userDesc = gson.fromJson(responseJson, String.class);
                            Log.i(TAG, "获取描述请求响应解析数据:" + userDesc);
                            //回传用户名和简介
                            Intent intent = new Intent();
                            intent.putExtra("userName", userName);
                            intent.putExtra("userDesc", userDesc);
                            //设置数据状态
                            setResult(RESULT_OK, intent);
                            //销毁当前方法
                            finish();
                        }else {
                            //登录失败
                            //子线程中准备Toast
                            Looper.prepare();
                            Toast.makeText(mContext, "登录失败,用户名或密码错误", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                //抛出异常
                } catch (IOException e) {
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(mContext, "服务器异常", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

            }
        }.start();

    }

    /**
     * 数据回传接收方法
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //判断由哪一个intent发出当请求（是否是requestCode == 1）
        switch (requestCode) {
            //注册页面回传数据
            case REGISTER_REQUEST_CODE:
                //判断返回当数据是否正常（判断是否是resultCode == RESULT_OK）
                if (resultCode == RESULT_OK) {
                    //获取数据并打印
                    userName = data.getStringExtra("userName");
                    userPassword = data.getStringExtra("userPassword");
                    userDesc = data.getStringExtra("userDesc");
                    Log.i(TAG, "用户注册数据回传: " +
                            "userName = " + userName +
                            ",userPassword = " + userPassword +
                            ",userDesc = " + userDesc);
                    //注册完自动登录
                    login();
                }
                break;
            default:
        }
    }
}