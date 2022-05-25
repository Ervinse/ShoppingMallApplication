package pers.ervinse.shoppingmall.shoppingcart.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pers.ervinse.shoppingmall.BaseFragment;
import pers.ervinse.shoppingmall.R;
import pers.ervinse.shoppingmall.community.fragment.CommunityFragment;
import pers.ervinse.shoppingmall.domain.Goods;
import pers.ervinse.shoppingmall.home.adapter.HomeAdapter;
import pers.ervinse.shoppingmall.shoppingcart.adapter.ShoppingCartAdapter;

public class ShoppingCartFragment extends BaseFragment {

    private static final String TAG = ShoppingCartFragment.class.getSimpleName();
    private TextView cart_total_tv;
    private CheckBox cart_check_all_checkbox,cart_delete_all_checkbox;
    RecyclerView cart_item_rv;
    List<Goods> goodsList;

    ShoppingCartAdapter adapter;

    @Override
    public View initView() {
        Log.d(TAG, "购物车视图被初始化了");

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
        Log.d(TAG, "购物车数据被初始化了");

        //TODO 模拟数据
        //加载数据
        goodsList = new ArrayList<Goods>();
        for (int i = 1;i < 20;i++){
            Goods goods = new Goods();
            goods.setName("商品" + String.valueOf(i));
            goods.setDescription("这是商品" + String.valueOf(i) + "的描述");
            goods.setPrice(999.99);
            goods.setNumber(1);
            goodsList.add(goods);
        }

        //创建首页循环视图适配器,加载数据
        adapter = new ShoppingCartAdapter(mContext,goodsList,cart_total_tv,cart_check_all_checkbox,cart_delete_all_checkbox);
        //循环视图加载适配器
        cart_item_rv.setAdapter(adapter);
        //创建网格布局
        GridLayoutManager manager =  new GridLayoutManager(mContext,1);
        //循环视图加载网格布局
        cart_item_rv.setLayoutManager(manager);
    }
}
