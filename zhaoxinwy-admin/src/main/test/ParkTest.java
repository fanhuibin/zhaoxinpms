//
//
//import java.util.concurrent.TimeUnit;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.alibaba.fastjson.JSONObject;
//import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
//import com.google.zxing.client.result.ResultParser;
//import com.zhaoxinms.ZhaoXinWY;
//import com.zhaoxinms.base.util.JsonUtil;
//import com.zhaoxinms.base.util.RedisUtil;
//import com.zhaoxinms.common.core.domain.model.LoginUser;
//import com.zhaoxinms.common.core.redis.RedisCache;
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
//@SpringBootTest(classes=ZhaoXinWY.class)
//@RunWith(SpringRunner.class)
//public class ParkTest {
//
//	@Autowired
//	private ZhenshiMqttCarMachineAdapt zhenshiMqttCarMachineAdapt;
//	@Autowired
//	private IParkPayService parkPayService;
//	@Autowired
//	private IPaymentOnlineOrderService paymentOnlineOrderService;
//	@Autowired
//	private RedisCache redisCache;
//	@Autowired
//	private RedisUtil redisUtil;
//	@Autowired
//	private IParkUseLogService parkUseLogService;
//
//	
//	//测试车辆入场
////	@Test
////	public void enter() {
////	    AlarmInfoPlate plate = new AlarmInfoPlate();
////	    PlateResult plateResult = new PlateResult();
////	    plateResult.setType(0);
////	    plateResult.setLicense("晋K62B82");
////	    
////	    
////	    Result result = new Result();
////	    result.setPlateResult(plateResult);
////	    
////	    plate.setResult(result);
////        plate.setSerialno("40962d12-63d8b8ee");
////	    
////	    ZhenShiPlateResult zhenShiPlateResult = new ZhenShiPlateResult();
////	    zhenShiPlateResult.setAlarmInfoPlate(plate);
////	    
////	    zhenshiMqttCarMachineAdapt.mqttMessageArrived("", JsonUtil.getObjectToString(zhenShiPlateResult));
////	}
////	
////	//另一个停车场入场，车牌冲突情况
////    @Test
////    public void enter2() {
////        AlarmInfoPlate plate = new AlarmInfoPlate();
////        PlateResult plateResult = new PlateResult();
////        plateResult.setType(0);
////        plateResult.setLicense("晋K62B80");
////        
////        
////        Result result = new Result();
////        result.setPlateResult(plateResult);
////        
////        plate.setResult(result);
////        plate.setSerialno("rukou1");
////        
////        ZhenShiPlateResult zhenShiPlateResult = new ZhenShiPlateResult();
////        zhenShiPlateResult.setAlarmInfoPlate(plate);
////        
////        zhenshiMqttCarMachineAdapt.mqttMessageArrived("", JsonUtil.getObjectToString(zhenShiPlateResult));
////    }
////	
////	//测试未知设备
////    @Test
////    public void enterUnknow() {
////        //{"AlarmInfoPlate": {"channel": 0,"deviceName": "","ipaddr": "192.168.1.100","serialno": "5cdc5841-3d038816","heartbeat": 1}}
////        AlarmInfoPlate plate = new AlarmInfoPlate();
////        PlateResult plateResult = new PlateResult();
////        plateResult.setType(0);
////        plateResult.setLicense("晋K62B81");
////        
////        
////        Result result = new Result();
////        result.setPlateResult(plateResult);
////        
////        plate.setResult(result);
////        plate.setSerialno("-63d8b8ee");
////        
////        ZhenShiPlateResult zhenShiPlateResult = new ZhenShiPlateResult();
////        zhenShiPlateResult.setAlarmInfoPlate(plate);
////        
////        zhenshiMqttCarMachineAdapt.mqttMessageArrived("", JsonUtil.getObjectToString(zhenShiPlateResult));
////    }
//	
//	//测试车辆出场
//	@Test
//	public void exit() {
////      AlarmInfoPlate plate = new AlarmInfoPlate();
////      PlateResult plateResult = new PlateResult();
////      plateResult.setType(0);
////      plateResult.setLicense("晋K62B81");
////      
////      
////      Result result = new Result();
////      result.setPlateResult(plateResult);
////      
////      plate.setResult(result);
////      plate.setSerialno("chukou1");
////      
////      ZhenShiPlateResult zhenShiPlateResult = new ZhenShiPlateResult();
////      zhenShiPlateResult.setAlarmInfoPlate(plate);
////      
////      zhenshiMqttCarMachineAdapt.mqttMessageArrived("", JsonUtil.getObjectToString(zhenShiPlateResult));
//	}
//	
////	//离场支付
//	@Test
//    public void exitAndPay() throws IllegalAccessException {
//
////      //指定岗亭码
////      PaymentOrder order = parkPayService.createParkOrder("1273546282689982464");
////      paymentOnlineOrderService.paySuccess(order.getId(), new WxPayOrderNotifyResult());
////      
//	}
//	
////	@Test
////	public void testRedis() {
////	    TempCarFeeResult result = new TempCarFeeResult("123123", 20.0, 20.0, 20.0);
////	    redisUtil.insert("test123", result, RedisUtil.CAHCEHOUR);
////	    Object s =  redisUtil.getString("test123");
////	    System.out.println("------------------------------------");
////	    System.out.println(s.getClass());
////	    redisCache.setCacheObject("test123", result, 15, TimeUnit.MINUTES);
////	    redisCache.getCacheObject("test123");
////	}
//	@Test
//    public void plateNoFee() {
////	    String plateNo = "晋K62B81";
////	    String parkId = "1238793260756689527";
////	    TempCarFeeResult result = parkUseLogService.getCarFee(plateNo, parkId);
////	    System.out.println(result.getPayCharge());
//    }
//	@Test
//	public void prePay() throws IllegalAccessException {
////	    String plateNo = "晋K62B81";
////	    String parkId = "1238793260756689527";
////	    PaymentOrder order = parkPayService.createParkPreOrder(plateNo, parkId);
////	    paymentOnlineOrderService.paySuccess(order.getId(), new WxPayOrderNotifyResult());
//	}
//}
