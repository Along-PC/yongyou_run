package com.tourye.run;
/**
 *
 * @ClassName:   SaveConstants
 *
 * @Author:   along
 *
 * @Description:    存储key字段
 *
 * @CreateDate:   2019/4/4 9:29 AM
 *
 */
public interface SaveConstants {

    public static final String USER_ID="user_id";//用户id
    public static final String USER_HEAD="user_head";//用户头像
    public static final String USER_NAME="user_name";//用户名
    public static final String USER_TOKEN="Authorization";//用户名
    public static final String ACTION_ID="action_id";//活动id
    public static final String TEAM_ID="team_id";//队伍id
    public static final String LEVEL_ID="level_id";//队伍组别id
    public static final String IS_JOINED="is_joined";//是否加入战队
    public static final String AUTHORIZATION="Authorization";//用户token
    public static final String HASLOGIN="hasLogin";//是否登录
    public static final String PACKAGE_ID="package_id";//报名套餐id
    public static final String IS_CAN_UPGRADE_PACKAGE="is_can_upgrade_package";//是否可以更改套餐
    public static final String SIGN_IN_START_DATE="sign_in_start_date";//开赛时间---打卡开始时间
    public static final String SIGN_IN_FINISH_DATE="sign_in_finish_date";//活动结束时间---打卡结束时间
    public static final String MONITOR_APPLY_START_DATE="monitor_apply_start_date";//队长申请开始时间
    public static final String MONITOR_APPLY_FINISH_DATE="monitor_apply_finish_date";//队长申请结束时间
    public static final String SIGN_UP_START_DATE="sign_up_start_date";//报名开始时间
    public static final String SIGN_UP_FINISH_DATE="sign_up_finish_date";//报名截止时间
    public static final String SIGN_UP_PROTOCOL="sign_up_protocol";//报名协议
    public static final String IS_VERIFIED="is_verified";//是否实名认证了
    public static final String LEVEL_GROUP="level_group";//报名的组别
    public static final String TIPS_COVER="tips_cover";//锦囊页头图
    public static final String RULE_URL="rule_url";//活动规则

    public static final String SHARE_FIRST_TITLE="share_first_title";
    public static final String SHARE_SECOND_TITLE="share_second_title";

    public static final String IS_LONG_LIGHT="is_long_light";//是否开启跑步屏幕常亮
    public static final String IS_DEFAULT_LOCK="is_default_lock";//是否开启跑步默认锁屏
    public static final String IS_VOICE_BROADCAST="is_voice_broadcast";//是否开启跑步语音播报

    public static final String IS_SHOW_RUNNING_DIALOG="is_show_running_dialog";//是否显示轨迹打卡规则弹窗
    public static final String IS_SHOW_USER_PROTOCOL="is_show_user_protocol";//是否显示用户协议弹窗

}
