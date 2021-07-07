package com.mci.common.exception;

/***
 * 错误码和错误信息定义类
 * 1. 错误码定义规则为5为数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：100001。10:通用 001:系统未知异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * 错误码列表：
 *  10: 通用
 *      001：参数格式校验
 *  11: 商品 - Product
 *  12: 订单 - Purchase order
 *  13: 购物车 - Shopping Cart
 *  14: 物流 - Logistic
 *  15: Member/User
 *
 */
public enum BizCodeEnume {
    UNKNOWN_EXCEPTION(10000, "Unknown system error"),
    VALID_EXCEPTION(10001, "Parameter format error"),
    SMS_CODE_EXCEPTION(10002, "New code request must be superior than 60 seconds"),
    PRODUCT_UP_EXCEPTION(11000, "Product status up error"),
    USER_ALREADY_EXIST_EXCEPTION(15001, "User already exist"),
    PHONE_ALREADY_EXIST_EXCEPTION(15002, "Phone number already exist"),
    LOGIN_ACCOUNT_PASSWORD_INVALID_EXCEPTION(15003, "Login account or password invalid");

    private int code;
    private String msg;

    BizCodeEnume(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
