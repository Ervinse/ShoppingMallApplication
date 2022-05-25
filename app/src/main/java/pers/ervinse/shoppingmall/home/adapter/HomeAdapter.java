package pers.ervinse.shoppingmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pers.ervinse.shoppingmall.GoodsInfoActivity;
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
        holder.item_description_tv.setText(goodsList.get(position).getDescription());
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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
                    Goods goodsByClick = goodsList.get(getLayoutPosition());
                    Intent intent = new Intent(context, GoodsInfoActivity.class);
                    intent.putExtra("goods", goodsByClick);
                    context.startActivity(intent);
                }
            });
        }
    }
}
