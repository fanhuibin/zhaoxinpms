 package org.jeecg.modules;

import org.jeecg.modules.jmreport.common.util.DesUtils;
import org.jeecg.modules.jmreport.dyndb.JmreportDynamicDbUtil;

public class jmreportGeneratePassword {
     
     /**
      * 生成jimuReport的数据源的密码
      * 表名：jimu_report_data_source
      * @param args
      */
     public static void main(String[] args) {
         String dbPassword = "root";
         String str2 = DesUtils.decode(dbPassword);
         String savedPassword = ("@JimuReport" + str2);
         System.out.println(savedPassword);
     }
}
