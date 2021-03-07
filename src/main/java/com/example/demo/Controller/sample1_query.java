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
public class sample1_query {

    private static final String template = "select * from sample1 where id=%d";
    /*
        ssn,ssn1是为了方便展示而创建的，可以递增返回sql条目，若要返回最新一条，\

        需要做两步
        1.第一句private static final String template 的sql语句修改为
        select * from sample1 order by id desc limit 0,1;

        2.将函数contextLoads()修改为
        public List<Map<String,Object>> contextLoads() {
        List<Map<String,Object>> result=jdbcTemplate1.queryForList(template);
        return result;
    }
     */
    private static int ssn=1;//该ssn与执行template组合
    private static int ssn2=1;
    private static final String sentence2="select id  from sample1 order by id desc limit 0,1;";
    //选取设定好的主database
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;
    //用于返回表中对象
    @RequestMapping("/sample1")
    @ResponseBody
    public List<Map<String,Object>> contextLoads() {
        return jdbcTemplate1.queryForList(String.format(template,ssn++));
    }
    //用于返回表中条目数
    @RequestMapping("/sp1rows")
    @Scheduled(fixedRate = 500)
    //refresh info_page for 0.2ms
    public long rows() {
        return jdbcTemplate1.queryForObject(sentence2,long.class);
    }
    //用于返回所需要的的函数以及函数计算后的数值
    @RequestMapping("/function1")
    @ResponseBody
    public Map<String,Object> fun1() {
        //函数算式
        String fun1="y=ax+b,a=2,b=3,result = %f";
        /*
            此处执行的sql语句同样是依赖ssnd的递增数据
            选取的是表中条目的length列
            拓展方式可以是，改为select * ....
            并让前端解析选取要展示的某x列

            若要修改为返回最新的一条则将语句改为改为
             double result1=jdbcTemplate1.queryForObject(String.format("select length from sample1 order by id desc limit 0,1"),double.class);
         */
        double result1=jdbcTemplate1.queryForObject(String.format("select length from sample1 where id=%d",ssn2++),double.class);
        //同样创建map对象用于返回“以字符串fun1”为标签“计算后的数值”
        Map<String,Object> tmp=new HashMap<String, Object>();
        tmp.put(fun1,result1*2+3);
        return tmp;
    }
}