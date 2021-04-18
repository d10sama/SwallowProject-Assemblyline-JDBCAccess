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
    String sentence1 = "select ID,line1_station2_product_time," +
            "line1_station3_product_time,"+
            "line1_station4_product_time,"+
            "line1_station5_product_time,"+
            "line1_station7_product_time,"+
            "line1_station8_product_time,"+
            "line1_station10_product_time"+
            "  from Product_Table order by id desc limit 0,1;";
    //选取database
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;

    //compare whether new neck is generated
    @RequestMapping("/l1neck")//返回页面
    @Scheduled(fixedRate = 500)//定时0.5秒
    public Map<String, Object> content1() {
        int count=1;
        LinkedHashMap<String,Object> temp=new LinkedHashMap<String,Object>();
        List<Map<String,Object>> result=jdbcTemplate1.queryForList(sentence1);
        for(Map<String, Object> map:result)
            for(String s: map.keySet())
            {

                if(count==1)
                {
                    temp.put(s,map.get(s).toString());
                    count++;
                }
                else{
                    temp.put(s,map.get(s).toString());
                }
            }
        return cmp(temp);
    }
    private LinkedHashMap<String,Object> cmp(LinkedHashMap<String,Object> tmp)
    {
        int idignore=0;
        int value=0;
        String neckname="";
        int tmpvalue;
        String name;
        final String Neck="Neck";
        //读取Map中的数据
        for(String s:tmp.keySet())
        {
            if(idignore!=0) {
                tmpvalue=Integer.parseInt(tmp.get(s).toString());
                //每当最大值小于当前读取的值，则将瓶颈位置替换为当前名字
                if(value<=tmpvalue)
                {
                    value = tmpvalue;
                    neckname=s;
                }
            }
            idignore++;
        }
        //下面这句话证明对于station10以外的位置，charat(14)为"_",
        //System.out.println(neckname+":"+neckname.charAt(14));
        if(neckname.charAt(14)=='_')
            neckname=new String(""+neckname.charAt(13));
        else if(neckname.charAt(14)=='0')
            neckname="10";
        else
            neckname="error";
        tmp.put(Neck,neckname);
        return tmp;
    }

}
