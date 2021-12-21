package com.zhaoxinms.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.Options;

public class DynamicExpressiontUtil {
    // public static void main(String[] args) {
    // Map<String, Object> map = new HashMap(4);
    // map.put("单价", new BigDecimal(1.09)); //M后缀代表用bigDecimal计算
    // map.put("数量", new BigDecimal(2.01));
    //
    // //编译且执行表达式。
    // Expression script = AviatorEvaluator.getInstance().compile("if(单价<10){return 单价*数量;}");
    // Object result = script.execute(map);
    // BigDecimal isDiscount = ((BigDecimal)result).setScale(2, BigDecimal.ROUND_HALF_UP);
    // }

    public static void validateExpress(String express) {
        String testNum = "10";
        String testPrice = "1";
        String result = getExpressResult(express, testNum, testPrice);
    }

    public static String getExpressResult(String express, String num, String price) {
        Map<String, Object> map = new HashMap(4);
        map.put("单价", new BigDecimal(price)); // M后缀代表用bigDecimal计算
        map.put("数量", new BigDecimal(num));
        AviatorEvaluator.getInstance().setOption(Options.ALWAYS_PARSE_FLOATING_POINT_NUMBER_INTO_DECIMAL, true);
        // 编译且执行表达式。
        Expression script = AviatorEvaluator.getInstance().compile(express);
        Object result = script.execute(map);
        BigDecimal isDiscount = ((BigDecimal)result).setScale(2, BigDecimal.ROUND_HALF_UP);
        return isDiscount.toString();
    }
}
