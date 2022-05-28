package pers.ervinse.shoppingmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import pers.ervinse.shoppingmall.GoodsInfoActivity;
import pers.ervinse.shoppingmall.R;
import pers.ervinse.shoppingmall.domain.Goods;
import pers.ervinse.shoppingmall.utils.OkhttpUtils;

/**
 * 首页循环视图适配器
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private static final String TAG = HomeAdapter.class.getSimpleName();

    //商品数据
    private List<Goods> goodsList;
    //上下文
    private Context mContext;


    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    /**
     * 刷新所有数据
     */
    public void flushView(){
        notifyDataSetChanged();
    }


    /**
     * 创建适配器时传入要加载的商品数据和上下文
     */
    public HomeAdapter(List<Goods> goodsList, Context context) {
        this.goodsList = goodsList;
        this.mContext = context;
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
        View itemView = View.inflate(mContext, R.layout.home_recycler_item, null);
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
        private Button buy_button;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name_tv = itemView.findViewById(R.id.item_name);
            item_description_tv = itemView.findViewById(R.id.item_description);
            item_price_tv = itemView.findViewById(R.id.item_price);
            item_image = itemView.findViewById(R.id.item_image);
            buy_button = itemView.findViewById(R.id.buy_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //加载数据
                    Goods goodsByClick = goodsList.get(getLayoutPosition());
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra("goods", goodsByClick);
                    mContext.startActivity(intent);
                }
            });

            buy_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Thread() {
                        @Override
                        public void run() {
                            Log.i(TAG, "进入添加购物车商品线程");



                            Gson gson = new Gson();
                            String responseJson = null;

                            Goods goodsByClick = goodsList.get(getLayoutPosition());
                            Goods goodsForAdd = new Goods();
                            goodsForAdd.setName(goodsByClick.getName());
                            String goodsJson = gson.toJson(goodsForAdd);
                            try {
                                //发送登录请求
                                responseJson = OkhttpUtils.doPost("http://192.168.1.8:8088/cart/addGoodsToCart",goodsJson);
                                Log.i(TAG, "添加购物车商品响应json:" + responseJson);
                                responseJson = gson.fromJson(responseJson, String.class);
                                Log.i(TAG, "添加购物车商品响应解析对象:" + responseJson);

                                //加创建商品布局
                                if (responseJson != null) {
                                    if (responseJson.equals("true")){
                                        Looper.prepare();
                                        Toast.makeText(mContext, "商品已添加到购物车", Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }else {
                                        Looper.prepare();
                                        Toast.makeText(mContext, "商品已经在购物车啦", Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }

                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                                Looper.prepare();
                                Toast.makeText(mContext, "获取数据失败,服务器错误", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }

                        }
                    }.start();
                }
            });
        }
    }
}
