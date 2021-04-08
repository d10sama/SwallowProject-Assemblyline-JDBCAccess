package com.example.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@ResponseBody
@Controller
@CrossOrigin
public class divertion {

    private final String torque040070="select id,spc_line1_station040_torque_1," +
            "spc_line1_station040_torque_2,"+
            "spc_line1_station040_torque_3,"+
            "spc_line1_station040_torque_4,"+
            "spc_line1_station040_torque_5,"+
            "spc_line1_station040_torque_6,"+
            "spc_line1_station040_torque_7,"+
            "spc_line1_station040_torque_8,"+
            "spc_line1_station040_torque_9,"+
            "spc_line1_station040_torque_10,"+
            "spc_line1_station070_torque_1," +
            "spc_line1_station070_torque_2,"+
            "spc_line1_station070_torque_3,"+
            "spc_line1_station070_torque_4,"+
            "spc_line1_station070_torque_5,"+
            "spc_line1_station070_torque_6,"+
            "spc_line1_station070_torque_7,"+
            "spc_line1_station070_torque_8,"+
            "spc_line1_station070_torque_9,"+
            "spc_line1_station070_torque_10"+
            " from Product_Table order by id desc limit 0,1;";

    //选取设定好的主database
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;


    @RequestMapping("/screw_diversion")
    @Scheduled(fixedRate = 500)
    public List<LinkedHashMap<String,Object>> SD()
    {
        //float数组初始化
        float[] torques=new float[20];
        int count=0;
        float torque_sum1=0.0f;
        int pos_not_zero1=0;
        float torque_sum2=0.0f;
        int pos_not_zero2=0;
        int id=0;
        //数组存扭矩
        List<Map<String,Object>> result=jdbcTemplate1.queryForList(torque040070);
        for(Map<String,Object> map:result)
            for(String s:map.keySet())
            {
                if(id==0) {
                    id = Integer.parseInt(map.get(s).toString());
                }
                else
                    torques[count++]=Float.parseFloat(map.get(s).toString());
            }
        //torque数据减去预期值
        for(int i=0;i<20;i++) {
            if(i<10&&torques[i]!=0)
            {
                torques[i] -= 325;
                pos_not_zero1++;
                torque_sum1+=torques[i];
            }
            else if(i>9&&torques[i]!=0) {
                torques[i] -= 240;
                pos_not_zero2++;
                torque_sum2+=torques[i];
            }
        }
        List<LinkedHashMap<String,Object>> outcome=new LinkedList<>();
        LinkedHashMap<String,Object> torqueMap=new LinkedHashMap<>();
        torqueMap.put("id",id);
        torqueMap.put("avg_torque_diversion040",(float)torque_sum1/pos_not_zero1);
        torqueMap.put("avg_torque_diversion070",(float)torque_sum1/pos_not_zero1);
        outcome.add(torqueMap);
        return outcome;
    }


}
