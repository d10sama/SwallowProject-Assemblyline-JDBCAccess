package com.example.demo.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@ResponseBody
@Controller
@CrossOrigin
public class sample1_query {

    private static final String template = "select * from Product_Table order by id desc limit 0,1;";

    private static final String PASSOKNG ="select id," +
            "line1_station2_product_bypass," +//0
            "line1_station2_product_OK,"+//1
            "line1_station2_product_NG,"+//2
            "line1_station3_product_bypass," +//3
            "line1_station3_product_OK,"+//4
            "line1_station3_product_NG,"+//5
            "line1_station4_product_bypass," +//6
            "line1_station4_product_OK,"+//7
            "line1_station4_product_NG," +//8
            "line1_station5_product_bypass," +//9
            "line1_station5_product_OK,"+//0
            "line1_station5_product_NG," +//1
            "line1_station7_product_bypass," +//2
            "line1_station7_product_OK,"+//3
            "line1_station7_product_NG," +//4
            "line1_station8_product_bypass," +//5
            "line1_station8_product_OK,"+//6
            "line1_station8_product_NG," +//7
            "line1_station10_product_bypass,"+//8
            "line1_station10_product_OK,"+//9
            "line1_station10_product_NG, "+//0
            "product_total_OK"+//1
    " from Product_Table order by id desc limit 0,1;";//



    private static final String sentence2="select id  from Product_Table order by id desc limit 0,1;";



    final private String Select_line_product_Id="select line_product_Id from Product_Table order by id desc limit 0,1;";
    final private String getOnlineYear="select online_time_year from Product_Table order by id desc limit 0,1;";
    final private String getOnlineMon="select online_time_month from Product_Table order by id desc limit 0,1;";
    final private String getOnlineDay="select online_time_day  from Product_Table order by id desc limit 0,1;";
    final private String getOnlineHour="select online_time_hour from Product_Table order by id desc limit 0,1;";
    final private String getOnlineMin="select online_time_minute from Product_Table order by id desc limit 0,1;";
    final private String getOnlineSec="select online_time_second from Product_Table order by id desc limit 0,1;";
    final private String getOfflineYear="select offline_time_year from Product_Table order by id desc limit 0,1;";
    final private String getOfflineMon="select offline_time_month from Product_Table order by id desc limit 0,1;";
    final private String getOfflineDay="select offline_time_day  from Product_Table order by id desc limit 0,1;";
    final private String getOfflineYer="select offline_time_hour from Product_Table order by id desc limit 0,1;";
    final private String getOfflineHour="select offline_time_minute from Product_Table order by id desc limit 0,1;";
    final private String getOfflineMin="select offline_time_minute from Product_Table order by id desc limit 0,1;";
    final private String getOfflineSec="select offline_time_second from Product_Table order by id desc limit 0,1;";
    final String timeConsume="select time_difference from Product_Table order by id desc limit 0,1;";

    private final String qualified_Screw_station2=
            "select line1_station2_product_NG from Product_Table order by id desc limit 0,50";
    private final String qualified_Screw_station3=
            "select line1_station3_product_NG from Product_Table order by id desc limit 0,50";
    private final String qualified_Press="select " +
            "spc_line1_station040_result_1,"+
            "spc_line1_station040_result_2,"+
            "spc_line1_station040_result_3,"+
            "spc_line1_station040_result_4,"+
            "spc_line1_station040_result_5,"+
            "spc_line1_station040_result_6,"+
            "spc_line1_station040_result_7,"+
            "spc_line1_station040_result_8,"+
            "spc_line1_station040_result_9,"+
            "spc_line1_station040_result_10,"+
            "spc_line1_station070_result_1,"+
            "spc_line1_station070_result_2,"+
            "spc_line1_station070_result_3,"+
            "spc_line1_station070_result_4,"+
            "spc_line1_station070_result_5,"+
            "spc_line1_station070_result_6,"+
            "spc_line1_station070_result_7,"+
            "spc_line1_station070_result_8,"+
            "spc_line1_station070_result_9,"+
            "spc_line1_station070_result_10"+
            " from Product_Table order by id desc limit 0,50;";

    final private String command_qual="update statistics_table set qualified_product_number=%d where id=1;";
    final private String command_fini="update statistics_table set finished_product_number=%d where id=1;";

    //选取设定好的主database
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;
    //用于返回表中对象
    @RequestMapping("/sample1")
    @Scheduled(fixedRate = 500)//定时0.5秒
    @ResponseBody
    public List<Map<String,Object>> contextLoads() {
        int c=0;
        List<Map<String,Object>> result=jdbcTemplate1.queryForList(template);
        int count=1;
        return result;
    }
    @RequestMapping("/PASS_OK_NG")
    public LinkedHashMap<String,String> qualified()
    {
        LinkedHashMap<String,String> ans=new LinkedHashMap<String,String>();
        //allok逻辑：如果工位启用（0）且ok等于1且NG==0则算一个ok
        //要么工位未启用，跳过
        //要么就allok为false
        boolean allok=true;
        boolean StaInuse=false;//当前sta是否inuse
        boolean isOK=false;
        int id=0;
        int count=0;
        List<Map<String,Object>> result=jdbcTemplate1.queryForList(PASSOKNG);

        for(Map<String,Object> map:result)
            for(String s:map.keySet())
            {
                if(id==0) {
                    ans.put(s,map.get(s).toString());
                    continue;
                }
                ans.put(s,map.get(s).toString());
                if(allok)
                    switch (count%3)
                    {
                        case 0:
                            if(Integer.parseInt(map.get(s).toString())==0)
                                StaInuse=true;
                            break;
                        case 1:
                            if(StaInuse&&(Integer.parseInt(map.get(s).toString())==1))
                                isOK=true;
                            else
                                allok=false;
                            break;
                        case 2:if(StaInuse&&isOK&&(Integer.parseInt(map.get(s).toString())==0))
                                {
                                    StaInuse=false;
                                    isOK=false;
                                }else
                                {
                                    allok=false;
                                }
                            break;
                    }
                count++;
            }


        if(allok)
            ans.put("allok","1");
        else
            ans.put("allok","0");

        return ans;
    }

    @RequestMapping(value="/qualified")//,method={RequestMethod.POST,RequestMethod.GET})
    @Scheduled(fixedRate = 500)
    @ResponseBody
    public LinkedHashMap<String, Object> Adapter()//HttpServletRequest request, HttpSession session)
    {
        int qualified=0, completed=0;
        List<Map<String,Object>> result=jdbcTemplate1.queryForList("select product_total_ok from Product_Table");
        int count=1;
        LinkedHashMap<String,Object> temp=new LinkedHashMap<String,Object>();
        for(Map<String,Object> map:result)
            for(String s: map.keySet())
            {
                if((boolean)map.get(s)==true)
                {
                    qualified++;
                }
            }
        completed=jdbcTemplate1.queryForObject("select id from Product_Table order by id desc limit 0,1;",Integer.class);
        //执行sql语句
        jdbcTemplate1.execute(String.format(command_qual,qualified));
        jdbcTemplate1.execute(String.format(command_fini,completed));
        LinkedHashMap<String,Object> outcome=new LinkedHashMap<>();
        outcome.put("qualified",qualified);
        outcome.put("finished",completed);
        outcome.put("qualified_persentage",(double)qualified/completed);
        return outcome;
    }

    //KPI功能:显示单个产品相关数据
    @RequestMapping("/kpi1")
    @Scheduled(fixedRate = 500)
    public List<Map<String,Object>> KPI1()
    {
        LinkedHashMap<String,Object> kpi=new LinkedHashMap<String,Object>();
        String pID=jdbcTemplate1.queryForObject(Select_line_product_Id,String.class);
        int oy=jdbcTemplate1.queryForObject(getOnlineYear,Integer.class);
        int omon=jdbcTemplate1.queryForObject(getOnlineMon,Integer.class);
        int od=jdbcTemplate1.queryForObject(getOnlineDay,Integer.class);
        int oh=jdbcTemplate1.queryForObject(getOnlineHour,Integer.class);
        int omin=jdbcTemplate1.queryForObject(getOnlineMin,Integer.class);
        int osec=jdbcTemplate1.queryForObject(getOnlineSec,Integer.class);
        int ofy=jdbcTemplate1.queryForObject(getOfflineYear,Integer.class);
        int ofmon=jdbcTemplate1.queryForObject(getOfflineMon,Integer.class);
        int ofday=jdbcTemplate1.queryForObject(getOfflineDay,Integer.class);
        int ofhour=jdbcTemplate1.queryForObject(getOfflineHour,Integer.class);
        int ofmin=jdbcTemplate1.queryForObject(getOfflineMin,Integer.class);
        int ofsec=jdbcTemplate1.queryForObject(getOfflineSec,Integer.class);
        int time_conusme=jdbcTemplate1.queryForObject(timeConsume,Integer.class);
        kpi.put("pid",pID);
        kpi.put("oy",oy);
        kpi.put("omon",omon);
        kpi.put("od",od);
        kpi.put("oh",oh);
        kpi.put("omin",omin);
        kpi.put("osec",osec);
        kpi.put("ofy",ofy);
        kpi.put("ofmon",ofmon);
        kpi.put("ofday",ofday);
        kpi.put("ofhour",ofhour);
        kpi.put("ofmin",ofmin);
        kpi.put("ofsec",ofsec);
        kpi.put("time_consume",time_conusme);
        //产品合格率传输
        /*
        !!!!!
        暂定为拧紧合格率=拧紧合格产品数/总个数（040*10+070*10）
        !!!!!
         */
        int qualifiedcount_screw=0;//合格的拧紧数量
        int qualifiedcount_press=0;//合格的压装数量
        List<Map<String,Object>> result1=jdbcTemplate1.queryForList(qualified_Screw_station2);//2号位结果
        List<Map<String,Object>> result2=jdbcTemplate1.queryForList(qualified_Screw_station3);//3号位结果
        List<Map<String,Object>> result3=jdbcTemplate1.queryForList(qualified_Press);//压装结果

        LinkedHashMap<String,Object> press=new LinkedHashMap<>();//压装数据存储器
        LinkedHashMap<String,Object> screw=new LinkedHashMap<>();//拧紧数据存储器
        //读取result1,2,3数据
        for(Map<String,Object> map: result1)
            for(String s: map.keySet())
            {
                if(Boolean.parseBoolean(map.get(s).toString())==true)
                    qualifiedcount_press++;

            }

        for(Map<String,Object> map: result2)
            for(String s: map.keySet())
            {
                if(Boolean.parseBoolean(map.get(s).toString())==true) {
                    qualifiedcount_press++;
                }
            }

        for(Map<String,Object> map: result3)
            for(String s: map.keySet())
            {
                if( Integer.parseInt(map.get(s).toString())!=0) {
                    qualifiedcount_screw++;
                }
            }
        //获取完成产品数
        long id=jdbcTemplate1.queryForObject(sentence2,long.class);
        jdbcTemplate1.execute(String.format(command_fini,id));

        //向map存入数据
        press.put("press_percent",(double)qualifiedcount_press/50);

        screw.put("screw_percent",(double)qualifiedcount_screw/(20*50));
        List<Map<String, Object>> outcome = new LinkedList<>();
        outcome.add(kpi);
        outcome.add(press);
        outcome.add(screw);
        return outcome ;
    }

}