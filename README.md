# 安卓购物商城APP
### 版本 v1.3.7

### 项目背景

本项目是一个安卓开发课程期末大作业client端项目

作为一个作业性质项目,其本身有许多不完善之处,也有很多功能未实现,考虑到大部分学校对此类作业没有项目完整性要求,因此本着开源精神,将源码放出,给有类似开发需求的同学做一个参考

基于以上背景,本文档内容主要面向初学者,如有错误恳请路过大佬不吝指教

### 项目介绍

购物商城APP项目是一个基于Android平台的餐饮的O2O购物平台,app有5个基本页面(底部有常驻按钮)和三个功能页面

基本页面有:
- 主页
- 分类页
- 社区页
- 购物车
- 用户页

功能页面有:
- 商品详情页
- 登录页
- 注册页

注意:本项目所有测试基于Android Studio内置模拟器中Pixel 4a手机在API 32下的情况,无法保证在使用其他模拟器或其他机型下的正常运行,为减少出现不可预期的bug,请在该机型下运行项目.如果使用其他机型,请尽可能在5.8寸的1080*2340px屏幕环境下运行

### 功能说明
各个页面主要实现了以下功能:

- 主页
  - 以**每行2列的网格布局形式**显示部分菜品的名称,描述,价格,图片,
  - 点击每个商品会进入商品详情页面
  
- 分类页
  - 以**列表**形式显示所有菜品的名称,描述,价格,图片,
  - 点击每个商品会进入商品详情页面
  
- 商品详情页
  - 展示了商品的所有信息
  - 点击**加入购物车按钮**可以将商品添加到购物车列表
  
- 社区页
  - 使用ViewPager展示可滑动的广告页面
  
- 购物车页面
  - 显示添加到购物车的商品
  - **勾选商品和点击加减号后购物车总价会发生相应的改变**
  - **点击删除可以将商品从购物车删除**
  - 进入购物车页面自动刷新数据,切换到其他页面自动保存数据
  - 点击结算显示总价,完成结算
  
- 用户页面
  - 在未登录状态下点击"请登录"字样,跳转到登录页面
  - 登录状态下点击退出登录按钮完成退出登录
  - 登录状态下显示当前登录用户名称和简介
  
- 登录界面
  - 根据用户名和密码登录用户账号
  - 点击"注册"按钮前往注册页面
  - 注册页面完成注册后,自动登录
  
- 注册页面
  - 根据信息注册用户账号

注意:

- 本项目除社区页的广告页面为静态数据外,**其他数据都从后台获取**

- 在无法获取后台数据的情况下会出现"**获取数据失败,服务器错误**"字样

若出现该情况,请检查:
1. 是否正确搭建后台项目
2. 是否正确配置后台IP地址和端口号

### 技术栈
项目使用到以下安卓知识点
- intent跳转,传参
- Fragment(碎片)
- RecyclerView,Adapter,ViewHolder
- ViewPager
- AlertDialog
  
项目使用到以下框架
- okhttp3
- gson 2.7



### 上手指南
#### 部署后台程序
在使用本项目前需要部署app对应的后台程序,用于提供数据

传送地址: https://github.com/Ervinse/ShoppingMallApplicationBackendService.git

#### 获取项目
项目使用方式非常简单,步骤如下:
- 方式一
  1. 打开Android Studio,在欢迎页面(Welcome to Android Studio)中点击Get from VCS按钮
  2. 在Version control复选框中选择Git
  3. 在URL框中填入本项目地址:git@github.com:Ervinse/ShoppingMallApplication.git
  4. 在Directory框中选择导入本地后的目录
  5. 点击按钮Clone
   (注意:随着Android Studio版本更新,导入方式可能会发生改变.文档中的方式仅针对2021.2.1版本(2022年5月),如果发现该方法不适用,请善用搜索引擎搜索问题"如何在Android Studio中导入git项目")
- 方式二
  1. 在页面中选中master分支
  2. 点击code按钮,选择下拉菜单中的Download ZIP,下载对应的压缩包文件
  3. 解压文件,在Android Studio中打开对应的项目
  4. 等待gradle加载完成

注意:本项目使用了okhttp3和gson框架,以及相应的androidx扩展包,如果您电脑中之前没有使用这些框架,则需要**等待gradle下载完成**,由于网络原因,过程可能比较慢,请善用梯子/加速器等工具

#### 开发前配置
在完成后台部署,并加载项目后,我们需要在config.properties文件中配置后台IP地址和端口号,具体路径为:app/src/main/assets/config.properties

在本项目中我们以本地后台服务器为例,因此**后台地址为本机ip地址**,端口号参考后台程序application.yml文件中server:port: xxxx,**默认为8088**.(如果不会查看本机ip地址,请善用搜索引擎搜索问题"如何查看本机ip地址")

注意:在更改网络环境之后(包括但不限于切换wifi),**本机ip地址会有可能发生改变**,记得修改config.properties文件中参数

### 程序说明

- 打开app后进入的首页,对应的是MainActivity,在MainActivity内含有五个Fragment,对应五个基本页面,而下方标签式导航则直接由MainActivity加载
- 五个Fragment都继承于BaseFragment,这是一个基类Fragemnt,用于编写所有Fragment中共有功能,各个Fragment在继承它时应重写这些方法,例如:
  - `public abstract View initView()` 用于初始化组件
  - `public void initData()` 用于初始化数据
  - `public abstract void refreshData()` 用于切换回该页面时获取最新数据
  - `public abstract void saveData()` 用于将要切换到其他页面时将本地数据保存到后台数据库
- 在MainActivity中定义方法
  `private void switchFragment(BaseFragment fromFragment, BaseFragment nextFragment)`
  用于切换Fragment,其中fromFragment为上次显示的Fragment,nextFragment为当前正要显示的Fragment
  
  使用`FragmentTransaction`对象来控制Fragment的显示与隐藏,并在显示Fragment前调用`fromFragment.saveData();`,在隐藏Fragment前调用`nextFragment.refreshData();`达到切换页面前保存数据,切换页面后刷新数据
- 所有后台请求使用okhttp3框架发送,为简化开发,我将其发送get请求和post请求的代码封装到了utils目录下的OkhttpUtils工具类中,需要时直接使用静态类OkhttpUtils.doGet(参数)/.doPost(参数)发送对应请求即可
- 由于app中存在大量需要发送请求的地方,因此我写了一个位于utils目录下的PropertiesUtils工具类,用于读取config.properties中的地址和端口号,并将其转换为后台地址,需要时直接使用静态类PropertiesUtils.getUrl(mContext)获取后台url字符串,在之后拼接web层映射地址即可.
  列如:

  ```
  //发送登录请求
  String url = PropertiesUtils.getUrl(mContext);
  responseJson = OkhttpUtils.doPost(url + "/users/login", userJson);
  ```

  其中,url为通过静态类获取的后台url,mContext为上下文,/users/login为后台web层映射,userJson为参数列表
- 前后端数据交互使用json格式的字符串,在之前的版本中使用协议类,但是由于gson工具在解析协议类时存在数据类型转换异常的bug,故在v1.1版本之后弃用
- 在Android4.0以后，系统不再允许直接在主线程中发送HTTP请求,因此需要开辟一个子线程，然后等到数据返回成功后再刷新UI,以防止长时间后台未响应导致报错
  同时系统也不再运行直接在子线程中操控UI,因此我们使用Handler对象(线程处理器)来将子线程消息转发到主线程中操控UI,本项目使用比较多的在购物车模块的增删改查
  例如:
  ```
  public class ShoppingCartFragment extends BaseFragment {
      ...
    //线程处理器
    private Handler handler = new Handler();
    ...
     @Override
    public void refreshData() {
        Log.i(TAG, "联网刷新数据");
        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "进入获取购物车商品线程");
                ...
                try {
                    //发送登录请求
                    ...
                    //数据获取成功,加创建商品布局
                    if (goodsList != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ...
                            }
                        });
                    }

                } catch (IOException e) {
                    ...
                    Toast.makeText(mContext, "获取数据失败,服务器错误", Toast.
                    ...
                }
            }
        }.start();
    }
  }
  ```
- 商品图片保存在`app/src/main/res/drawable-hdpi-v4`目录下,使用`goods_image_x.png`命名,x代表商品序号,商品图片名保存在mysql数据库中,加载时使用`getResources()`方法将图片名解析为本地id,再使用`setImageResource()`方法为安卓控件赋值,例如
  ```
  //通过图片名字获取图片资源的id
  int id = mContext.getResources().getIdentifier(goodsList.get(position).getImage(), "drawable", mContext.getPackageName());
  holder.item_image.setImageResource(id);
  ```


### 版本控制
该项目使用Git进行版本管理。您可以在repository参看当前可用版本

### 参与贡献
@南风