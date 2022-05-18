package pers.ervinse.shoppingmall.community.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import pers.ervinse.shoppingmall.BaseFragment;
import pers.ervinse.shoppingmall.home.fragment.HomeFragment;

public class CommunityFragment extends BaseFragment {

    private static final String TAG = CommunityFragment.class.getSimpleName();
    private TextView textView;

    @Override
    public View initView() {
        Log.e(TAG, "社区视图被初始化了");
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.RED);
        return textView;
    }

    public void initData() {
        super.initData();
        Log.e(TAG, "社区数据被初始化了");
        textView.setText("社区");
    }
}
