package pers.ervinse.shoppingmall;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 基类Fragemnt
 */
public abstract class BaseFragment extends Fragment {

    protected Context mContext;

    /**
     * 当该类被系统创建的时候被回调
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }


    /**
     * 子类初始化组件时调用
     * @return
     */
    public abstract View initView() ;

    /**
     * 当Activity被创建了的时候回调这个方法
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 初始化相关数据
     * 当子类需要联网请求数据的时候，在该方法中联网请求
     */
    public void initData() {

    }

    public abstract void refreshData() ;
}
