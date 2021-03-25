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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ResponseBody
@Controller
@CrossOrigin
public class sample1_query {

    private static final String template = "select * from Product_Table order by id desc limit 0,1;";

    private static final String sentence2="select id  from Product_Table order by id desc limit 0,1;";
    String[] NGOK;
    int preID;
    //选取设定好的主database
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;
    //用于返回表中对象
    @RequestMapping("/PD_Table")
    @Scheduled(fixedRate = 500000)//定时1秒
    @ResponseBody
    public List<Map<String,Object>> contextLoads() {
        int c=0;
        List<Map<String,Object>> result=jdbcTemplate1.queryForList(template);
        int count=1;
        this.NGOK=new String[28];
        for(Map<String,Object> map:result)
            for(String s: map.keySet())
            {
                if(count>25&&count<54) {
                    //System.out.println(s+":"+map.get(s).toString()+" "+c);
                    this.NGOK[c]=map.get(s).toString();
                    c++;
                }
                count++;
            }
        /*for(int i=0;i<28;i++)
            System.out.println(this.NGOK[i]+" "+i);*/
        return result;
    }
    //用于返回表中条目数
    @RequestMapping("/sp1rows")
    @Scheduled(fixedRate = 10)
    //refresh info_page for 0.2ms
    public long rows() {
        return jdbcTemplate1.queryForObject(sentence2,long.class);
    }
    @RequestMapping("/PASS_OK_NG")
    public Map<String, Object> qualified()
    {
        Map<String,Object> temp=new LinkedHashMap<String,Object>();
        //若all_ok等于true则跳过判断
        if(this.NGOK[18]=="true")
        {
            temp.put("allok",1);
        }else {
            temp.put("allok",0);
            for (int i = 0; i < 9; i++) {
                //否则判断(PASS为真且NG为真）的工位
                if (this.NGOK[i] == "true" && this.NGOK[i + 9] == "true") {
                    temp.put(String.format("line1_station%d_product_OK", i), 1);
                }
            }
            for (int i = 0; i < 9; i++)
            {
                if (this.NGOK[i] == "true" && this.NGOK[i + 19] == "false") {
                    temp.put(String.format("line1_station%d_product_NG", i), 0);
                }
            }
        }
        return temp;
    }

}