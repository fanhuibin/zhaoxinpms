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
////    @Test
////    public void syncUser() {
////        SecurityUtils.setIsApp();
////        QyWxService.syncLocalUserToThirdApp("");
////    }
////    
//    @Test
//    public void sendMessage() {
//        MiniNoticeMessage message = new MiniNoticeMessage();
//        message.setTouser("payee");
//        
//        //显示的内容
//        List<ContentItem> items = new ArrayList<ContentItem>();
//        ContentItem item = new ContentItem();
//        item.setKey("客户地址");
//        item.setValue("里兄巷军民小区10号楼9单元201");
//        ContentItem item2 = new ContentItem();
//        item2.setKey("客户姓名");
//        item2.setValue("张老三");
//        
//        ContentItem item3 = new ContentItem();
//        item3.setKey("客户电话");
//        item3.setValue("13651256236");
//        
//        ContentItem item4 = new ContentItem();
//        item4.setKey("客户电话");
//        item4.setValue("13651256236");
//        
//        ContentItem item5 = new ContentItem();
//        item4.setKey("工单进度");
//        item4.setValue("待现场确认");
//        
//        items.add(item5);
//        items.add(item);
//        items.add(item2);
//        items.add(item3);
//        items.add(item4);
//        
//        MiniprogramNotice notice = new MiniprogramNotice();
//        notice.setAppid("wx20607bdf9576d8e0");
//        notice.setTitle("xx发起的维修申请");
//        notice.setDescription("2022年10月10日");
//        notice.setPage("pages/index");
//        notice.setEmphasis_first_item(false);
//        notice.setContent_item(items);
//        
//        
//        message.setMiniprogram_notice(notice);
//        
//        QyWxService.sendMessage(message);
//    }
//}
