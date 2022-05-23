package pers.ervinse.shoppingmall.community.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import pers.ervinse.shoppingmall.R;

/**
 * 社区适配器
 */
public class CommunityPagerAdapter extends PagerAdapter {

    private Context mContext;
    //存有图片id的集合
    private List<Integer> imageList;
    //存有标题的集合
    private List<String> titleList;

    /**
     * 创建适配器
     * @param context 上下文
     * @param imageList 存有图片id的集合
     * @param titleList 存有标题的集合
     */
    public CommunityPagerAdapter(Context context , List<Integer> imageList,List<String> titleList) {
        this.mContext = context;
        this.imageList = imageList;
        this.titleList = titleList;
    }

    /**
     * 获取创建ViewPager个数
     * @return
     */
    @Override
    public int getCount() {
        return imageList.size();
    }

    /**
     * 加载每一个ViewPager的布局,并绑定数据
     * @param container 包含ViewPager的容器
     * @param position 当前ViewPager的个数
     * @return ViewPager对象
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //加载每一个ViewPager的布局
        View view = View.inflate(mContext, R.layout.page_community,null);
        //获取图片控件,并从图片集合中取出对应的图片id,加载到控件中
        ImageView image =  view.findViewById(R.id.image);
        image.setImageResource(imageList.get(position));
        //将当前ViewPager对象加入容器中
        container.addView(view);
        //返回ViewPager对象
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container,position,object); 这一句要删除，否则报错
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 获取ViewPager的标题
     * @param position ViewPager的个数
     * @return 标题字符串
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
