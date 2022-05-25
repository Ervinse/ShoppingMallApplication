package pers.ervinse.shoppingmall;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import pers.ervinse.shoppingmall.domain.Goods;

public class GoodsInfoActivity extends Activity {


    private ImageView good_info_back_btn;
    private TextView goods_name_tv,goods_price_tv,goods_description_tv,goods_location_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);

        good_info_back_btn = findViewById(R.id.good_info_back_btn);

        goods_name_tv = findViewById(R.id.goods_name_tv);
        goods_price_tv = findViewById(R.id.goods_price_tv);
        goods_description_tv = findViewById(R.id.goods_description_tv);
        goods_location_tv = findViewById(R.id.goods_location_tv);

        good_info_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //加载商品数据
        Intent intent = getIntent();
        Goods goods = (Goods)intent.getSerializableExtra("goods");

        goods_name_tv.setText(goods.getName());
        goods_price_tv.setText(String.valueOf(goods.getPrice()));
        goods_description_tv.setText(goods.getDescription());
        goods_location_tv.setText(goods.getLocation());

    }
}