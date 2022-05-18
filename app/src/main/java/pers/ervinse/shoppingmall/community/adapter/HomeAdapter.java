package pers.ervinse.shoppingmall.community.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pers.ervinse.shoppingmall.R;
import pers.ervinse.shoppingmall.domain.Goods;

/**
 * 首页循环视图适配器
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    //商品数据
    private List<Goods> goodsList;
    //上下文
    private Context context;

    /**
     * 创建适配器时传入要加载的商品数据和上下文
     */
    public HomeAdapter(List<Goods> goodsList, Context context) {
        this.goodsList = goodsList;
        this.context = context;
    }

    /**
     * 创建ViewHolder时调用此方法,加载每一项子数据的布局文件
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.home_recycler_item, null);
        return new HomeViewHolder(itemView);
    }

    /**
     * 将ViewHolder中的组件和数据绑定
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.item_name_tv.setText(goodsList.get(position).getName());
        holder.item_description_tv.setText(goodsList.get(position).getDescribe());
        holder.item_price_tv.setText(String.valueOf(goodsList.get(position).getPrice()));
        //TODO 绑定商品图片
    }

    /**
     * 获取数据的条数
     * @return
     */
    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    /**
     * 内部类,加载子项布局
     */
    public class HomeViewHolder extends RecyclerView.ViewHolder {
        private TextView item_name_tv,item_description_tv,item_price_tv;
        private ImageView item_image;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name_tv = itemView.findViewById(R.id.item_name);
            item_description_tv = itemView.findViewById(R.id.item_description);
            item_price_tv = itemView.findViewById(R.id.item_price);
            item_image = itemView.findViewById(R.id.item_image);
        }
    }
}