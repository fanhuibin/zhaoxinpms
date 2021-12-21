package com.zhaoxinms.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.zhaoxinms.base.util.StringUtil;

public class ValidateUtil {
    /** 整数 */
    private static final String V_INTEGER = "^-?[1-9]\\d*$";

    /** 正整数 */
    private static final String V_Z_INDEX = "^[1-9]\\d*$";

    /** 负整数 */
    private static final String V_NEGATIVE_INTEGER = "^-[1-9]\\d*$";

    /** 数字 */
    private static final String V_NUMBER = "^([+-]?)\\d*\\.?\\d+$";

    /** 正数 */
    private static final String V_POSITIVE_NUMBER = "^[1-9]\\d*|0$";

    /** 负数 */
    private static final String V_NEGATINE_NUMBER = "^-[1-9]\\d*|0$";

    /** 浮点数 */
    private static final String V_FLOAT = "^([+-]?)\\d*\\.\\d+$";

    /** 正浮点数 */
    private static final String V_POSTTIVE_FLOAT = "^[0-9]\\d*\\.\\d*|0.\\d*[1-9]\\d*$";

    /** 负浮点数 */
    private static final String V_NEGATIVE_FLOAT = "^-([1-9]\\d*\\.\\d*|0.\\d*[1-9]\\d*)$";

    /** 非负浮点数（正浮点数 + 0） */
    private static final String V_UNPOSITIVE_FLOAT = "^[1-9]\\d*\\.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$";

    /** 非正浮点数（负浮点数 + 0） */
    private static final String V_UN_NEGATIVE_FLOAT = "^(-([1-9]\\d*\\.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$";

    /** 邮件 */
    private static final String V_EMAIL =
        "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

    /** 颜色 */
    private static final String V_COLOR = "^[a-fA-F0-9]{6}$";

    /** url */
    private static final String V_URL = "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$";

    /** 仅中文 */
    private static final String V_CHINESE = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";

    /** 仅ACSII字符 */
    private static final String V_ASCII = "^[\\x00-\\xFF]+$";

    /** 邮编 */
    private static final String V_ZIPCODE = "^\\d{6}$";

    /** 手机 */
    private static final String V_MOBILE = "^(1)[0-9]{10}$";

    /** ip地址 */
    private static final String V_IP4 =
        "^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$";

    /** 非空 */
    private static final String V_NOTEMPTY = "^\\S+$";

    /** 图片 */
    private static final String V_PICTURE = "(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$";

    /** 压缩文件 */
    private static final String V_RAR = "(.*)\\.(rar|zip|7zip|tgz)$";

    /** 日期 */
    private static final String V_DATE =
        "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";

    /** QQ号码 */
    private static final String V_QQ_NUMBER = "^[1-9]*[1-9][0-9]*$";

    /** 电话号码的函数(包括验证国内区号,国际区号,分机号) */
    private static final String V_TEL = "^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$";

    /** 用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串 */
    private static final String V_USERNAME = "^\\w+$";

    /** 字母 */
    private static final String V_LETTER = "^[A-Za-z]+$";

    /** 大写字母 */
    private static final String V_LETTER_U = "^[A-Z]+$";

    /** 小写字母 */
    private static final String V_LETTER_I = "^[a-z]+$";

    /** 身份证 */
    private static final String V_IDCARD = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$";

    /** 验证密码(数字和英文同时存在) */
    private static final String V_PASSWORD_REG = "[A-Za-z]+[0-9]";

    /** 验证密码长度(6-18位) */
    private static final String V_PASSWORD_LENGTH = "^\\d{6,18}$";

    /** 验证两位数 */
    private static final String V_TWO＿POINT = "^[0-9]+(.[0-9]{2})?$";

    /** 验证一个月的31天 */
    private static final String V_31DAYS = "^((0?[1-9])|((1|2)[0-9])|30|31)$";

    private ValidateUtil() {}

    /**
     * 验证是不是整数
     * 
     * @param value
     *            要验证的字符串 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Integer(String value) {
        return match(V_INTEGER, value);
    }

    /**
     * 验证是不是正整数
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Z_index(String value) {
        return match(V_Z_INDEX, value);
    }

    /**
     * 验证是不是负整数
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Negative_integer(String value) {
        return match(V_NEGATIVE_INTEGER, value);
    }

    /**
     * 验证是不是数字
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Number(String value) {
        return match(V_NUMBER, value);
    }

    /**
     * 验证是不是正数
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean PositiveNumber(String value) {
        return match(V_POSITIVE_NUMBER, value);
    }

    /**
     * 验证是不是负数
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean NegatineNumber(String value) {
        return match(V_NEGATINE_NUMBER, value);
    }

    /**
     * 验证一个月的31天
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Is31Days(String value) {
        return match(V_31DAYS, value);
    }

    /**
     * 验证是不是ASCII
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean ASCII(String value) {
        return match(V_ASCII, value);
    }

    /**
     * 验证是不是中文
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Chinese(String value) {
        return match(V_CHINESE, value);
    }

    /**
     * 验证是不是颜色
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Color(String value) {
        return match(V_COLOR, value);
    }

    /**
     * 验证是不是日期
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Date(String value) {
        return match(V_DATE, value);
    }

    /**
     * 验证是不是邮箱地址
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Email(String value) {
        return match(V_EMAIL, value);
    }

    /**
     * 验证是不是浮点数
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Float(String value) {
        return match(V_FLOAT, value);
    }

    /**
     * 验证是不是正确的身份证号码
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean IDcard(String value) {
        return match(V_IDCARD, value);
    }

    /**
     * 验证是不是正确的IP地址
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean IP4(String value) {
        return match(V_IP4, value);
    }

    /**
     * 验证是不是字母
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Letter(String value) {
        return match(V_LETTER, value);
    }

    /**
     * 验证是不是小写字母
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Letter_i(String value) {
        return match(V_LETTER_I, value);
    }

    /**
     * 验证是不是大写字母
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Letter_u(String value) {
        return match(V_LETTER_U, value);
    }

    /**
     * 验证是不是手机号码
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Mobile(String value) {
        return match(V_MOBILE, value);
    }

    /**
     * 验证是不是负浮点数
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Negative_float(String value) {
        return match(V_NEGATIVE_FLOAT, value);
    }

    /**
     * 验证非空
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Notempty(String value) {
        return match(V_NOTEMPTY, value);
    }

    /**
     * 验证密码的长度(6~18位)
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Number_length(String value) {
        return match(V_PASSWORD_LENGTH, value);
    }

    /**
     * 验证密码(数字和英文同时存在)
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Password_reg(String value) {
        return match(V_PASSWORD_REG, value);
    }

    /**
     * 验证图片
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Picture(String value) {
        return match(V_PICTURE, value);
    }

    public static boolean PositiveFloatOrNum(String num) {
        if (StringUtils.isEmpty(num)) {
            return false;
        }
        if (ValidateUtil.Posttive_float(num) || ValidateUtil.PositiveNumber(num)) {
            return true;
        }
        return false;
    }

    /**
     * 验证正浮点数
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Posttive_float(String value) {
        return match(V_POSTTIVE_FLOAT, value);
    }

    /**
     * 验证QQ号码
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean QQnumber(String value) {
        return match(V_QQ_NUMBER, value);
    }

    /**
     * 验证压缩文件
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Rar(String value) {
        return match(V_RAR, value);
    }

    /**
     * 验证电话
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Tel(String value) {
        return match(V_TEL, value);
    }

    /**
     * 验证两位小数
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Two_point(String value) {
        return match(V_TWO＿POINT, value);
    }

    /**
     * 验证非正浮点数
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Un_negative_float(String value) {
        return match(V_UN_NEGATIVE_FLOAT, value);
    }

    /**
     * 验证非负浮点数
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Unpositive_float(String value) {
        return match(V_UNPOSITIVE_FLOAT, value);
    }

    /**
     * 验证URL
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Url(String value) {
        return match(V_URL, value);
    }
    
    public static boolean validDateStr(String dateStr, String pattern) {
        if (StringUtil.isEmpty(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        try {
            LocalDate.parse(dateStr, new DateTimeFormatterBuilder().appendPattern(pattern).parseStrict().toFormatter());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证用户注册。匹配由数字、26个英文字母或者下划线组成的字符串
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean UserName(String value) {
        return match(V_USERNAME, value);
    }

    /**
     * 验证邮编
     * 
     * @param value
     *            要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Zipcode(String value) {
        return match(V_ZIPCODE, value);
    }

    /**
     * @param regex
     *            正则表达式字符串
     * @param str
     *            要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
