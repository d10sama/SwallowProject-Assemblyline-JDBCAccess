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
import java.util.LinkedList;
import java.util.Map;
import java.util.List;


@ResponseBody
@Controller
@CrossOrigin
public class TimeConsume {
    final String query50="select time_difference from (select time_difference from Product_Table order by id desc limit 0,50) as tmp order by time_difference desc;";
    //时间统计，数组顺序代表自己到自己加十，左闭右开，如time[7]=70（含）~80（不含）
    int times[];

    //SELECT TABLE
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;

    @RequestMapping("/recent50")
    @Scheduled(fixedRate = 500)
    public LinkedList<Integer> timeconsume()
    {
        List<Map<String,Object>> result=jdbcTemplate1.queryForList(query50);
        int tmpval;
        int ConUp=0,ConDown=0,isUp0=0;
        int times[]= {0,0,0,0,0,0,0,0,0,0};//数组容量为10，十个数据段
        LinkedList<Integer> consume=new LinkedList<>();

        //解析结果，对于8*50我固定以（上限-下限）/8并且统计每段的数量值
        for(Map<String,Object> map:result)
            for(String s:map.keySet())
            {
                if(Float.parseFloat(map.get(s).toString())!=0f)
                {
                    tmpval=Integer.parseInt(map.get(s).toString());
                    //System.out.println(s+map.get(s).toString());
                    consume.add(tmpval);
                    //最大值记录
                    if(isUp0==0) {
                        ConUp=tmpval;
                        isUp0++;
                    }
                }//当前值等于0，则上次记录的tmpval为最小值，故记录后即可退出

            }

        int ConPartition=(ConUp-ConDown)/8;


        LinkedList<Integer> fin=new LinkedList<>();
        Integer[] ConCOUNT={0,0,0,0,0,0,0,0};
        int Contopcount=0;



        for(int i=0;i<7;i++) {
            //System.out.println("P"+p.get(Ptopcount)+"small "+(Pup-Ppartition*(i+1))+"big "+(Pup-Ppartition*i));
            while (consume.get(Contopcount) >= (ConUp - ConPartition * (i + 1)) && consume.get(Contopcount) <= (ConUp - ConPartition * i)) {
                ConCOUNT[i]++;
                Contopcount++;
                if (Contopcount >= consume.size())
                    break;
            }
        }
        Integer UpperBound=0,LowerBound=0;


        for(int i=0;i<7;i++) {
            UpperBound=(ConUp - ConPartition * (i + 1));
            LowerBound=(ConUp - ConPartition * i);
            fin.add(UpperBound);
            fin.add(LowerBound);
            fin.add(ConCOUNT[i]);
        }

        return fin;
    }
}
