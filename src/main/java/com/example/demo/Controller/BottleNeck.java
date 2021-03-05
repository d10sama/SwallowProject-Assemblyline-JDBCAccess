package com.example.demo.Controller;

import jdk.nashorn.internal.objects.annotations.Property;
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
import java.util.Queue;

/*
    该段代码可以自适应工位数量
    并且能补获数据库错误并且返回特定-1数值
 */

@ResponseBody
@Controller
@CrossOrigin
public class BottleNeck {
    static long OriginNum=0;
    long presentNum;

    String sentence1 = "select id  from line1_sets_time order by id desc limit 0,1;";

    String template1="select id,set1,set2,set3 from line1_sets_time where id=%d";

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;

    //compare whether new neck is generated
    @RequestMapping("/l1neck")
    @Scheduled(fixedRate = 1000)
    public List<Map<String, Object>> content1() {
        int count=0;
        List<Map<String, Object>> tmp = null;
        try {
            this.presentNum = jdbcTemplate1.queryForObject(sentence1, long.class);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("pre="+this.presentNum);
        tmp= jdbcTemplate1.queryForList(String.format(template1, this.presentNum));
        return cmp(tmp);

    }
    String sentence2 = "select id  from line2_sets_time order by id desc limit 0,1;";

    String template2="select id,set1,set2,set3 from line2_sets_time where id=%d";
    @RequestMapping("/l2neck")
    @Scheduled(fixedRate = 1000)
    public List<Map<String, Object>> content2() {
        int count=0;
        List<Map<String, Object>> tmp = null;
        try {
            this.presentNum = jdbcTemplate1.queryForObject(sentence2, long.class);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        //.out.println("pre="+this.presentNum);
        tmp= jdbcTemplate1.queryForList(String.format(template2, this.presentNum));
        return cmp(tmp);
    }
    String sentence3 = "select id  from line3_sets_time order by id desc limit 0,1;";

    String template3="select id,set1,set2,set3 from line3_sets_time where id=%d";
    @RequestMapping("/l3neck")
    @Scheduled(fixedRate = 500)
    public List<Map<String, Object>> content3() {
        int count=0;
        List<Map<String, Object>> tmp = null;
        try {
            this.presentNum = jdbcTemplate1.queryForObject(sentence3, long.class);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        //.out.println("pre="+this.presentNum);
        tmp= jdbcTemplate1.queryForList(String.format(template3, this.presentNum));
        return cmp(tmp);
    }
    List<Map<String,Object>> cmp(List<Map<String,Object>> tmp)
    {
        int[] sets;
        int count=0;
        int value=0;
        for(int i=0;i<tmp.get(0).size();i++)
            count++;
        sets=new int[count];
        count=0;
        try{
            for (Map<String, Object> map : tmp) {
                for (String s : map.keySet()) {
                    sets[count++]=Integer.parseInt(map.get(s).toString());
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        count=0;
        try {
            for (int i = 1; i < sets.length; i++) {
                if (count < sets[i]) {
                    count = sets[i];
                    value = i;
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        Map<String,Object> ADD=new HashMap<String,Object>();
        ADD.put("neck",value);
        tmp.add(ADD);
        return tmp;
    }


}
