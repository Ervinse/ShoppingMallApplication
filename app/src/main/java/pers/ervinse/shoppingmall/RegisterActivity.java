package pers.ervinse.shoppingmall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private EditText register_login_name_et, register_login_password_et,register_login_desc_et;
    private Button register_btn;

    private String userName,userPassword, userDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_login_name_et = findViewById(R.id.register_login_name_et);
        register_login_password_et = findViewById(R.id.register_login_password_et);
        register_login_desc_et = findViewById(R.id.register_login_desc_et);
        register_btn = findViewById(R.id.register_btn);

        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener(){

        //注册按钮监听事件
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean registerSuccess = true;
                Log.d(TAG, "注册请求事件");

                userName = register_login_name_et.getText().toString();
                userPassword = register_login_password_et.getText().toString();
                userDesc = register_login_desc_et.getText().toString();

                //TODO 发送注册请求

                //注册成功
                if(registerSuccess){
                    Log.d(TAG, "注册请求成功");
                    //注册成功
                    Intent intent = new Intent();

                    intent.putExtra("userName", userName);
                    intent.putExtra("userPassword", userPassword);
                    //回传用户名
                    setResult(RESULT_OK,intent);
                    //销毁当前方法
                    finish();
                } else {
                    //注册失败
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}