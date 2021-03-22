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
    String sentence1 = "select id  from Product_Table order by id desc limit 0,1;";
    String template1="select * from Product_Table where id=%d";
    //选取database
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;

    //compare whether new neck is generated
    @RequestMapping("/l1neck")//返回页面
    @Scheduled(fixedRate = 1000)//定时1秒
    public List<Map<String, Object>> content1() {
        int count=0;
        //创建对象
        List<Map<String, Object>> tmp = null;
        //尝试对表line1_sets_time执行sql语句，失败则在控制台输出错误信息
        try {
            this.presentNum = jdbcTemplate1.queryForObject(sentence1, long.class);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        //让tmp等于sql语句返回的对象
        tmp= jdbcTemplate1.queryForList(String.format(template1, this.presentNum));
        //返回cmp()方法处理过后的tmp
        return cmp(tmp);

    }
    //下同
    String sentence2 = "select id  from line2_sets_time order by id desc limit 0,1;";

    String template2="select * from where id=%d";
    @RequestMapping("/l2neck")
    @Scheduled(fixedRate = 1000)
    public List<Map<String, Object>> content2() {
        int count=0;
        List<Map<String, Object>> tmp = null;
        try {
            this.presentNum = jdbcTemplate1.queryForObject(sentence2, long.class);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        //.out.println("pre="+this.presentNum);
        tmp= jdbcTemplate1.queryForList(String.format(template2, this.presentNum));
        return cmp(tmp);
    }
    String sentence3 = "select id  from line3_sets_time order by id desc limit 0,1;";

    //下同
    String template3="select * from Product_Table where id=%d";
    @RequestMapping("/l3neck")
    @Scheduled(fixedRate = 500)
    public List<Map<String, Object>> content3() {
        int count=0;
        List<Map<String, Object>> tmp = null;
        try {
            this.presentNum = jdbcTemplate1.queryForObject(sentence3, long.class);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        tmp= jdbcTemplate1.queryForList(String.format(template3, this.presentNum));
        return cmp(tmp);
    }
    //cmp方法
    List<Map<String,Object>> cmp(List<Map<String,Object>> tmp)
    {
        int[] sets;//存数据数组
        int count=0;//数组大小计数器
        int value=0;//数组值记录器
        //计算数组大小
        for(int i=0;i<tmp.get(0).size();i++)
            count++;

        sets=new int[count];//为数组new空间

        count=0;//计数器重置
        //两层for循环取出条目数据并存入sets[]
        /*
            特别注意，由于sql语句为select id,......
            所以数组第一位存储的并不是某个工位的数据，而是id值，而要避免他的方法会在下面提到
         */
        try{
            for (Map<String, Object> map : tmp) {
                for (String s : map.keySet()) {
                    sets[count++]=Integer.parseInt(map.get(s).toString());
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        count=0;//计数器清零
        /*
            通过从
            i=1而不是i=0跳过第一位存放id的
            若value的值小于数组当前位置的值
            则value=当前值
            这样通过时间复杂度为n比较出最大的工位
            既瓶颈工位
         */
        try {
            for (int i = 1; i < sets.length; i++) {
                if (count < sets[i]) {
                    count = sets[i];
                    value = i;
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        //创建map对象ADD方便存入tmp中
        Map<String,Object> ADD=new HashMap<String,Object>();
        //add中放入标签为“neck”的value值；
        ADD.put("neck",value);
        tmp.add(ADD);
        //返回处理过后的tmp
        return tmp;
    }


}
