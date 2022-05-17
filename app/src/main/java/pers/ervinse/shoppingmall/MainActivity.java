package pers.ervinse.shoppingmall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    //底部按钮组
    private RadioGroup bottom_btn_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置底部按钮中的'首页'在打开应用时被选中
        bottom_btn_group = findViewById(R.id.bottom_btn_group);
        bottom_btn_group.check(R.id.home_bottom_btn);
    }
}