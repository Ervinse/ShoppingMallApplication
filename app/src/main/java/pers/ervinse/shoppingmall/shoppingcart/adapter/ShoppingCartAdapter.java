package pers.ervinse.shoppingmall.shoppingcart.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import pers.ervinse.shoppingmall.R;
import pers.ervinse.shoppingmall.domain.Goods;
import pers.ervinse.shoppingmall.utils.OkhttpUtils;
import pers.ervinse.shoppingmall.utils.PropertiesUtils;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private static final String TAG = "ShoppingCartAdapter";
    //上下文
    private final Context mContext;
    //线程处理器
    private Handler handler = new Handler();
    //数据集合
    private List<Goods> goodsList;
    //总价TextView
    private final TextView cart_total_tv;
    //全选,全删CheckBox
    private final CheckBox cart_check_all_checkbox,cart_delete_all_checkbox;
    //每一个item的点击监听器
    private AdapterView.OnItemClickListener onItemClickListener;

    View itemView;

    /**
     * 供外部使用,更新goodsList数据
     * @param goodsList
     */
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
     * 创建购物车适配器
     * 获取上下文,货物商品数据,页面布局组件
     * 设置事件监听器
     */
    public  ShoppingCartAdapter(Context mContext, List<Goods> goodsList, TextView cart_total_tv, CheckBox cart_check_all_checkbox, CheckBox cart_delete_all_checkbox) {
        this.mContext = mContext;
        this.goodsList = goodsList;
        //商品总价TextView
        this.cart_total_tv = cart_total_tv;
        //全选
        this.cart_check_all_checkbox = cart_check_all_checkbox;
        //全部删除
        this.cart_delete_all_checkbox = cart_delete_all_checkbox;
        showTotalPrice();
        //设置事件监听器
        setListener();
        //校验是否全选
        checkAll();
    }

    /**
     * 加载item的点击事件监听器
     * @param onItemClickListener
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置各个事件监听器
     */
    private void setListener() {

        //加载item的点击事件监听器(RecyclerView内部的item事件)
        setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             *
             * @param adapterView 被点击的Adapter对象
             * @param item 被点击的Item（可用于获取该item内的组件）
             * @param position 被点击的是第几个item(从0开始，0算第一个，类似数组)
             * @param id 当前点击的item在listview 里的第几行的位置，通常id与position的值相同
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long id) {

                Log.i(TAG, "购物车第" + position + "项数据点击事件");
                //1.根据位置找到对应的Goods对象
                Goods goods = goodsList.get(position);
                //2.设置取反状态
                goods.setSelected(!goods.getSelected());
                System.out.println(goods);
                //3.刷新状态
                notifyItemChanged(position);
                //4.校验是否全选
                checkAll();
                //5.重新计算总价格
                showTotalPrice();
            }

        });

        //全选CheckBox的点击事件
        cart_check_all_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                boolean isCheck = cart_check_all_checkbox.isChecked();
                //2.根据状态设置全选和非全选
                checkAll_none(isCheck);
                //3.计算总价格
                showTotalPrice();

            }
        });

        //全删CheckBox的点击事件
        cart_delete_all_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //1.得到状态
                boolean isCheck = cart_delete_all_checkbox.isChecked();
                //2.根据状态设置全选和非全选
                checkAll_none(isCheck);

            }
        });

    }


    /**
     * 校验是否全选
     */
    public void checkAll() {
        if(goodsList != null && goodsList.size() >0){
            int number = 0;
            for (int i=0;i<goodsList.size();i++){
                Goods goods = goodsList.get(i);
                if(!goods.isSelected){
                    //非全选
                    cart_check_all_checkbox.setChecked(false);
                    cart_delete_all_checkbox.setChecked(false);
                }else{
                    //选中的
                    number ++;
                }
            }

            if(number == goodsList.size()){
                //全选
                cart_check_all_checkbox.setChecked(true);
                cart_delete_all_checkbox.setChecked(true);
            }
        }else{
            cart_check_all_checkbox.setChecked(false);
            cart_delete_all_checkbox.setChecked(false);
        }
    }

    /**
     * 根据状态设置全选和非全选
     * @param isCheck
     */
    public void checkAll_none(boolean isCheck) {
        if(goodsList != null && goodsList.size() >0){
            for (int i=0;i<goodsList.size();i++){
                Goods goods = goodsList.get(i);
                goods.setSelected(isCheck);
                notifyItemChanged(i);
            }
        }
    }

    /**
     * 根据当前已选的商品单价和数量获取总价,并将总价显示在对应的TextView上
     */
    private void showTotalPrice() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String totalPrice = decimalFormat.format(getTotalPrice());
        if (totalPrice.equals(".00")){
            totalPrice = "0.00";
        }
        cart_total_tv.setText("￥" + totalPrice);
    }

    /**
     * 根据当前已选的商品单价和数量获取总价
     * @return 总价
     */
    public double getTotalPrice() {
        double totalPrice = 0.0;
        if (goodsList != null && goodsList.size() > 0) {

            for (int i = 0; i < goodsList.size(); i++) {
                Goods goods = goodsList.get(i);
                if(goods.isSelected){

                    totalPrice = totalPrice + Double.valueOf(goods.getNumber()) * Double.valueOf(goods.getPrice());
                }
            }
        }
        return totalPrice;
    }


    /**
     * 创建适配器
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ShoppingCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = View.inflate(mContext, R.layout.item_shop_cart, null);
        return new ViewHolder(itemView);
    }

    /**
     * 将RecyclerView中每一项item的组件个数据绑定在一起
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ShoppingCartAdapter.ViewHolder holder, int position) {
        Goods goods = goodsList.get(position);
        holder.cart_item_description_tv.setText(goods.getName() + "  " + goods.getDescription());
        holder.cart_item_price_tv.setText("￥" + String.valueOf(goodsList.get(position).getPrice()));
        holder.cart_item_check_checkbox.setChecked(goodsList.get(position).getSelected());
        holder.cart_item_value_tv.setText("" + goodsList.get(position).getNumber());
        //通过图片名字获取图片资源的id
        int id = mContext.getResources().getIdentifier(goodsList.get(position).getImage(), "drawable", mContext.getPackageName());
        holder.cart_item_image.setImageResource(id);
    }

    /**
     * 获取一共要加载item的个数
     * @return
     */
    @Override
    public int getItemCount() {
        return goodsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cart_item_check_checkbox;
        //商品图片
        private ImageView cart_item_image;
        //商品描述,单价,数量
        private TextView cart_item_description_tv, cart_item_price_tv,cart_item_value_tv;
        //数量加减按钮
        private ImageView cart_item_sub_btn, cart_item_add_btn;
        //删除商品按钮
        private Button cart_item_delete_btn;

        //商品最大数量和最小数量
        private int MIN_NUM = 1,MAX_NUM = 99;

        /**
         * 加载item布局文件
         * @param itemView 整个item对象
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cart_item_check_checkbox = itemView.findViewById(R.id.cart_item_check_checkbox);
            cart_item_image = itemView.findViewById(R.id.cart_item_image);
            cart_item_description_tv = itemView.findViewById(R.id.cart_item_description_tv);
            cart_item_price_tv = itemView.findViewById(R.id.cart_item_price_tv);
            cart_item_sub_btn = itemView.findViewById(R.id.cart_item_sub_btn);
            cart_item_add_btn = itemView.findViewById(R.id.cart_item_add_btn);
            cart_item_value_tv = itemView.findViewById(R.id.cart_item_value_tv);
            cart_item_delete_btn = itemView.findViewById(R.id.cart_item_delete_btn);

            //减少商品数量按钮监听事件
            cart_item_sub_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, "减少商品数量按钮监听方法");
                    //从数据集合中获取对应的商品
                    Goods goods = goodsList.get(getLayoutPosition());
                    //获取该商品减少之前的数量
                    int number = goods.getNumber();
                    //调用subGoodsNum()方法,根据规则修改数据,返回修改之后的数据
                    int numberBySub = subGoodsNum(number);
                    //将修改之后的数据记录该商品中
                    goods.setNumber(numberBySub);
                    //页面上显示修改后的数量
                    cart_item_value_tv.setText( numberBySub + "");
                    //根据数量重新计算总价,并刷新总价页面
                    showTotalPrice();
                }
            });

            //增加商品数量按钮监听事件
            cart_item_add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, "增加商品数量按钮监听方法");
                    //从数据集合中获取对应的商品
                    Goods goods = goodsList.get(getLayoutPosition());
                    //获取该商品增加之前的数量
                    int number = goods.getNumber();
                    //调用addGoodsNum()方法,根据规则修改数据,返回修改之后的数据
                    int numberByAdd = addGoodsNum(number);
                    //将修改之后的数据记录该商品中
                    goods.setNumber(numberByAdd);
                    //页面上显示修改后的数量
                    cart_item_value_tv.setText( numberByAdd + "");
                    //根据数量重新计算总价,并刷新总价页面
                    showTotalPrice();
                }
            });

            //设置商品选中checkbox的点击事件
            cart_item_check_checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(null,null,getLayoutPosition(),getLayoutPosition());
                    }
                }
            });

            //设置删除商品的点击事件
            cart_item_delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, "删除购物车商品数量按钮监听方法");
                    //设置删除按钮点击之后的弹出对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                            .setTitle("删除商品")
                            .setMessage("是否删除该商品?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    new Thread() {
                                        @Override
                                        public void run() {
                                            Log.i(TAG, "进入删除购物车商品数量线程");


                                            Gson gson = new Gson();
                                            String responseJson = null;
                                            //获取要删除的商品名
                                            Goods goodsForAdd = new Goods();
                                            goodsForAdd.setName(goodsList.get(getLayoutPosition()).getName());
                                            String goodsJson = gson.toJson(goodsForAdd);
                                            try {
                                                //发送删除请求
                                                String url = PropertiesUtils.getUrl(mContext);
                                                responseJson = OkhttpUtils.doPost(url + "/cart/deleteByName",goodsJson);
                                                Log.i(TAG, "删除购物车商品响应json:" + responseJson);
                                                responseJson = gson.fromJson(responseJson, String.class);
                                                Log.i(TAG, "删除购物车商品响应解析对象:" + responseJson);

                                                //删除成功
                                                if (responseJson != null) {
                                                    if (responseJson.equals("true")){
                                                        //切回主线程刷新视图
                                                        handler.post(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                //全局刷新
                                                                goodsList.remove(getLayoutPosition());
                                                                notifyDataSetChanged();
                                                                Toast.makeText(mContext, "商品已删除", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
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
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    //创建删除对话框并显示
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            });
        }

        /**
         * 根据规则增加商品数量
         * @param num 商品原数量
         * @return 修改之后的商品数量
         */
        private int addGoodsNum(int num){
            if (num >= MAX_NUM - 1){
                return MAX_NUM;
            }else {
                return  num + 1;
            }
        }

        /**
         * 根据规则减少商品数量
         * @param num 商品原数量
         * @return 修改之后的商品数量
         */
        private int subGoodsNum(int num){
            if (num <= MIN_NUM + 1){
                return MIN_NUM;
            }else {
                return  num - 1;
            }
        }

    }
}
