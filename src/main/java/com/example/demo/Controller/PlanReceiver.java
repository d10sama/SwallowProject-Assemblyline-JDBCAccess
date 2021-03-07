package com.example.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@ResponseBody
@Controller
@CrossOrigin
public class PlanReceiver {
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;
    private static final String command="insert into process_plan (line1set1,line1set2,line1set3,line2,line3,startdate,enddate) values (%d,%d,%d,%d,%d,%d,%d);";
    @RequestMapping(value="/get",method={RequestMethod.POST,RequestMethod.GET})
    public String Adapter(HttpServletRequest request, HttpSession session)
    {
        /*
        功能修改方式为
        增加
        String （变量名）=request.getParameter(“线路名工位名");
         */
        int line1set1=Integer.parseInt(request.getParameter("line1set1"));
        int line1set2=Integer.parseInt(request.getParameter("line1set2"));
        int line1set3=Integer.parseInt(request.getParameter("line1set3"));
        int line2=Integer.parseInt(request.getParameter("line2"));
        int line3=Integer.parseInt(request.getParameter("line3"));
        int startdate=Integer.parseInt(request.getParameter("startdate"));
        int enddate=Integer.parseInt(request.getParameter("enddate"));
        System.out.println(startdate);
        //执行sql语句
        jdbcTemplate1.execute(String.format(command,line1set1,line1set2,line1set3,line2,line3,startdate,enddate));

        return "success";
    }
    private static final String newProcessPlan="select *  from process_plan order by id desc limit 0,1;";
    @RequestMapping("/newPlan")
    private List<Map<String,Object>> newPlan()
    {
        List<Map<String,Object>> result=jdbcTemplate1.queryForList(newProcessPlan);
        return result;
    }
}
