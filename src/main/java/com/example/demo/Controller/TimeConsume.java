package com.example.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;


@ResponseBody
@Controller
@CrossOrigin
public class TimeConsume {
    final String query50="select time_difference from Product_Table order by id desc limit 0,50;";
    //时间统计，数组顺序代表自己到自己加十，左闭右开，如time[7]=70（含）~80（不含）
    int times[];

    //SELECT TABLE
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;

    @RequestMapping("/recent50")
    @Scheduled(fixedRate = 1000000)
    public LinkedHashMap<String,Object> timeconsume()
    {
        List<Map<String,Object>> result=jdbcTemplate1.queryForList(query50);
        int count=result.size();
        int tmpvalue;
        int times[]=new int[11];

        for(int i=0;i<11;i++)
            times[i]=0;
        for(Map<String,Object> map:result) {
            for (String s : map.keySet()) {
                tmpvalue = Integer.parseInt(map.get(s).toString());
                times[tmpvalue / 10]++;
            }
        }
        LinkedHashMap<String,Object> fin=new LinkedHashMap<String,Object>();
        for(int i=0;i<11;i++)
        {
            fin.put(String.format("%d",i),times[i]);
        }
        return fin;
    }
}
