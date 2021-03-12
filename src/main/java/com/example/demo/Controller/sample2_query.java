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

@ResponseBody
@Controller
@CrossOrigin
public class sample2_query {
    private static final String template = "select * from sample2 order by id desc limit 0,1;";
    private static int ssn=1;
    private static int ssn2=1;
    private static final String sentence2="select id  from sample2 order by id desc limit 0,1;";

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;
    @RequestMapping("/sample2")
    @ResponseBody
    public List<Map<String,Object>> contextLoads() {
        List<Map<String,Object>> result=jdbcTemplate1.queryForList(template);
        return result;
    }
    @RequestMapping("/sp2rows")
    @Scheduled(fixedRate = 500)
    //refresh info_page for 0.2ms
    public long rows() {
        return jdbcTemplate1.queryForObject(sentence2,long.class);
    }
    @RequestMapping("/function2")
    @ResponseBody
    public Map<String,Object> fun1() {
        String fun1="y=ax+b,a=2,b=3,result = %f";
        double result1=jdbcTemplate1.queryForObject(String.format("select length from sample2 where id=%d",ssn2++),double.class);
        Map<String,Object> tmp=new HashMap<String, Object>();
        tmp.put(fun1,result1*2+3);
        return tmp;
    }
}