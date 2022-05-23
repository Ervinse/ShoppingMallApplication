package pers.ervinse.shoppingmall.type.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pers.ervinse.shoppingmall.GoodsInfoActivity;
import pers.ervinse.shoppingmall.R;
import pers.ervinse.shoppingmall.domain.Goods;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.TypeViewHolder>{

    //商品数据
    private List<Goods> goodsList;
    //上下文
    private Context context;


    /**
     * 创建适配器时传入要加载的商品数据和上下文
     */
    public TypeAdapter(List<Goods> goodsList, Context context) {
        this.goodsList = goodsList;
        this.context = context;
    }

    @NonNull
    @Override
    public TypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_type, null);
        return new TypeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeViewHolder holder, int position) {

        holder.item_name_tv.setText(goodsList.get(position).getName());
        holder.item_description_tv.setText(goodsList.get(position).getDescribe());
        holder.item_price_tv.setText(String.valueOf(goodsList.get(position).getPrice()));
        //TODO 绑定商品图片
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    public class TypeViewHolder extends RecyclerView.ViewHolder {

        private TextView item_name_tv,item_description_tv,item_price_tv;
        private ImageView item_image;

        public TypeViewHolder(@NonNull View itemView) {
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
                        goods.setDescribe("这是商品" + String.valueOf(i) + "的描述");
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
