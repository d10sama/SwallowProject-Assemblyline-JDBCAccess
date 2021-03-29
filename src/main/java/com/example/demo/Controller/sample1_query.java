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
import java.util.List;
import java.util.Map;

@ResponseBody
@Controller
@CrossOrigin
public class sample1_query {

    private static final String template = "select * from Product_Table order by id desc limit 0,1;";

    private static final String sentence2="select id  from Product_Table order by id desc limit 0,1;";
    LinkedHashMap<String,Object> temp;
    //选取设定好的主database
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;
    //用于返回表中对象
    @RequestMapping("/sample1")
    @Scheduled(fixedRate = 500000)//定时1秒
    @ResponseBody
    public List<Map<String,Object>> contextLoads() {
        int c=0;
        List<Map<String,Object>> result=jdbcTemplate1.queryForList(template);
        int count=1;
        this.temp=new LinkedHashMap<String,Object>();
        for(Map<String,Object> map:result)
            for(String s: map.keySet())
            {
                if(count==1)
                    this.temp.put(s,map.get(s).toString());
                if(count>25&&count<54) {
                    //System.out.println(s+":"+map.get(s).toString()+" "+c);
                    this.temp.put(s,map.get(s).toString());
                }
                count++;
            }
        /*for(int i=0;i<28;i++)
            System.out.println(this.NGOK[i]+" "+i);*/
        return result;
    }
    //用于返回表中条目数
    @RequestMapping("/sp1rows")
    @Scheduled(fixedRate = 100)
    //refresh info_page for 0.2ms,顺便修改完成产品数
    public long rows()
    {
        long id=jdbcTemplate1.queryForObject(sentence2,long.class);
        jdbcTemplate1.execute(String.format(command_fini,id));
        return id;
    }
    @RequestMapping("/PASS_OK_NG")
    public Map<String, Object> qualified()
    {
        return this.temp;
    }
    //接收合格品数，并自我运算完成产品数，再存入数据库中

    final private String command_qual="update statistics_table set qualified_product_number=%d where id=1;";
    final private String command_fini="update statistics_table set finished_product_number=%d where id=1;";
    //接收合格品数，再存入数据库中
    @RequestMapping(value="/get",method={RequestMethod.POST,RequestMethod.GET})
    public void Adapter(HttpServletRequest request, HttpSession session)
    {
        /*
        功能修改方式为
        增加
        String （变量名）=request.getParameter(“线路名工位名");
         */
        int qualified=Integer.parseInt(request.getParameter("qualified"));
        //执行sql语句
        jdbcTemplate1.execute(String.format(command_qual,qualified));
    }
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
    //KPI功能:显示单个产品相关数据
    @RequestMapping("/kpi1")
    @Scheduled(fixedRate = 500)
    public Map<String,Object> KPI1()
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
        return kpi;
    }
    @RequestMapping("/kpi2")
    @Scheduled(fixedRate = 500)
    public Map<String,Object> KPI2()
    {
        


        return null;
    }
}