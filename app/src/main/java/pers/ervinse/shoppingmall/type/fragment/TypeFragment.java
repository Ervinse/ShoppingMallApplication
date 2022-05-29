package pers.ervinse.shoppingmall.type.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import pers.ervinse.shoppingmall.BaseFragment;
import pers.ervinse.shoppingmall.R;
import pers.ervinse.shoppingmall.domain.Goods;
import pers.ervinse.shoppingmall.type.adapter.TypeAdapter;
import pers.ervinse.shoppingmall.utils.OkhttpUtils;
import pers.ervinse.shoppingmall.utils.PropertiesUtils;

/**
 * 全部商品碎片
 */
public class TypeFragment extends BaseFragment {

    private static final String TAG = TypeFragment.class.getSimpleName();
    //线程处理器
    private Handler handler = new Handler();

    private RecyclerView rv_type;
    private TypeAdapter adapter;

    List<Goods> goodsList;

    /**
     * 初始化视图
     * @return
     */
    @Override
    public View initView() {
        Log.i(TAG, "类别视图初始化");
        //为当前fragment加载布局文件
        View view = View.inflate(mContext, R.layout.fragment_type, null);
        rv_type = view.findViewById(R.id.rv_type);
        return view;
    }

    /**
     * 初始化数据
     */
    public void initData() {
        super.initData();
        Log.i(TAG, "类别数据初始化");

        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "进入获取商品线程");

                Gson gson = new Gson();
                String responseJson = null;
                try {
                    //发送获取商品请求
                    String url = PropertiesUtils.getUrl(mContext);
                    responseJson = OkhttpUtils.doGet(url + "/goods");
                    Log.i(TAG, "获取商品响应json:" + responseJson);
                    goodsList = gson.fromJson(responseJson, new TypeToken<List<Goods>>() {
                    }.getType());
                    Log.i(TAG, "获取商品响应解析对象:" + goodsList);

                    //获取商品商品成功
                    if (goodsList != null) {
                        //切回主线程加载视图
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //创建类别循环视图适配器,加载数据
                                adapter = new TypeAdapter(goodsList, mContext);
                                //循环视图加载适配器
                                rv_type.setAdapter(adapter);
                                //创建网格布局
                                GridLayoutManager manager = new GridLayoutManager(mContext, 1);
                                //循环视图加载网格布局
                                rv_type.setLayoutManager(manager);
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
                Log.i(TAG, "进入获取商品线程");

                Gson gson = new Gson();
                String responseJson = null;

                try {
                    //发送获取商品请求
                    String url = PropertiesUtils.getUrl(mContext);
                    responseJson = OkhttpUtils.doGet(url + "/goods");
                    Log.i(TAG, "获取商品响应json:" + responseJson);
                    goodsList = gson.fromJson(responseJson, new TypeToken<List<Goods>>() {
                    }.getType());
                    Log.i(TAG, "获取商品响应解析对象:" + goodsList);

                    //切回主线程更新视图
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //更新数据
                            adapter.setGoodsList(goodsList);
                            //刷新视图
                            adapter.flushView();
                        }
                    });
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
    public void saveData() {

    }

}
