// package com.zhaoxinms.weixin.controller.api;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
//import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
//import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
//import com.github.binarywang.wxpay.exception.WxPayException;
//import com.github.binarywang.wxpay.service.WxPayService;
//import com.ruoyi.common.core.domain.AjaxResult;
//import com.zhaoxinms.weixin.config.CommonConstants;
//import com.zhaoxinms.weixin.config.WxPayConfiguration;
//import com.zhaoxinms.weixin.constant.MyReturnCode;
//import com.zhaoxinms.weixin.entity.WxUser;
//import com.zhaoxinms.weixin.utils.LocalDateTimeUtils;
//import com.zhaoxinms.weixin.utils.ThirdSessionHolder;
//import com.zhaoxinms.weixin.utils.WxMaUtil;
//
//import cn.hutool.json.JSONUtil;
//import io.swagger.annotations.ApiOperation;
//
//public class PayController {
//    /**
//     * 调用统一下单接口，并组装生成支付所需参数对象.
//     *
//     * @param orderInfo 统一下单请求参数
//     * @return 返回 {@link com.github.binarywang.wxpay.bean.order}包下的类对象
//     */
//    @ApiOperation(value = "调用统一下单接口")
//    @PostMapping("/unifiedOrder")
//    public AjaxResult unifiedOrder(HttpServletRequest request, @RequestBody OrderInfo orderInfo) throws WxPayException {
//        //检验用户session登录
//        WxUser wxUser = new WxUser();
//        wxUser.setId(ThirdSessionHolder.getThirdSession().getWxUserId());
//        wxUser.setSessionKey(ThirdSessionHolder.getThirdSession().getSessionKey());
//        wxUser.setOpenId(ThirdSessionHolder.getThirdSession().getOpenId());
//        orderInfo = orderInfoService.getById(orderInfo.getId());
//        if(orderInfo == null){
//            return AjaxResult.error(MyReturnCode.ERR_70005.getCode(), MyReturnCode.ERR_70005.getMsg());
//        }
//        if(!CommonConstants.NO.equals(orderInfo.getIsPay())){//只有未支付的详单能发起支付
//            return AjaxResult.error(MyReturnCode.ERR_70004.getCode(), MyReturnCode.ERR_70004.getMsg());
//        }
//        if(orderInfo.getPaymentPrice().compareTo(BigDecimal.ZERO)==0){//0元购买不调支付
//            orderInfo.setPaymentTime(LocalDateTime.now());
//            orderInfoService.notifyOrder(orderInfo);
//            return AjaxResult.success();
//        }
//        String appId = WxMaUtil.getAppId(request);
//        WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
//        wxPayUnifiedOrderRequest.setAppid(appId);
//        String body = orderInfo.getName();
//        body = body.length() > 40 ? body.substring(0,39) : body;
//        wxPayUnifiedOrderRequest.setBody(body);
//        wxPayUnifiedOrderRequest.setOutTradeNo(orderInfo.getOrderNo());
//        wxPayUnifiedOrderRequest.setTotalFee(orderInfo.getPaymentPrice().multiply(new BigDecimal(100)).intValue());
//        wxPayUnifiedOrderRequest.setTradeType("JSAPI");
//        wxPayUnifiedOrderRequest.setNotifyUrl(mallConfigProperties.getNotifyHost()+"/weixin/api/ma/orderinfo/notify-order");
//        wxPayUnifiedOrderRequest.setSpbillCreateIp("127.0.0.1");
//        wxPayUnifiedOrderRequest.setOpenid(wxUser.getOpenId());
//        WxPayService wxPayService = WxPayConfiguration.getPayService();
//        return AjaxResult.success(JSONUtil.parse(wxPayService.createOrder(wxPayUnifiedOrderRequest)));
//    }
//
//    /**
//     * 支付回调
//     * @param xmlData
//     * @return
//     * @throws WxPayException
//     */
//    @ApiOperation(value = "支付回调")
//    @PostMapping("/notify-order")
//    public String notifyOrder(@RequestBody String xmlData) throws WxPayException {
//        log.info("支付回调:"+xmlData);
//        WxPayService wxPayService = WxPayConfiguration.getPayService();
//        WxPayOrderNotifyResult notifyResult = wxPayService.parseOrderNotifyResult(xmlData);
//        OrderInfo orderInfo = orderInfoService.getOne(Wrappers.<OrderInfo>lambdaQuery()
//                .eq(OrderInfo::getOrderNo,notifyResult.getOutTradeNo()));
//        if(orderInfo != null){
//            if(orderInfo.getPaymentPrice().multiply(new BigDecimal(100)).intValue() == notifyResult.getTotalFee()){
//                String timeEnd = notifyResult.getTimeEnd();
//                LocalDateTime paymentTime = LocalDateTimeUtils.parse(timeEnd);
//                orderInfo.setPaymentTime(paymentTime);
//                orderInfo.setTransactionId(notifyResult.getTransactionId());
//                orderInfoService.notifyOrder(orderInfo);
//                return WxPayNotifyResponse.success("成功");
//            }else{
//                return WxPayNotifyResponse.fail("付款金额与订单金额不等");
//            }
//        }else{
//            return WxPayNotifyResponse.fail("无此订单");
//        }
//    }
//}
