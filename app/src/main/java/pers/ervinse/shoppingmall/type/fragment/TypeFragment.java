package pers.ervinse.shoppingmall.type.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pers.ervinse.shoppingmall.BaseFragment;
import pers.ervinse.shoppingmall.R;
import pers.ervinse.shoppingmall.domain.Goods;
import pers.ervinse.shoppingmall.home.adapter.HomeAdapter;
import pers.ervinse.shoppingmall.type.adapter.TypeAdapter;

public class TypeFragment extends BaseFragment {

    private static final String TAG = TypeFragment.class.getSimpleName();

    private RecyclerView rv_type;
    private RecyclerView.Adapter adapter;

    List<Goods> goodsList;

    @Override
    public View initView() {
        Log.i(TAG, "类别视图初始化");
        //为当前fragment加载布局文件
        View view = View.inflate(mContext, R.layout.fragment_type, null);
        rv_type = view.findViewById(R.id.rv_type);
        return view;
    }

    public void initData() {
        super.initData();
        Log.i(TAG, "类别数据初始化");


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

        //创建类别循环视图适配器,加载数据
        adapter = new TypeAdapter(goodsList, mContext);
        //循环视图加载适配器
        rv_type.setAdapter(adapter);
        //创建网格布局
        GridLayoutManager manager =  new GridLayoutManager(mContext,1);
        //循环视图加载网格布局
        rv_type.setLayoutManager(manager);
    }
}
