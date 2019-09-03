package com.tourye.run;

public class Constants {

    public static final String BASE_URL=BuildConfig.BASE_URL;
    public static final String DOMAIN=BuildConfig.DOMAIN_NAME;

    //用户基本信息
    public static final String USER_BASIC_INFO=BASE_URL+"/user/basic_info";
    //活动信息
    public static final String ACTION_INFO=BASE_URL+"/activity/activity_info";
    //战报列表
    public static final String BATTLE_REPORT_LIST=BASE_URL+"/activity/news";
    //活动评价
    public static final String ACTIVITY_COMMENT=BASE_URL+"/activity/activity_comment";
    //组别列表
    public static final String BATTLE_GROUP_LIST=BASE_URL+"/activity/level_list";
    //战队列表
    public static final String BATTLE_LIST=BASE_URL+"/activity/team_list";
    //广告
    public static final String ADVERTISE=BASE_URL+"/activity/footer_images";
    //打卡页广告
    public static final String PUNCH_ADVERTISE=BASE_URL+"/framework/sign_in_ads";
    //套餐列表
    public static final String PACKAGE_LIST=BASE_URL+"/activity/package_list";
    //队伍信息
    public static final String TEAM_INFO=BASE_URL+"/activity/team_info";
    //战队成员信息
    public static final String TEAM_MEMBER_INFO=BASE_URL+"/activity/team_members";
    //战队打卡信息
    public static final String TEAM_PUNCH_INFO=BASE_URL+"/activity/team_sign_in_list";
    //战队详情动态
    public static final String TEAM_DETAIL_DYNAMIC=BASE_URL+"/activity/team_moments";
    //个人完赛排行榜
    public static final String GAME_COMPLETE_RANKING=BASE_URL+"/activity/user_finish_rate_rank";
    //个人总距离排行榜
    public static final String DISTANCE_RANKING=BASE_URL+"/activity/user_total_distance_rank";
    //个人今日距离排行榜
    public static final String DISTANCE_TODAY_RANKING=BASE_URL+"/activity/today_distance_rank";
    //一级行政区列表
    public static final String TOP_DISTRICTS=BASE_URL+"/framework/top_level_districts";
    //次级行政区列表
    public static final String CHILD_DISTRICTS=BASE_URL+"/framework/child_districts";
    //----------社区
    //所有动态列表
    public static final String MOMENT_LIST=BASE_URL+"/community/moment_list";
    //动态详情
    public static final String DYNAMIC_DETAIL=BASE_URL+"/community/moment_detail";
    //点赞
    public static final String THUMB_UP=BASE_URL+"/community/like";
    //取消点赞
    public static final String THUMB_UP_CANCEL=BASE_URL+"/community/cancel_like";
    //评论列表
    public static final String COMMENT_LIST=BASE_URL+"/community/comment_list";
    //回复列表
    public static final String REPLY_LIST=BASE_URL+"/community/reply_list";
    //删除回复
    public static final String DELETE_REPLY=BASE_URL+"/community/delete_reply";
    //删除评论
    public static final String DELETE_COMMENT=BASE_URL+"/community/delete_comment";
    //删除动态
    public static final String DELETE_DYNAMIC=BASE_URL+"/community/delete_moment";
    //发表回复
    public static final String CREATE_REPLY=BASE_URL+"/community/create_reply";
    //发表评论
    public static final String CREATE_COMMENT=BASE_URL+"/community/create_comment";
    //发表动态列表
    public static final String CREATE_DYNAMIC=BASE_URL+"/community/create_moment";
    //个人中心---广告
    public static final String USER_CENTER_ADVERTISEMENT=BASE_URL+"/framework/user_center_ads";
    //队伍数据
    public static final String TEAM_DATA=BASE_URL+"/monitor/team_data";
    //队伍管理---影响人数
    public static final String TEAM_INFLUENCE=BASE_URL+"/monitor/affect_stats";
    //队伍管理---队伍奖励
    public static final String TEAM_AWARD=BASE_URL+"/monitor/prize_list";
    //礼品---获取礼品基础信息
    public static final String USER_GIFT_INFO=BASE_URL+"/gift/user_info";
    //获取验证码
    public static final String GET_VERIFY_CODE=BASE_URL+"/auth/send_login_sms";
    //登录
    public static final String USER_LOGIN=BASE_URL+"/auth/check_login_sms";
    //战队完赛率排行榜
    public static final String TEAM_FINISH_RATE_RANK=BASE_URL+"/activity/team_finish_rate_rank";
    //战队人气排行榜
    public static final String TEAM_THUMB_UP_RANK =BASE_URL+"/activity/team_thumb_up_rank";
    //个人人气排行榜
    public static final String PERSON_THUMB_UP_RANK =BASE_URL+"/activity/user_thumb_up_rank";
    //个人邀请排行榜
    public static final String PERSON_INVITE_RANK =BASE_URL+"/activity/user_referrer_rank";
    //锦囊
    public static final String ACTION_TIPS =BASE_URL+"/participation/tips";
    //个人打卡距离统计
    public static final String USER_STATS =BASE_URL+"/user/stats";
    //检查是否有最新消息
    public static final String CHECK_NEWS =BASE_URL+"/system_notice/check";
    //系统消息
    public static final String MESSAGE_SYSTEM =BASE_URL+"/system_notice/notice_list";
    //评论消息
    public static final String MESSAGE_COMMENT =BASE_URL+"/system_notice/reply_list";
    //点赞消息
    public static final String MESSAGE_THUMB =BASE_URL+"/system_notice/like_list";
    //搜索战队
    public static final String SEARCH_BATTLE =BASE_URL+"/activity/search_team";
    //获取待检查打卡数量
    public static final String CHECK_SIGN_IN_COUNT =BASE_URL+"/participation/check_sign_in_count";
    //获取检查打卡记录
    public static final String GET_CHECK_PUNCH =BASE_URL+"/participation/check_sign_in";
    //日历打卡记录
    public static final String CALENDAR_PUNCH_RECORD =BASE_URL+"/participation/sign_in_calendar";
    //上传图片
    public static final String UPLOAD_IMAGE =BASE_URL+"/framework/upload_image";
    //创建战队
    public static final String CREATE_TEAM =BASE_URL+"/monitor/create_team";
    //截图打卡
    public static final String SCREEN_PUNCH =BASE_URL+"/participation/create_sign_in";
    //截图打卡生成图片
    public static final String SCREEN_PUNCH_CARD =BASE_URL+"/participation/sign_in_card";
    //获取支付宝订单
    public static final String Sign_ORDER_ZFB =BASE_URL+"/participation/ali_app_pay";
    //查询支付宝支付状态
    public static final String ZFB_PAY_STATUS =BASE_URL+"/participation/pay_status";
    //创建报名订单
    public static final String CREATE_ORDER =BASE_URL+"/participation/create_order";
    //获取打卡详情
    public static final String PUNCH_DETAIL =BASE_URL+"/participation/sign_in_detail";
    //检查队长申请状态
    public static final String LEADER_APPLY_STATUS =BASE_URL+"/monitor_apply/check";
    //提交队长申请
    public static final String LEADER_APPLY =BASE_URL+"/monitor_apply/create";
    //获取招募信息
    public static final String MONITOR_RECRUIT_STATS =BASE_URL+"/monitor/recruit_stats";
    //获取自己所有队伍列表
    public static final String MONITOR_TEAM_LIST =BASE_URL+"/monitor/team_list";
    //对用户点赞
    public static final String USER_THUMB_UP =BASE_URL+"/participation/create_thumb_up";
    //对用户取消点赞
    public static final String USER_THUMB_UP_CANCEL =BASE_URL+"/participation/cancel_thumb_up";
    //更新用户信息
    public static final String UPDATE_USER_INFO =BASE_URL+"/user/user_info";
    //战队招募人数排行榜
    public static final String team_recruit_rank =BASE_URL+"/monitor/rank";
    //记录战队详情页访问情况
    public static final String RECORD_TEAM_VISIT =BASE_URL+"/activity/team_visit";
    //获取影响人员列表
    public static final String INFLUENCE_PEOPLE_LIST =BASE_URL+"/monitor/affect_list";
    //获取队员打卡详情
    public static final String TEAM_SIGN_IN_DETAIL =BASE_URL+"/monitor/team_sign_in_detail";
    //获取队伍打卡情况
    public static final String TEAM_SIGN_IN =BASE_URL+"/monitor/team_sign_in";
    //获取已经生效的奖金提取
    public static final String PRIZE_DETAIL =BASE_URL+"/prize/prize_detail";
    //获取未生效的奖金提取
    public static final String WITHDRAW_DETAIL =BASE_URL+"/prize/withdraw_detail";
    //获取队伍打卡日历
    public static final String TEAM_SIGN_IN_CALENDAR =BASE_URL+"/monitor/team_sign_in_calendar";
    //获取合同类型
    public static final String CONTRACT_TYPE =BASE_URL+"/prize/contract_type";
    //检查提现是否签约
    public static final String CHECK_CONTRACT =BASE_URL+"/prize/check_contract";
    //获取奖金可提现余额
    public static final String PRIZE_ACCOUNT =BASE_URL+"/prize/account";
    //发起签约
    public static final String CREATE_CONTRACT =BASE_URL+"/prize/create_contract";
    //发起实名认证
    public static final String INIT_REAL_NAME_VERIFY =BASE_URL+"/user/init_real_name_verify";
    //校验实名认证
    public static final String CHECK_REAL_NAME_VERIFY =BASE_URL+"/user/check_real_name_verify";
    //获取上上签签约链接
    public static final String CONTRACT_SIGN_URL =BASE_URL+"/prize/contract_sign_url";
    //获取提现账户
    public static final String GET_WITHDRAWAL_ACCOUNT =BASE_URL+"/prize/withdraw_method";
    //更新提现账户
    public static final String UPDATE_WITHDRAWAL_ACCOUNT =BASE_URL+"/prize/withdraw_method";
    //发起提现
    public static final String WITHDRAW =BASE_URL+"/prize/withdraw";
    //检查上上签付款状态
    public static final String CONTRACT_SIGN_STATUS =BASE_URL+"/prize/contract_sign_status";
    //物流列表
    public static final String LOGISTICS_LIST =BASE_URL+"/gift/orders";
    //获取队长合同
    public static final String GET_LEADER_CONTRACT =BASE_URL+"/prize/contract_view_url";
    //更新队伍信息
    public static final String UPDATE_TEAM_INFO =BASE_URL+"/monitor/team_info";
    //历届报名情况---报名列表
    public static final String SIGNUP_INFO =BASE_URL+"/participation/list";
    //获取今日打卡情况
    public static final String TODAY_PUNCH_DETAIL =BASE_URL+"/participation/sign_in_data";
    //解析地理位置
    public static final String PARSE_LOCATION =BASE_URL+"/framework/decode_geo_location";
    //轨迹存储
    public static final String STORE_TRACE =BASE_URL+"/app/store_trace";
    //举报动态
    public static final String REPORT_DYNAMIC =BASE_URL+"/community/report";
    //获取已经上传的轨迹
    public static final String GET_TRACE =BASE_URL+"/app/get_trace";
    //刷新token
    public static final String REFRESH_TOKEN =BASE_URL+"/auth/refresh_token";

}
