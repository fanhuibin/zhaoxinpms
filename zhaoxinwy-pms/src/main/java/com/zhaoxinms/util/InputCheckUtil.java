package com.zhaoxinms.util;

import org.apache.commons.lang3.StringUtils;

public class InputCheckUtil {

    public static boolean isNotEmpty(String s) {
        if ("null".equals(s)) {
            return false;
        } else if (StringUtils.isEmpty(s)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isNotEmpty(Object s) {
        if ("null".equals(s)) {
            return false;
        } else if (s == null) {
            return false;
        } else {
            return true;
        }
    }
}
