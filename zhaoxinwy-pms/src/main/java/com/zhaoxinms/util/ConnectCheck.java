package com.zhaoxinms.util;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.zhaoxinms.common.utils.http.HttpUtils;

@Component
public class ConnectCheck {
    
    @PostConstruct
    public static void main(String args[]){
        String s = HttpUtils.sendGet("http://www.baidu.com");
        System.out.println(s);
    }
}
