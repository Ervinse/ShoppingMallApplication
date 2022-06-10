package pers.ervinse.shoppingmall;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.IOException;

import pers.ervinse.shoppingmall.domain.Goods;
import pers.ervinse.shoppingmall.utils.OkhttpUtils;
import pers.ervinse.shoppingmall.utils.PropertiesUtils;

/**
 * 商品详情页面
 */
public class GoodsInfoActivity extends Activity {

    private static final String TAG = GoodsInfoActivity.class.getSimpleName();
    private Goods goods;
    private Context mContext;

    //返回按钮,商品图片
    private ImageView good_info_back_btn,goods_image;
    private TextView goods_name_tv,goods_price_tv,goods_description_tv,goods_location_tv;
    //添加到购物车按钮
    private Button good_info_add_cart_btn;

    /**
     * 创建视图
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        mContext = this;

        good_info_back_btn = findViewById(R.id.good_info_back_btn);
        goods_name_tv = findViewById(R.id.goods_name_tv);
        goods_price_tv = findViewById(R.id.goods_price_tv);
        goods_description_tv = findViewById(R.id.goods_description_tv);
        goods_location_tv = findViewById(R.id.goods_location_tv);
        good_info_add_cart_btn = findViewById(R.id.good_info_add_cart_btn);
        goods_image = findViewById(R.id.goods_image);


        good_info_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        good_info_add_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        Log.i(TAG, "进入获取商品线程");

                        Gson gson = new Gson();
                        String responseJson = null;

                        //获取当前商品信息
                        Goods goodsForAdd = new Goods();
                        goodsForAdd.setName(goods.getName());
                        String goodsJson = gson.toJson(goodsForAdd);
                        try {
                            //发送添加到购物车请求
                            String url = PropertiesUtils.getUrl(mContext);
                            responseJson = OkhttpUtils.doPost(url + "/cart/addGoodsToCart",goodsJson);
                            Log.i(TAG, "添加购物车商品响应json:" + responseJson);
                            responseJson = gson.fromJson(responseJson, String.class);
                            Log.i(TAG, "添加购物车商品响应解析对象:" + responseJson);

                            if (responseJson != null) {
                                //添加购物车成功
                                if (responseJson.equals("true")){
                                    Looper.prepare();
                                    Toast.makeText(mContext, "商品已添加到购物车", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                //添加购物车失败,商品已经在购物车
                                }else {
                                    Looper.prepare();
                                    Toast.makeText(mContext, "商品已经在购物车啦", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            Looper.prepare();
                            Toast.makeText(mContext, "获取数据失败,服务器错误", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }

                    }
                }.start();
            }
        });
    }

    /**
     * Activity创建时调用
     * 根据intent中携带的商品信息创建视图数据
     */
    @Override
    protected void onStart() {
        super.onStart();

        //加载商品数据
        Intent intent = getIntent();
        goods = (Goods)intent.getSerializableExtra("goods");

        goods_name_tv.setText(goods.getName());
        goods_price_tv.setText(String.valueOf(goods.getPrice()));
        goods_description_tv.setText(goods.getDescription());
        goods_location_tv.setText(goods.getLocation());

        int id = mContext.getResources().getIdentifier(goods.getImage(), "drawable", mContext.getPackageName());
        goods_image.setImageResource(id);

    }
}