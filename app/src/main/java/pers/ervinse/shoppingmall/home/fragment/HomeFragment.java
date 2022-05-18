package pers.ervinse.shoppingmall.home.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import pers.ervinse.shoppingmall.BaseFragment;
import pers.ervinse.shoppingmall.R;


public class HomeFragment extends BaseFragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private RecyclerView rvHome;
    private ImageView ib_top;
    private TextView tv_search_home;
    private TextView tv_message_home;

    @Override
    public View initView() {
        Log.e(TAG, "主页视图被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        rvHome = view.findViewById(R.id.rv_home);
        tv_search_home = view.findViewById(R.id.tv_search_home);
        tv_message_home = view.findViewById(R.id.tv_message_home);
        return view;
    }

    public void initData() {
        super.initData();
        Log.e(TAG, "主页数据被初始化了");
    }
}
