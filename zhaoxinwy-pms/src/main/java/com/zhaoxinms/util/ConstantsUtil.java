package com.zhaoxinms.util;

public class ConstantsUtil {

    /** 商铺状态 */
    public static final String HOUSE_STATE_EMPTY = "empty";
    public static final String HOUSE_STATE_RENTED = "rented";
    public static final String HOUSE_STATE_SELLED = "selled";

    /** 押金状态 */
    public static final String DEPOSIT_STATE_PAIED = "paied";
    public static final String DEPOSIT_STATE_REFUND = "refunded";

    /** 支付记录表类型 */
    public static final String PAY_LOG_TYPE_PAY = "pay"; // 收款
    public static final String PAY_LOG_TYPE_REFUND = "refund"; // 退款

    /** 支付记录费用分类 */
    public static final String PAY_LOG_FEE_TYPE_DEPOSIT = "deposit";
    public static final String PAY_LOG_FEE_TYPE_TEMP = "temp";
    public static final String PAY_LOG_FEE_TYPE_HOUSE = "house";

    /** 预收款类型 */
    public static final String PAY_PRE_TYPE_ADD = "add"; // 收款
    public static final String PAY_PRE_TYPE_PAYADD = "payAdd"; // 转结
    public static final String PAY_PRE_TYPE_REFUND = "refund"; // 退还
    public static final String PAY_PRE_TYPE_PAY = "pay"; // 支付

    /** 付费数据类型 */
    public static final String PAY_BILL_TYPE_HOUSE = "house"; // 商铺
    
    /**
     * 退款状态
     */
    public static final int PAY_BILL_REFUND_STATE_NONE = 0; //未退款
    public static final int PAY_BILL_REFUND_STATE_PARTIAL = 1; //部分退款
    public static final int PAY_BILL_REFUND_STATE_ALL = 2; //全部退款
    
    /** 支付状态 */
    public static final int PAY_BILL_PAY_STATE_UNPAIED = 0; //未缴费
    public static final int PAY_BILL_PAY_STATE_PAYING = 2; //付款中
    public static final int PAY_BILL_PAY_STATE_PAIED = 1; //已缴费
    
    /** yes or no **/
    public static final byte NO = 0;
    public static final byte YES = 1;
    
    /**数据来源**/
    public static final int CLIENT_WX_PC = 1; //pc管理端
    public static final int CLIENT_WX_MINIAPP = 2; //业主小程序    
}
