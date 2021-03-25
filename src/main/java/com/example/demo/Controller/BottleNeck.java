package com.example.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    该段代码可以自适应工位数量
    并且能补获数据库错误并且返回特定-1数值
 */

@ResponseBody
@Controller
@CrossOrigin
public class BottleNeck {

    static long OriginNum=0;//记录原始条目数
    long presentNum;//记录当前条目数
    //sql语句
    String sentence1 = "select id  from Product_Table order by id desc limit 0,1;";
    String template1="select  line1_station2_product_time,line1_station3_product_time," +
            "line1_station4_product_time, " +
            "line1_station5_product_time, " +
            "line1_station6_product_time, " +
            "line1_station7_product_time, " +
            "line1_station8_product_time, " +
            "line1_station9_product_time, " +
            "line1_station10_product_time"+
            " from Product_Table where id=%d";
    //选取database
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;

    //compare whether new neck is generated
    @RequestMapping("/l1neck")//返回页面
    @Scheduled(fixedRate = 1000)//定时1秒
    public List<Map<String, Object>> content1() {
        return null;
    }

}
