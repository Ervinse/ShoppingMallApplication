package pers.ervinse.shoppingmall.shoppingcart.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pers.ervinse.shoppingmall.BaseFragment;
import pers.ervinse.shoppingmall.R;
import pers.ervinse.shoppingmall.community.fragment.CommunityFragment;
import pers.ervinse.shoppingmall.domain.Goods;
import pers.ervinse.shoppingmall.home.adapter.HomeAdapter;
import pers.ervinse.shoppingmall.shoppingcart.adapter.ShoppingCartAdapter;
import pers.ervinse.shoppingmall.type.adapter.TypeAdapter;
import pers.ervinse.shoppingmall.utils.OkhttpUtils;

public class ShoppingCartFragment extends BaseFragment {

    private static final String TAG = ShoppingCartFragment.class.getSimpleName();
    private Handler handler = new Handler();

    private TextView cart_total_tv;
    private CheckBox cart_check_all_checkbox,cart_delete_all_checkbox;
    RecyclerView cart_item_rv;
    List<Goods> goodsList;

    ShoppingCartAdapter adapter;

    @Override
    public View initView() {
        Log.i(TAG, "购物车视图被初始化了");

        View view = View.inflate(mContext, R.layout.fragment_shopping_cart, null);
        cart_total_tv = view.findViewById(R.id.cart_total_tv);
        cart_check_all_checkbox = view.findViewById(R.id.cart_check_all_checkbox);
        cart_delete_all_checkbox = view.findViewById(R.id.cart_delete_all_checkbox);
        View viewById = view.findViewById(R.id.cart_item_price_tv);
        cart_item_rv = view.findViewById(R.id.cart_item_rv);
        return view;
    }

    public void initData() {
        super.initData();
        Log.i(TAG, "购物车数据被初始化了");

        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "进入获取商品线程");


                Gson gson = new Gson();
                String responseJson = null;
                try {
                    //发送登录请求
                    responseJson = OkhttpUtils.doGet("http://192.168.1.8:8088/cart");
                    Log.i(TAG, "获取购物车商品响应json:" + responseJson);
                    goodsList = gson.fromJson(responseJson, new TypeToken<List<Goods>>() {
                    }.getType());
                    Log.i(TAG, "获取购物车商品响应解析对象:" + goodsList);

                    //加创建商品布局
                    if (goodsList != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //创建首页循环视图适配器,加载数据
                                adapter = new ShoppingCartAdapter(mContext,goodsList,cart_total_tv,cart_check_all_checkbox,cart_delete_all_checkbox);
                                //循环视图加载适配器
                                cart_item_rv.setAdapter(adapter);
                                //创建网格布局
                                GridLayoutManager manager =  new GridLayoutManager(mContext,1);
                                //循环视图加载网格布局
                                cart_item_rv.setLayoutManager(manager);
                            }
                        });
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


    @Override
    public void refreshData() {

        Log.i(TAG, "联网刷新数据");

        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "进入获取商品线程");


                Gson gson = new Gson();
                String responseJson = null;
                try {
                    //发送登录请求
                    responseJson = OkhttpUtils.doGet("http://192.168.1.8:8088/cart");
                    Log.i(TAG, "获取购物车商品响应json:" + responseJson);
                    goodsList = gson.fromJson(responseJson, new TypeToken<List<Goods>>() {
                    }.getType());
                    Log.i(TAG, "获取购物车商品响应解析对象:" + goodsList);

                    //加创建商品布局
                    if (goodsList != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setGoodsList(goodsList);
                                adapter.flushView();
                            }
                        });
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
}
