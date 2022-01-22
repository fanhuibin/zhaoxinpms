package com.zhaoxinms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.zhaoxinms"},
    exclude = {DataSourceAutoConfiguration.class, org.activiti.spring.boot.SecurityAutoConfiguration.class,})
@EnableTransactionManagement
@MapperScan(basePackages = {"com.zhaoxinms.mapper", "com.zhaoxinms.**.mapper"})
public class ZhaoXinWY {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(ZhaoXinWY.class, args);
        System.out.println("888888888888 8b        d8        88888888ba  88b           d88  ad88888ba   \r\n"
            + "         ,88  Y8,    ,8P         88      \"8b 888b         d888 d8\"     \"8b  \r\n"
            + "       ,88\"    `8b  d8'          88      ,8P 88`8b       d8'88 Y8,          \r\n"
            + "     ,88\"        Y88P            88aaaaaa8P' 88 `8b     d8' 88 `Y8aaaaa,    \r\n"
            + "   ,88\"          d88b   aaaaaaaa 88\"\"\"\"\"\"'   88  `8b   d8'  88   `\"\"\"\"\"8b,  \r\n"
            + " ,88\"          ,8P  Y8, \"\"\"\"\"\"\"\" 88          88   `8b d8'   88         `8b  \r\n"
            + "88\"           d8'    `8b         88          88    `888'    88 Y8a     a8P  \r\n"
            + "888888888888 8P        Y8        88          88     `8'     88  \"Y88888P\"   ");
    }
}
