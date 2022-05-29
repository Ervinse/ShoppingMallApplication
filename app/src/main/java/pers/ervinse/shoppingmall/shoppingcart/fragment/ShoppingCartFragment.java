package pers.ervinse.shoppingmall.shoppingcart.fragment;

import static pers.ervinse.shoppingmall.R.id.cart_settle_btn;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import pers.ervinse.shoppingmall.BaseFragment;
import pers.ervinse.shoppingmall.R;
import pers.ervinse.shoppingmall.domain.Goods;
import pers.ervinse.shoppingmall.shoppingcart.adapter.ShoppingCartAdapter;
import pers.ervinse.shoppingmall.utils.OkhttpUtils;
import pers.ervinse.shoppingmall.utils.PropertiesUtils;

/**
 * 购物车碎片
 */
public class ShoppingCartFragment extends BaseFragment {

    private static final String TAG = ShoppingCartFragment.class.getSimpleName();
    //线程处理器
    private Handler handler = new Handler();

    //总价
    private TextView cart_total_tv;
    //全选,全删
    private CheckBox cart_check_all_checkbox,cart_delete_all_checkbox;

    private Button cart_settle_btn;

    RecyclerView cart_item_rv;
    List<Goods> goodsList;

    ShoppingCartAdapter adapter;

    /**
     * 初始化视图
     * @return
     */
    @Override
    public View initView() {
        Log.i(TAG, "购物车视图被初始化了");

        View view = View.inflate(mContext, R.layout.fragment_shopping_cart, null);
        cart_total_tv = view.findViewById(R.id.cart_total_tv);
        cart_check_all_checkbox = view.findViewById(R.id.cart_check_all_checkbox);
        cart_delete_all_checkbox = view.findViewById(R.id.cart_delete_all_checkbox);
        View viewById = view.findViewById(R.id.cart_item_price_tv);
        cart_item_rv = view.findViewById(R.id.cart_item_rv);

        cart_settle_btn = view.findViewById(R.id.cart_settle_btn);

        cart_settle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String totalPrice = decimalFormat.format(adapter.getTotalPrice());
                if (!totalPrice.equals(".00")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                            .setTitle("完成结算")
                            .setMessage("商品总计:" + totalPrice + "元,是否完成结算?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(mContext, "完成商品购买,总计价格为:" + totalPrice + "元", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    //创建删除对话框并显示
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

        return view;
    }

    /**
     * 初始化数据
     */
    public void initData() {
        super.initData();
        Log.i(TAG, "购物车数据被初始化了");

        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "进入获取购物车商品线程");

                Gson gson = new Gson();
                String responseJson = null;
                try {
                    //发送登录请求
                    String url = PropertiesUtils.getUrl(mContext);
                    responseJson = OkhttpUtils.doGet(url + "/cart");
                    Log.i(TAG, "获取购物车商品响应json:" + responseJson);
                    goodsList = gson.fromJson(responseJson, new TypeToken<List<Goods>>() {
                    }.getType());
                    Log.i(TAG, "获取购物车商品响应解析对象:" + goodsList);

                    //获取数据成功,加创建商品布局
                    if (goodsList != null) {
                        //切回主线程加载视图
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


    /**
     * 刷新数据
     */
    @Override
    public void refreshData() {

        Log.i(TAG, "联网刷新数据");

        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "进入获取购物车商品线程");


                Gson gson = new Gson();
                String responseJson = null;
                try {
                    //发送登录请求
                    String url = PropertiesUtils.getUrl(mContext);
                    responseJson = OkhttpUtils.doGet(url + "/cart");
                    Log.i(TAG, "获取购物车商品响应json:" + responseJson);
                    goodsList = gson.fromJson(responseJson, new TypeToken<List<Goods>>() {
                    }.getType());
                    Log.i(TAG, "获取购物车商品响应解析对象:" + goodsList);

                    //数据获取成功,加创建商品布局
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

    /**
     * 将当前商品数据保存到服务器中
     */
    @Override
    public void saveData() {
        Log.i(TAG, "保存数据");

        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "进入保存购物车数据线程");

                Gson gson = new Gson();
                String goodsListJson = gson.toJson(goodsList);
                Log.i(TAG, "保存购物车数据响应json:" + goodsListJson);
                String responseJson = null;
                try {
                    //发送获取购物车商品请求
                    String url = PropertiesUtils.getUrl(mContext);
                    responseJson = OkhttpUtils.doPost(url + "/cart/updateGoodsInfo",goodsListJson);
                    Log.i(TAG, "保存购物车数据响应json:" + responseJson);
                    responseJson = gson.fromJson(responseJson,String.class);
                    Log.i(TAG, "保存购物车数据响应解析对象:" + responseJson);

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
