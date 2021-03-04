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
    static int id=0;
    int sets[];//set to record and count to count sets;

    String sentence1 = "select id  from line1_sets_time order by id desc limit 0,1;";

    String template1="select set1,set2,set3 from line1_sets_time where id=%d";

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;

    //compare whether new neck is generated
    @RequestMapping("/l1neck")
    @Scheduled(fixedRate = 1000)
    public int content1() {
        int count=0;
        try {
            this.presentNum = jdbcTemplate1.queryForObject(sentence1, long.class);
        }catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        //System.out.println("pre="+this.presentNum);
        List<Map<String, Object>> tmp = jdbcTemplate1.queryForList(String.format(template1, this.presentNum));
        Map<String, Object> result=tmp.get(0);
        this.sets=new int[result.size()];
        for (String s : result.keySet()) {
                this.sets[count++]=Integer.parseInt(result.get(s).toString());
        }
        return cmp(this.sets);

    }
    String sentence2 = "select id  from line2_sets_time order by id desc limit 0,1;";

    String template2="select set1,set2,set3 from line2_sets_time where id=%d";
    @RequestMapping("/l2neck")
    @Scheduled(fixedRate = 1000)
    public int content2() {
        int count=0;
        try {
            this.presentNum = jdbcTemplate1.queryForObject(sentence2, long.class);
        }catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        //System.out.println("pre="+this.presentNum);
        List<Map<String, Object>> tmp = jdbcTemplate1.queryForList(String.format(template2, this.presentNum));
        Map<String, Object> result=tmp.get(0);
        this.sets=new int[result.size()];
        for (String s : result.keySet()) {
            this.sets[count++]=Integer.parseInt(result.get(s).toString());
        }
        return cmp(this.sets);

    }
    String sentence3 = "select id  from line3_sets_time order by id desc limit 0,1;";

    String template3="select set1,set2,set3 from line3_sets_time where id=%d";
    @RequestMapping("/l3neck")
    @Scheduled(fixedRate = 500)
    public int content3() {
        int count=0;
        try {
            this.presentNum = jdbcTemplate1.queryForObject(sentence3, long.class);
        }catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        //.out.println("pre="+this.presentNum);
        List<Map<String, Object>> tmp = jdbcTemplate1.queryForList(String.format(template3, this.presentNum));
        Map<String, Object> result=tmp.get(0);
        this.sets=new int[result.size()];
        for (String s : result.keySet()) {
            this.sets[count++]=Integer.parseInt(result.get(s).toString());
        }
        return cmp(this.sets);

    }

    private int cmp(int[] sets)
    {
        int tmp=0;
        int count=0;
        for(int i=0;i<sets.length;i++)
        {
            if(tmp<sets[i])
            {
                tmp=sets[i];
                count=i;
            }
        }
        return count+1;
    }
}
