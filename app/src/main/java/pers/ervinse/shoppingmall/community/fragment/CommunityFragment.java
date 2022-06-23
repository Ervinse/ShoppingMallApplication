package pers.ervinse.shoppingmall.community.fragment;

import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import pers.ervinse.shoppingmall.BaseFragment;
import pers.ervinse.shoppingmall.R;
import pers.ervinse.shoppingmall.community.Adapter.CommunityPagerAdapter;

public class CommunityFragment extends BaseFragment {

    private static final String TAG = CommunityFragment.class.getSimpleName();

    private ViewPager view_pager;

    @Override
    public View initView() {
        Log.i(TAG, "社区视图被初始化");
        View view = View.inflate(mContext, R.layout.fragment_community, null);
        view_pager = view.findViewById(R.id.view_pager);
        return view;
    }

    public void initData() {
        super.initData();
        Log.i(TAG, "社区数据被初始化");


        //加载图片id数据
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.drawable.carousel_1);
        list.add(R.drawable.carousel_2);
        list.add(R.drawable.carousel_3);
        list.add(R.drawable.carousel_4);


        //加载标题数据
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("猕猴桃");
        titleList.add("新奇士柠檬");
        titleList.add("自然好茶");
        titleList.add("端午情");

        //加载适配器
        view_pager.setAdapter(new CommunityPagerAdapter(mContext,list,titleList));
    }

    @Override
    public void refreshData() {
    }

    @Override
    public void saveData() {

    }
}
