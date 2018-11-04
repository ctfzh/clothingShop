package com.tl.customclothing.utils;

/**
 * Created by tianlin on 2017/3/31.
 * Tel : 15071485690
 * QQ : 953108373
 * Function :
 */

public class Constant
{
    // 服务器基本URL
    public static final String BASE_URL = "http://10.208.60.69:8080/CustomClothing/";

    // 读取时的默认值
    public static final String NULL = "null";

    // 两秒类按返回退出应用
    public static final int DELAY_TO_EXIT = 2000;

    // alert标志
    public static final String ALERT_DIALOG = "ALERT_DIALOG";

    // 性别
    public static final String MALE = "male";
    public static final String FEMALE = "female";

    // sdf
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_WITHOUT_DIVIDER = "yyyyMMddHHmmss";

    // 初始积分
    public static final String INIT_JIFEN = "0";

    // 注册
    public static final String REGISTER_SUCCESS = "S";
    public static final String REGISTER_FAILED = "E";

    // 登录
    public static final String LOGIN_SUCCESS = "S";
    public static final String LOGIN_FAILED = "E";
    // 同步用户数据成功
    public static final String ASYNC_SUCCESS = "S";

    public static final String CURRENT_JIFEN = "当前积分：";

    // 更新当前的用户
    public static final String USER_UPDATE = "USER_UPDATE";
    // 添加到购物车
    public static final String ADD_TO_CART_EVENT = "ADD_TO_CART_EVENT";
    // 更新订单的状态
    public static final String ORDER_SEARCH_REFRESH_EVENT = "ORDER_SEARCH_REFRESH_EVENT";

    // 缓存时间
    public static final long CACHE_TIME = 24 * 60 * 60 * 1000;

    // 假设模特为170厘米，标准身材的各种围
    public static final int MODEL_NORMAL_BUST = 70;
    public static final int MODEL_NORMAL_WAIST = 50;
    public static final int MODEL_NORMAL_HIP = 70;
    public static final int MODEL_NORMAL_HAND = 70;
    public static final int MODEL_NORMAL_LEG = 90;
    // 假设模特为170厘米，从150-190身材的各种围的极差
    public static final int MODEL_NORMAL_BUST_RANGE = 30;
    public static final int MODEL_NORMAL_WAIST_RANGE = 30;
    public static final int MODEL_NORMAL_HIP_RANGE = 30;
    public static final int MODEL_NORMAL_HAND_RANGE = 20;
    public static final int MODEL_NORMAL_LEG_RANGE = 30;
    // 服装类型
    public static final String UP = "上衣";
    public static final String UNDER = "下装";
    // 模特图片地址
    public static final String MODEL_HEAD_MALE = "model/male_head.png";
    public static final String MODEL_UP_MALE = "model/male_up.png";
    public static final String MODEL_UNDER_MALE = "model/male_under.png";
    public static final String MODEL_HEAD_FEMALE = "model/female_head.png";
    public static final String MODEL_UP_FEMALE = "model/female_up.png";
    public static final String MODEL_UNDER_FEMALE = "model/female_under.png";

    // url的后缀为.action
    public static final String URL_SUFFIX = ".action";

    // 订单状态,全部，待处理，处理中，待收货，待评论
    public static final String ORDER_STATUS_TO_DEAL_TITLE = "待处理";
    public static final String ORDER_STATUS_DEALING_TITLE = "处理中";
    public static final String ORDER_STATUS_RECEIVING_TITLE = "待收货";
    public static final String ORDER_STATUS_COMMENTING_TITLE = "待评论";
    public static final String ORDER_STATUS_FINISHING_TITLE = "已结束";
    public static final String ORDER_STATUS_TO_DEAL = "toDeal";
    public static final String ORDER_STATUS_DEALING = "dealing";
    public static final String ORDER_STATUS_RECEIVING = "receiving";
    public static final String ORDER_STATUS_COMMENTING = "commenting";
    public static final String ORDER_STATUS_FINISHING = "finishing";

    // 商品图片的高
    public static final int SHOP_IMG_HEIGHT = 150;
    public static final int SHOP_IMG_WIDTH = 100;
    // 瀑布式的布局每一行的商品数
    public static final int GRID_COL_COUNT = 2;

    // 用户所有的收货地址数量
    public static final int USER_ADDR_COUNT = 3;

    // 动画时长
    public static final long ANIMATION_DURATION = 500;
}
