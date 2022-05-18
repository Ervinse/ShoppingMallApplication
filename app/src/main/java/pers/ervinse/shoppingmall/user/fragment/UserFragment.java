package pers.ervinse.shoppingmall.user.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import pers.ervinse.shoppingmall.BaseFragment;

public class UserFragment extends BaseFragment {

    private static final String TAG = UserFragment.class.getSimpleName();
    private TextView textView;

    @Override
    public View initView() {
        Log.e(TAG, "用户视图被初始化了");
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.RED);
        return textView;
    }

    public void initData() {
        super.initData();
        Log.e(TAG, "用户数据被初始化了");
        textView.setText("用户");
    }
}
