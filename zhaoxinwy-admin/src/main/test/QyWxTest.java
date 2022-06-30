//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.alibaba.fastjson.JSONObject;
//import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
//import com.google.zxing.client.result.ResultParser;
//import com.jeecg.qywx.api.message.vo.ContentItem;
//import com.jeecg.qywx.api.message.vo.MiniNoticeMessage;
//import com.jeecg.qywx.api.message.vo.MiniprogramNotice;
//import com.zhaoxinms.ZhaoXinWY;
//import com.zhaoxinms.base.util.JsonUtil;
//import com.zhaoxinms.base.util.RedisUtil;
//import com.zhaoxinms.common.core.domain.model.LoginUser;
//import com.zhaoxinms.common.core.redis.RedisCache;
//import com.zhaoxinms.common.utils.SecurityUtils;
//import com.zhaoxinms.park.service.IParkPayService;
//import com.zhaoxinms.park.service.IParkUseLogService;
//import com.zhaoxinms.park.things.model.zhenshi.AlarmInfoPlate;
//import com.zhaoxinms.park.things.model.zhenshi.PlateResult;
//import com.zhaoxinms.park.things.model.zhenshi.Result;
//import com.zhaoxinms.park.things.model.zhenshi.ZhenShiPlateResult;
//import com.zhaoxinms.park.things.zhenshiMqtt.ZhenshiMqttCarMachineAdapt;
//import com.zhaoxinms.park.util.feecalc.TempCarFeeResult;
//import com.zhaoxinms.pay.service.IPaymentOnlineOrderService;
//import com.zhaoxinms.payment.entity.PaymentOrder;
//import com.zhaoxinms.workflowpro.thirdapp.service.IThirdAppService;
//
//@SpringBootTest(classes = ZhaoXinWY.class)
//@RunWith(SpringRunner.class)
//public class QyWxTest {
//
//    @Autowired
//    @Qualifier("QyWxService")
//    private IThirdAppService QyWxService;
//    
////    @Test
////    public void sync() {
////        SecurityUtils.setIsApp();
////        QyWxService.syncLocalDepartmentToThirdApp("");
////    }
////    
//    @Test
//    public void syncUser() {
//        SecurityUtils.setIsApp();
//        QyWxService.syncLocalUserToThirdApp("");
//    }
////    
////    @Test
////    public void sendMessage() {
////        MiniNoticeMessage message = new MiniNoticeMessage();
////        message.setTouser("payee");
////        
////        //显示的内容
////        List<ContentItem> items = new ArrayList<ContentItem>();
////        ContentItem item = new ContentItem();
////        item.setKey("会议地点");
////        item.setValue("123123123");
////        item.setKey("客户姓名");
////        item.setValue("张老三");
////        items.add(item);
////        
////        MiniprogramNotice notice = new MiniprogramNotice();
////        notice.setAppid("wx20607bdf9576d8e0");
////        notice.setTitle("xx发起的维修申请");
////        notice.setDescription("2022年10月10日");
////        notice.setPage("pages/index");
////        notice.setEmphasis_first_item(false);
////        notice.setContent_item(items);
////        
////        
////        message.setMiniprogram_notice(notice);
////        
////        QyWxService.sendMessage(message);
////    }
//}
