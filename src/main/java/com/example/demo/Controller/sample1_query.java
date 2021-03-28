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
    int preID;
    LinkedHashMap<String,Object> temp;
    //选取设定好的主database
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;
    //用于返回表中对象
    @RequestMapping("/sample1")
    @Scheduled(fixedRate = 500000)//定时1秒
    @ResponseBody
    public List<Map<String,Object>> contextLoads() {
        int c=0;
        List<Map<String,Object>> result=jdbcTemplate1.queryForList(template);
        int count=1;
        this.temp=new LinkedHashMap<String,Object>();
        for(Map<String,Object> map:result)
            for(String s: map.keySet())
            {
                if(count==1)
                    this.temp.put(s,map.get(s).toString());
                if(count>25&&count<54) {
                    //System.out.println(s+":"+map.get(s).toString()+" "+c);
                    this.temp.put(s,map.get(s).toString());
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
        return this.temp;
    }

}