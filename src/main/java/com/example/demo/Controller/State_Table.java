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
import java.util.List;
import java.util.Map;

//state_coomunication 在json中0为错误，1为正常
@ResponseBody
@Controller
@CrossOrigin
public class State_Table {
    //sql语句
    final private String command="select * from State_Table limit 0,1;";


    //选取设定好的主database
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;

    @RequestMapping("/states")
    @ResponseBody
    @Scheduled(fixedRate = 5000)
    public LinkedHashMap<String,Object> contentLoad()
    {
        int count=1;

        List<Map<String,Object>> result=jdbcTemplate1.queryForList(command);
        LinkedHashMap<String,Object> tmp=new LinkedHashMap<String,Object>();
        for(Map<String,Object> map:result)
            for(String s:map.keySet())
            {
                if(count<=13)
                {   tmp.put(s,map.get(s).toString());}
                else if(count==14)
                {
                    String tempstring=map.get(s).toString();
                    for(int i=0;i<16;i++)
                    {

                        tmp.put(String.format("sc%d",i+1),tempstring.charAt(i));
                    }
                }
                else
                {
                    String tempstring=map.get(s).toString();
                    for(int i=0;i<16;i++)
                    {
                        tmp.put(String.format("se%d",i+1),tempstring.charAt(i));
                    }
                }
                count++;
            }
        return tmp;
    }


}
