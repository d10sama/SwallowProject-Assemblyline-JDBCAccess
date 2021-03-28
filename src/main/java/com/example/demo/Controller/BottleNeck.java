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
    String sentence1 = "select *  from Product_Table order by id desc limit 0,1;";
    //选取database
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;

    //compare whether new neck is generated
    @RequestMapping("/l1neck")//返回页面
    @Scheduled(fixedRate = 100000)//定时1秒
    public Map<String, Object> content1() {
        int count=1;
        LinkedHashMap<String,Object> temp=new LinkedHashMap<String,Object>();
        List<Map<String,Object>> result=jdbcTemplate1.queryForList(sentence1);
        for(Map<String, Object> map:result)
            for(String s: map.keySet())
            {
                if(count==1)
                {temp.put(s,map.get(s).toString());}
                if(count>=16&&count<=22) {
                    temp.put(s,map.get(s).toString());
                }
                count+=1;
            }
        return cmp(temp);
    }
    private LinkedHashMap<String,Object> cmp(LinkedHashMap<String,Object> tmp)
    {
        int count=2;
        int idignore=0;
        int value=0;
        int tmpvalue;
        final String Neck="Neck";
        for(String s:tmp.keySet())
        {
            if(idignore!=0) {
                tmpvalue=Integer.parseInt(tmp.get(s).toString());
                if(value<tmpvalue)
                {
                    value = tmpvalue;
                    count++;
                    System.out.println(s+" "+"value="+value+" count="+count);
                }
            }
            idignore++;
        }

        switch (count)
        {
            case 2:
            case 3:
            case 4:
            case 5:
                break;
            case 6:
            case 7:
                count++;
                break;
            case 8:
                count+=2;
                break;
        }
        tmp.put(Neck,count);
        return tmp;
    }

}
