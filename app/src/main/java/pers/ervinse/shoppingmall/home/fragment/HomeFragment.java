package pers.ervinse.shoppingmall.home.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pers.ervinse.shoppingmall.BaseFragment;
import pers.ervinse.shoppingmall.R;
import pers.ervinse.shoppingmall.home.adapter.HomeAdapter;
import pers.ervinse.shoppingmall.domain.Goods;


public class HomeFragment extends BaseFragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private RecyclerView rvHome;
    private ImageView ib_top;
    private TextView tv_search_home;
    private TextView tv_message_home;

    private RecyclerView.Adapter adapter;

    List<Goods> goodsList;

    /**
     * 初始化视图
     * @return
     */
    @Override
    public View initView() {
        Log.d(TAG, "主页视图初始化");
        //为当前fragment加载布局文件
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        rvHome = view.findViewById(R.id.rv_home);
        tv_search_home = view.findViewById(R.id.tv_search_home);
        tv_message_home = view.findViewById(R.id.tv_message_home);
        return view;
    }

    public void initData() {
        super.initData();
        Log.d(TAG, "主页数据初始化");

        //TODO 模拟数据
        //加载数据
        goodsList = new ArrayList<Goods>();
        for (int i = 1;i < 20;i++){
            Goods goods = new Goods();
            goods.setName("商品" + String.valueOf(i));
            goods.setDescription("这是商品" + String.valueOf(i) + "的描述");
            goods.setPrice(999.99);
            goodsList.add(goods);
        }

        //创建首页循环视图适配器,加载数据
        adapter = new HomeAdapter(goodsList, mContext);
        //循环视图加载适配器
        rvHome.setAdapter(adapter);
        //创建网格布局
        GridLayoutManager manager =  new GridLayoutManager(mContext,2);
        //循环视图加载网格布局
        rvHome.setLayoutManager(manager);
    }
}
