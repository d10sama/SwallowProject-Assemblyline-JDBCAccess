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
    //从数据库中读取
    private final String torque040070="select id," +
            "line_product_Id,"+
            "spc_line1_station040_torque_1," +
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
            "spc_line1_station070_torque_10,"+
            "spc_line1_station010_pressdata_1,"+
            "spc_line1_station010_displacementdata_1,"+
            "spc_line1_station010_pressdata_2,"+
            "spc_line1_station010_displacementdata_2,"+
            "spc_line1_station010_pressdata_3,"+
            "spc_line1_station010_displacementdata_3,"+
            "spc_line1_station010_pressdata_4,"+
            "spc_line1_station010_displacementdata_4,"+
            "spc_line1_station020_pressdata_1,"+
            "spc_line1_station020_displacementdata_1,"+
            "spc_line1_station020_pressdata_2,"+
            "spc_line1_station020_displacementdata_2,"+
            "spc_line1_station020_pressdata_3,"+
            "spc_line1_station020_displacementdata_3,"+
            "spc_line1_station020_pressdata_4,"+
            "spc_line1_station020_displacementdata_4"+
            " from Product_Table order by id desc limit 0,1;";
    //期望值记录
    int[] TorqueExpectation={240,325};
    int[] PressureExpectation={0,0,0};
    /*
        期望值运算，要减去的数值取决于productid编号，只有040诱导轮是减去325，其他都是240！
     */
    //选取设定好的主database
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;

    /*
    伺服压机工位的压力值偏移量、
    伺服压机工位的位移值、
    液压机工位的压力值偏移量、
    液压机工位的位移值；已获取

    其中压力值有给定的预期值，
    和扭矩一样，以一个数组存储，内容是：
        产品类型号+不同工位的预期压力值；


        !!!!!!!!!!!!!!!!!!!
        位移参数没有预期值
        !!!!!!!!!!!!!!!!!!!
     */

    /*
        本段函数可能的问题在于，我用通用查找来寻找所需的列，既从select *中找对应的列
        可能在不同的数据库中，顺序不一样
        因此一旦出现问题建议将查询语句改为对应的列即可

        ！！！！！！
        更为推荐的方法，在数据库中创建视图（view），这样效果更好


     */

    @RequestMapping("/diversion")
    @Scheduled(fixedRate = 500)
    public List<LinkedHashMap<String,Object>> SD()
    {
        //变量创建
        float[] torques=new float[20];

        float[] sta010Press=new float[4];
        int s1pcount=0;
        float[] sta020Press=new float[4];
        int s2pcount=0;
        float[] sta010Disp=new float[4];
        int s1dcount=0;
        float[] sta020Disp=new float[4];
        int s2dcount=0;

        int count=0;
        int ProductIdCount=0;
        float torque_sum1=0.0f;
        int pos_not_zero1=0;
        float torque_sum2=0.0f;
        int pos_not_zero2=0;
        String productid="#";
        int id=0;
        //数组存扭矩
        List<Map<String,Object>> result=jdbcTemplate1.queryForList(torque040070);
        for(Map<String,Object> map:result)
            for(String s:map.keySet())
            {
                if(id==0) {
                    id = Integer.parseInt(map.get(s).toString());
                }
                else if(id!=0&&ProductIdCount==0)
                {
                    productid=map.get(s).toString();
                    ProductIdCount++;
                }else if(count<20){
                    torques[count++] = Float.parseFloat(map.get(s).toString());
                }else {
                    //20~27是010，20，22，24，26是压装数值，21，23，25，27是位移数值
                    if (count < 28 && ((count & 1) == 0))
                    {
                        sta010Press[s1pcount++]=Float.parseFloat(map.get(s).toString());
                        //
                    }
                    else if(count < 28 && ((count & 1)== 1))
                    {
                        sta010Disp[s1dcount++]=Float.parseFloat(map.get(s).toString());
                        //
                    }
                    //28及以上是020，28，30，32，34是压装数值，29，31，33，35是位移数值
                    else if(count > 27 && ((count & 1) == 0))
                    {
                        sta020Press[s2pcount++]=Float.parseFloat(map.get(s).toString());
                        //
                    }
                    else if(count > 27 && ((count & 1) == 1))
                    {
                        sta020Disp[s2dcount++]=Float.parseFloat(map.get(s).toString());
                        //
                    }
                    count++;
                }
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
        //press减去预期值后平均
        /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //注意事項：
        //pressの設定値はまだ確認されていなっかだ、
        //予想の設定値は１１４５１４と思います。
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
        float sta010PressSum=0f,sta020PressSum=0f;
        int pos_not_zero010=0,pos_not_zero020=0;
        for(int i=0;i<3;i++)
        {
            if(sta010Press[i]!=0)
            {
                sta010Press[i] -= 114514;//!!!!!!!!!!!!!!!!!!!!!!!!!!!
                pos_not_zero010++;
                sta010PressSum+=sta010Press[i];
            }
            if(sta020Press[i]!=0) {
                sta020Press[i] -= 114514;//!!!!!!!!!!!!!!!!!!!!!!!!!!!
                pos_not_zero020++;
                sta020PressSum+=sta020Press[i];
            }
        }
        //位移平均值
        float sta010sum=0f,sta020sum=0f;
        for(int i=0;i<4;i++) {
            sta010sum+=sta010Disp[i];
            sta020sum+=sta020Disp[i];
        }
        List<LinkedHashMap<String,Object>> outcome=new LinkedList<>();
        LinkedHashMap<String,Object> torqueMap=new LinkedHashMap<>();
        LinkedHashMap<String,Object> Pre_DisMap=new LinkedHashMap<>();

        torqueMap.put("id",id);
        torqueMap.put("productid",productid);
        torqueMap.put("avg_torque_diversion040",(float)torque_sum1/pos_not_zero1);
        torqueMap.put("avg_torque_diversion070",(float)torque_sum1/pos_not_zero1);

        Pre_DisMap.put("avg_010_dis",(float)sta010sum/4);
        Pre_DisMap.put("avg_020_dis",(float)sta020sum/4);
        Pre_DisMap.put("avg_010_press",(float)sta010PressSum/pos_not_zero010);
        Pre_DisMap.put("avg_020_press",(float)sta020PressSum/pos_not_zero020);


        outcome.add(torqueMap);
        outcome.add(Pre_DisMap);
        return outcome;
    }


}
