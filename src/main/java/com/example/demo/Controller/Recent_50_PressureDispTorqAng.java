package com.example.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@ResponseBody
@Controller
@CrossOrigin
public class Recent_50_PressureDispTorqAng {
    //选取设定好的主database
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;

    LinkedList<LinkedList<Integer>> storage=new LinkedList<LinkedList<Integer>>();

    //8*50
    private static final String Punion= "select spc_line1_station010_pressdata_1 from Product_Table union select \n" +
            "    spc_line1_station010_pressdata_2 from Product_Table union select \n" +
            "    spc_line1_station010_pressdata_3 from Product_Table union select \n" +
            "    spc_line1_station010_pressdata_4 from Product_Table union select \n" +
            "    spc_line1_station020_pressdata_1 from Product_Table union select \n" +
            "    spc_line1_station020_pressdata_2 from Product_Table union select \n" +
            "    spc_line1_station020_pressdata_3 from Product_Table union select \n" +
            "    spc_line1_station020_pressdata_4 from Product_Table\n" +
            "            order by spc_line1_station010_pressdata_1 desc limit 0,400;";
    //8*50
    private static final String Dunion= "select \n" +
            "    spc_line1_station010_displacementdata_1 from Product_Table union select \n" +
            "    spc_line1_station010_displacementdata_2 from Product_Table union select \n" +
            "    spc_line1_station010_displacementdata_3 from Product_Table union select \n" +
            "    spc_line1_station010_displacementdata_4 from Product_Table union select \n" +
            "    spc_line1_station020_displacementdata_1 from Product_Table union select \n" +
            "    spc_line1_station020_displacementdata_2 from Product_Table union select \n" +
            "    spc_line1_station020_displacementdata_3 from Product_Table union select \n" +
            "    spc_line1_station020_displacementdata_4 from Product_Table\n" +
            "            order by spc_line1_station010_displacementdata_1 desc limit 0,400;";
    //20*50
    private static final String Tunion=
            "select spc_line1_station040_torque_1  from Product_Table union select \n" +
            "    spc_line1_station040_torque_2  from Product_Table union select \n" +
            "    spc_line1_station040_torque_3  from Product_Table union select \n" +
            "    spc_line1_station040_torque_4  from Product_Table union select \n" +
            "    spc_line1_station040_torque_5  from Product_Table union select \n" +
            "    spc_line1_station040_torque_6  from Product_Table union select \n" +
            "    spc_line1_station040_torque_7  from Product_Table union select \n" +
            "    spc_line1_station040_torque_8  from Product_Table union select \n" +
            "    spc_line1_station040_torque_9  from Product_Table union select \n" +
            "    spc_line1_station040_torque_10  from Product_Table union select \n" +
            "    spc_line1_station070_torque_1  from Product_Table union select \n" +
            "    spc_line1_station070_torque_2  from Product_Table union select \n" +
            "    spc_line1_station070_torque_3  from Product_Table union select \n" +
            "    spc_line1_station070_torque_4  from Product_Table union select \n" +
            "    spc_line1_station070_torque_5  from Product_Table union select \n" +
            "    spc_line1_station070_torque_6  from Product_Table union select \n" +
            "    spc_line1_station070_torque_7  from Product_Table union select \n" +
            "    spc_line1_station070_torque_8  from Product_Table union select \n" +
            "    spc_line1_station070_torque_9  from Product_Table union select \n" +
            "    spc_line1_station070_torque_10  from Product_Table\n" +
            "    order by spc_line1_station040_torque_1 desc limit 0,1000;";
    //20*50
    private static final String Aunion=
            "select spc_line1_station040_angle_1  from Product_Table union select \n" +
            "    spc_line1_station040_angle_2  from Product_Table union select \n" +
            "    spc_line1_station040_angle_3  from Product_Table union select \n" +
            "    spc_line1_station040_angle_4  from Product_Table union select \n" +
            "    spc_line1_station040_angle_5  from Product_Table union select \n" +
            "    spc_line1_station040_angle_6  from Product_Table union select \n" +
            "    spc_line1_station040_angle_7  from Product_Table union select \n" +
            "    spc_line1_station040_angle_8  from Product_Table union select \n" +
            "    spc_line1_station040_angle_9  from Product_Table union select \n" +
            "    spc_line1_station040_tangle10  from Product_Table union select \n" +
            "    spc_line1_station070_angle_1  from Product_Table union select \n" +
            "    spc_line1_station070_angle_2  from Product_Table union select \n" +
            "    spc_line1_station070_angle_3  from Product_Table union select \n" +
            "    spc_line1_station070_angle_4  from Product_Table union select \n" +
            "    spc_line1_station070_angle_5  from Product_Table union select \n" +
            "    spc_line1_station070_angle_6  from Product_Table union select \n" +
            "    spc_line1_station070_angle_7  from Product_Table union select \n" +
            "    spc_line1_station070_angle_8  from Product_Table union select \n" +
            "    spc_line1_station070_angle_9  from Product_Table union select \n" +
            "    spc_line1_station070_angle_10  from Product_Table\n" +
            "    order by spc_line1_station040_torque_1 desc limit 0,1000;";

    @RequestMapping("/PDTA")
    @Scheduled(fixedRate = 500)
    public LinkedHashMap<String,Object> pdta()
    {

        LinkedHashMap<String,Object> ans=new LinkedHashMap<>();
        //P
        LinkedList<Float> p=new LinkedList<>();
        //D
        LinkedList<Float> d=new LinkedList<>();
        //T
        LinkedList<Float> t=new LinkedList<>();
        //A
        LinkedList<Float> a=new LinkedList<>();
        float tmpval=0f;
        int ColumnsPTR=0;
        int count=0;


        //存入结果
        List<Map<String,Object>> resultP=jdbcTemplate1.queryForList(Punion);
        float Pup,Pdown;
        int Ptopcount=0;
        List<Map<String,Object>> resultD=jdbcTemplate1.queryForList(Dunion);
        float Dup,Ddown;
        int Dtopcount=0;
        //List<Map<String,Object>> resultT=jdbcTemplate1.queryForList(Tunion);
        float Tup,Tdown;
        int Ttopcount=0;
        //List<Map<String,Object>> resultA=jdbcTemplate1.queryForList(Aunion);
        float Aup,Adown;
        int Atopcount=0;
        //解析结果，对于8*50我固定以（上限-下限）/8并且统计每段的数量值

        for(Map<String,Object> map:resultP)
            for(String s:map.keySet())
            {
                if(Float.parseFloat(map.get(s).toString())!=0f)
                {
                    tmpval=Float.parseFloat(map.get(s).toString());
                    //System.out.println(s+map.get(s).toString());
                    //最大值记录
                    if(Ptopcount==0) {
                        Pup=tmpval;
                        Ptopcount++;
                    }
                }//当前值等于0，则上次记录的tmpval为最小值，故记录后即可退出
                else {
                    Pdown = tmpval;
                    break;
                }
            }
        for(Map<String,Object> map:resultP)
            for(String s:map.keySet())
            {

            }
        //解析结果，对于8*50我固定以差值200为一个饼图的间距（既（上限-下限）/8并且统计每段的数量值）
        for(Map<String,Object> map:resultP)
            for(String s:map.keySet())
            {

            }
        for(Map<String,Object> map:resultP)
            for(String s:map.keySet())
            {
                //System.out.println(s+map.get(s).toString());
            }





        return ans;
    }


}
