package com.zhaoxinms.weixin.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.zhaoxinms.weixin.utils.SignUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpDataCubeService;
import me.chanjar.weixin.mp.api.WxMpService;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wxCheck")
public class WxTokenController extends BaseController {
    
    
    @GetMapping("/check")
    public void getUsersummary(HttpServletRequest request, HttpServletResponse response) throws IOException {
       String token = "";
        if (StringUtils.isNotBlank(request.getParameter("signature"))) {
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");
            if (SignUtil.checkSignature(token, signature, timestamp, nonce)) {
                log.info("数据源为微信后台，将echostr[{}]返回！", echostr);
                response.getOutputStream().print(echostr);
            }
        }
    }
    
    @GetMapping("model")
    public void getJson() {
        
    }
}
