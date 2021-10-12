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
            "select spc_line1_station040_angle_1  from Product_Table union select\n" +
                    "spc_line1_station040_angle_2  from Product_Table union select\n" +
                    "            spc_line1_station040_angle_3  from Product_Table union select\n" +
                    "            spc_line1_station040_angle_4  from Product_Table union select\n" +
                    "            spc_line1_station040_angle_5  from Product_Table union select\n" +
                    "            spc_line1_station040_angle_6  from Product_Table union select\n" +
                    "            spc_line1_station040_angle_7  from Product_Table union select\n" +
                    "            spc_line1_station040_angle_8  from Product_Table union select\n" +
                    "            spc_line1_station040_angle_9  from Product_Table union select\n" +
                    "            spc_line1_station040_angle_10  from Product_Table union select\n" +
                    "            spc_line1_station070_angle_1  from Product_Table union select\n" +
                    "            spc_line1_station070_angle_2  from Product_Table union select\n" +
                    "            spc_line1_station070_angle_3  from Product_Table union select\n" +
                    "            spc_line1_station070_angle_4  from Product_Table union select\n" +
                    "            spc_line1_station070_angle_5  from Product_Table union select\n" +
                    "            spc_line1_station070_angle_6  from Product_Table union select\n" +
                    "            spc_line1_station070_angle_7  from Product_Table union select\n" +
                    "            spc_line1_station070_angle_8  from Product_Table union select\n" +
                    "            spc_line1_station070_angle_9  from Product_Table union select\n" +
                    "            spc_line1_station070_angle_10  from Product_Table\n" +
                    "            order by spc_line1_station040_angle_1 desc limit 0,1000;";

    @RequestMapping("/PDTA")
    @Scheduled(fixedRate = 500)
    public LinkedList<String> pdta()
    {

        LinkedList<String> ans=new LinkedList<>();
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
        float Pup=0f,Pdown=0f;
        int Ptopcount=0;
        List<Map<String,Object>> resultD=jdbcTemplate1.queryForList(Dunion);
        float Dup=0f,Ddown=0f;
        int Dtopcount=0;
        List<Map<String,Object>> resultT=jdbcTemplate1.queryForList(Tunion);
        float Tup=0f,Tdown=0f;
        int Ttopcount=0;
//         List<Map<String,Object>> resultA=jdbcTemplate1.queryForList(Aunion);
//         float Aup=0f,Adown=0f;
//         int Atopcount=0;
        //解析结果，对于8*50我固定以（上限-下限）/8并且统计每段的数量值

        for(Map<String,Object> map:resultP)
            for(String s:map.keySet())
            {
                if(Float.parseFloat(map.get(s).toString())!=0f)
                {
                    tmpval=Float.parseFloat(map.get(s).toString());
                    p.add(tmpval);
                    //最大值记录
                    if(Ptopcount==0) {
                        Pup=tmpval;
                        Ptopcount++;
                    }
                }//当前值等于0，则上次记录的tmpval为最小值，故记录后即可退出

            }
        for(Map<String,Object> map:resultD)
            for(String s:map.keySet())
            {
                if(Float.parseFloat(map.get(s).toString())!=0f)
                {
                    tmpval=Float.parseFloat(map.get(s).toString());
                    d.add(tmpval);
                    //最大值记录
                    if(Dtopcount==0) {
                        Dup=tmpval;
                        Dtopcount++;
                    }
                }//当前值等于0，则上次记录的tmpval为最小值，故记录后即可退出

            }
        //解析结果，对于8*50我固定以差值200为一个饼图的间距（既（上限-下限）/8并且统计每段的数量值）
        for(Map<String,Object> map:resultT)
            for(String s:map.keySet())
            {
                if(Float.parseFloat(map.get(s).toString())!=0f)
                {
                    tmpval=Float.parseFloat(map.get(s).toString());
                    t.add(tmpval);
                    //最大值记录
                    if(Ttopcount==0) {
                        Tup=tmpval;
                        Ttopcount++;
                    }
                }
            }
//         for(Map<String,Object> map:resultA)
//             for(String s:map.keySet())
//             {
//                 if(Float.parseFloat(map.get(s).toString())!=0f)
//                 {
//                     tmpval=Float.parseFloat(map.get(s).toString());
//                     a.add(tmpval);
//                     //最大值记录
//                     if(Atopcount==0) {
//                         Aup=tmpval;
//                         Atopcount++;
//                     }
//                 }
//             }
            //将各个最大值分区
            float Ppartition=(Pup-Pdown)/8,Dpartition=(Dup-Ddown)/8,Tpartition=(Tup-Tdown)/8,Apartition=(Aup-Adown)/8;
            int[] PCOUNT={0,0,0,0,0,0,0,0};
            int[] DCOUNT={0,0,0,0,0,0,0,0};
            int[] TCOUNT={0,0,0,0,0,0,0,0};
           // int[] ACOUNT={0,0,0,0,0,0,0,0};
            Ptopcount=0;
            Dtopcount=0;
            Ttopcount=0;
           // Atopcount=0;
            //区间内数量统计
            for(int i=0;i<7;i++) {
                if(Ptopcount>=p.size()||Dtopcount>=d.size()||Ttopcount>=t.size()||Atopcount>=a.size())
                    break;
                while (p.get(Ptopcount) >= (Pup - Ppartition * (i + 1)) && p.get(Ptopcount) <= (Pup - Ppartition * i))
                {
                    PCOUNT[i]++;
                    Ptopcount++;
                    if (Ptopcount >= p.size())
                        break;
                }
                while(d.get(Dtopcount)>=(Dup-Dpartition*(i+1))&&d.get(Dtopcount)<=(Dup-Dpartition*i))
                {
                    DCOUNT[i]++;
                    Dtopcount++;
                    if(Dtopcount>=d.size())
                        break;
                }
                while(t.get(Ttopcount)>=(Tup-Tpartition*(i+1))&&t.get(Ttopcount)<=(Tup-Tpartition*i))
                {
                    TCOUNT[i]++;
                    Ttopcount++;
                    if(Ttopcount>=t.size())
                        break;
                }
//                 while(a.get(Atopcount)>=(Aup-Apartition*(i+1))&&a.get(Atopcount)<=(Aup-Apartition*i))
//                 {
//                     ACOUNT[i]++;
//                     Atopcount++;
//                     if(Atopcount>=a.size())
//                         break;
//                 }
            }
            Float p1,p2,d1,d2,t1,t2,a1,a2;
            Integer p3,t3,d3,a3;
            LinkedList<String> result=new LinkedList<>();

            //向ans中循环插入结果
            for(int i=0;i<7;i++)
            {
                p1=(Pup-Ppartition*i);
                p2=(Pup-Ppartition*(i+1));
                p3=PCOUNT[i];
                result.add("p");
                result.add(p1.toString());
                result.add(p2.toString());
                result.add(p3.toString());

                d1=(Dup-Dpartition*i);
                d2=(Dup-Dpartition*(i+1));
                d3=DCOUNT[i];
                result.add("d");
                result.add(d1.toString());
                result.add(d2.toString());
                result.add(d3.toString());

                t1=(Tup-Tpartition*i);
                t2=(Tup-Tpartition*(i+1));
                t3=TCOUNT[i];
                result.add("t");
                result.add(t1.toString());
                result.add(t2.toString());
                result.add(t3.toString());

//                 a1=(Aup-Apartition*i);
//                 a2=(Aup-Apartition*(i+1));
//                 a3=ACOUNT[i];
//                 result.add("a");
//                 result.add(a1.toString());
//                 result.add(a2.toString());
//                 result.add(a3.toString());
            }
        return result;
    }
}
