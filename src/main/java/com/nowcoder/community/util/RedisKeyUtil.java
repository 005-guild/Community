package com.nowcoder.community.util;

//生成redis命令的工具类
public class RedisKeyUtil {

    //分隔符
    private static final String SPLIT = ":";
    //点赞实体
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    //点赞用户
    private static final String PREFIX_USER_LIKE = "like:user";
    //用户关注实体
    private static final String PREFIX_FOLLOWEE = "followee";
    //实体拥有的粉丝
    private static final String PREFIX_FOLLOWER = "follower";
    //登录验证码
    private static final String PREFIX_KAPTCHA = "kaptcha";
    private static final String PREFIX_CODE = "code";
    //登录凭证
    private static final String PREFIX_TICKET = "ticket";
    // 用户
    private static final String PREFIX_USER = "user";
    //日期数据传入年月日就够了，不用时分秒
    // 单日UV
    private static final String PREFIX_UV = "uv";
    // 单日活跃用户
    private static final String PREFIX_DAU = "dau";
    private static final String PREFIX_POST = "post";

    // 某个实体的赞,实体是帖子或评论
    // like:entity:entityType:entityId -> set(userId)
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    // 某个用户的赞
    // like:user:userId -> int
    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }

    // 某个用户关注的实体
    // followee:userId:entityType -> zset(entityId,now)
    public static String getFolloweeKey(int userId, int entityType) {
        return PREFIX_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
    }

    // 某个实体拥有的粉丝
    // follower:entityType:entityId -> zset(userId,now)
    public static String getFollowerKey(int entityType, int entityId) {
        return PREFIX_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }

    // 登录验证码
    public static String getKaptchaKey(String owner) {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }

    // 登录的凭证
    public static String getTicketKey(String ticket) {
        return PREFIX_TICKET + SPLIT + ticket;
    }

    // 用户
    public static String getUserKey(int userId) {
        return PREFIX_USER + SPLIT + userId;
    }

    //日期数据传入年月日就够了，不用时分秒
    // 单日UV
    public static String getUVKey(String date) {
        return PREFIX_UV + SPLIT + date;
    }

    // 区间UV
    public static String getUVKey(String startDate, String endDate) {
        return PREFIX_UV + SPLIT + startDate + SPLIT + endDate;
    }

    // 单日活跃用户
    public static String getDAUKey(String date) {
        return PREFIX_DAU + SPLIT + date;
    }

    // 区间活跃用户
    public static String getDAUKey(String startDate, String endDate) {
        return PREFIX_DAU + SPLIT + startDate + SPLIT + endDate;
    }

    // 帖子分数
    public static String getPostScoreKey() {
        return PREFIX_POST + SPLIT + "score";
    }

    public static String getCodeKey(String owner) {
        return PREFIX_CODE + SPLIT + owner;
    }

}
