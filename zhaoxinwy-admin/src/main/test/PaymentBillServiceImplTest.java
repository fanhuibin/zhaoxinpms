

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.zhaoxinms.ZhaoXinWY;
import com.zhaoxinms.common.utils.DateUtils;
import com.zhaoxinms.framework.config.MybatisPlusMetaObjectHandler;
import com.zhaoxinms.pay.service.IPaymentOnlineOrderService;
import com.zhaoxinms.payment.entity.PaymentBillEntity;
import com.zhaoxinms.payment.service.PaymentBillCreateService;
import com.zhaoxinms.payment.service.PaymentBillService;
@SpringBootTest(classes=ZhaoXinWY.class)
@RunWith(SpringRunner.class)
public class PaymentBillServiceImplTest {

	@Autowired
	private PaymentBillService paymentBillService;
	@Autowired
	private PaymentBillCreateService paymentBillCreateService;
	@Autowired
	private IPaymentOnlineOrderService paymentOnlineOrderService;
	
	@Test
	public void pay() {
	    try {
            paymentOnlineOrderService.paySuccess("1569692255936098306", new WxPayOrderNotifyResult());
        } catch (IllegalAccessException e) {
             e.printStackTrace();
        }
	}
	
//	@Test
//	public void test() {
//		
//		for(int i = 0; i<1000;i++) {
//			PaymentBillEntity entity = new PaymentBillEntity();
//			entity.setResourceId("test");
//			entity.setResourceName("01-00"+i);
//			entity.setType("house");
//			entity.setFeeItemId("1456546648464388097");
//			entity.setFeeItemName("测试收费项");
//			entity.setFeeUser("李四");
//			entity.setBeginDate(DateUtils.parseDate("2021-11-09"));
//			entity.setEndDate(DateUtils.parseDate("2021-11-23"));
//			entity.setDeadline(DateUtils.parseDate("2021-11-30"));
//			entity.setLastIndex("0");
//			entity.setCurrentIndex("0");
//			entity.setMultiple("1");
//			entity.setLoss("0");
//			entity.setNum("1");
//			entity.setPrice("10");
//			entity.setTotal("1000");
//			entity.setLateFee("0");
//			entity.setDiscount("0");
//			entity.setReceivable("1000");
//			entity.setPayState("1");
//			entity.setPayTime(DateUtils.parseDate("2021-11-09 11:14:43"));
//			entity.setPayLogId("123");
//			entity.setPayLogNo("234");
//			entity.setContractId("e3d6cf4ede3749b3bb16ec287bec8a08");
//			entity.setEnabledMark(1);
//			paymentBillCreateService.save(entity);
//		}
//		
//	}

}
