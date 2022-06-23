package pers.ervinse.shoppingmall.user.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import pers.ervinse.shoppingmall.BaseFragment;
import pers.ervinse.shoppingmall.LoginActivity;
import pers.ervinse.shoppingmall.R;

public class UserFragment extends BaseFragment {

    private static final String TAG = UserFragment.class.getSimpleName();
    private static final int LOGIN_REQUEST_CODE = 1;

    //当前登录状态
    private boolean isLogin = false;

    private ImageView user_photo_image;
    private TextView user_name_tv, user_desc_tv;
    private Button user_logout_btn;
    private View user_bar;

    private Bitmap bitmap;


    /**
     * 初始化视图
     * @return
     */
    @Override
    public View initView() {
        Log.i(TAG, "用户视图初始化");
        View view = View.inflate(mContext, R.layout.fragment_user, null);
        user_photo_image = view.findViewById(R.id.user_photo_image);
        user_name_tv = view.findViewById(R.id.user_name_tv);
        user_desc_tv = view.findViewById(R.id.user_desc_tv);
        user_logout_btn = view.findViewById(R.id.user_logout_btn);
        user_bar = view.findViewById(R.id.user_bar);

        //获取用户头像控件,将其设置为圆角
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_example);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        rectRoundBitmap();
        circleBitmap(bitmap, width, height);


        return view;
    }

    //初始化数据
    public void initData() {
        super.initData();
        Log.i(TAG, "用户数据初始化");

        initListener();

    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        //登录
        user_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "用户登录事件");
                if (!isLogin) {

                    Log.i(TAG, "用户未登录");
                    //前往登录页面
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, LOGIN_REQUEST_CODE);
                }
            }
        });

        //登出
        user_logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "用户登出事件");

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                        .setTitle("退出登录")
                        .setMessage("您是否要退出登录?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //关闭对话框
                                dialogInterface.dismiss();
                                //用户名恢复,简介不可见
                                isLogin = false;
                                user_name_tv.setText("请登录");
                                user_desc_tv.setVisibility(View.GONE);
                            }
                        })

                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //关闭对话框
                                dialogInterface.dismiss();
                            }
                        });

                //创建对话框,并显示
                AlertDialog logoutDialog = builder.create();
                logoutDialog.show();


            }
        });

    }

    /**
     * 数据回传
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //判断由哪一个intent发出当请求（是否是requestCode == 1）
        switch (requestCode) {
            case LOGIN_REQUEST_CODE:
                //判断返回当数据是否正常（判断是否是resultCode == RESULT_OK）
                if (resultCode == RESULT_OK) {
                    isLogin = true;
                    //获取数据并打印
                    String userName = data.getStringExtra("userName");
                    String userDesc = data.getStringExtra("userDesc");
                    Log.i(TAG, "用户登录数据回传: userName = " + userName
                                                    + ",userDesc = " + userDesc);

                    user_name_tv.setText(userName);
                    user_desc_tv.setVisibility(View.VISIBLE);
                    user_desc_tv.setText(userDesc);
                }
                break;
            default:
        }
    }


    /**
     * 为 user_photo_image 控件添加 RoundedBitmapDrawable 圆角编辑类
     */
    private void rectRoundBitmap() {
        RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        bitmapDrawable.setAntiAlias(true);
        bitmapDrawable.setCornerRadius(50);
        user_photo_image.setImageDrawable(bitmapDrawable);
    }

    /**
     * 将bitmap图片进行剪切成正方形， 然后再设置圆角半径为正方形边长的一半
     */
    private void circleBitmap(Bitmap bitmap, int width, int height) {
        Bitmap circle = null;
        int min = Math.min(width, height);
        int max = Math.max(width, height);
        if (width == height) {
            circle = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        } else {
            // 居中裁剪
            if (width > height) {
                circle = Bitmap.createBitmap(bitmap, (max - min) / 2, 0, min, min);
            } else {
                circle = Bitmap.createBitmap(bitmap, 0, (max - min) / 2, min, min);
            }
        }
        RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), circle);
        bitmapDrawable.setCornerRadius(min / 2);
        bitmapDrawable.setAntiAlias(true);
        user_photo_image.setImageDrawable(bitmapDrawable);
    }

    @Override
    public void refreshData() {
    }

    @Override
    public void saveData() {

    }

}
