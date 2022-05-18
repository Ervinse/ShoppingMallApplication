package pers.ervinse.shoppingmall.shoppingcart.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import pers.ervinse.shoppingmall.BaseFragment;
import pers.ervinse.shoppingmall.community.fragment.CommunityFragment;

public class ShoppingCartFragment extends BaseFragment {

    private static final String TAG = ShoppingCartFragment.class.getSimpleName();
    private TextView textView;

    @Override
    public View initView() {
        Log.e(TAG, "购物车视图被初始化了");
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.RED);
        return textView;
    }

    public void initData() {
        super.initData();
        Log.e(TAG, "购物车数据被初始化了");
        textView.setText("购物车");
    }
}
