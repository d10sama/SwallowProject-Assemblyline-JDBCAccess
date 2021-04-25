package com.example.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

@ResponseBody
@Controller
@CrossOrigin
public class BoundSaver {
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;

    File BoundRecorder=new File("Bounds.txt");
    Integer SU=10,SL=-10,PU=10,PL=10;

    @RequestMapping(value="/get",method={RequestMethod.POST, RequestMethod.GET})
    public String Adapter(HttpServletRequest request, HttpSession session) {
        SU=Integer.parseInt(request.getParameter("SU"));
        SL=Integer.parseInt(request.getParameter("SL"));
        PU=Integer.parseInt(request.getParameter("PU"));
        PL=Integer.parseInt(request.getParameter("PL"));
        String SPrec=SU.toString()+" "+SL.toString()+" "+PU.toString()+" "+PL.toString()+" ";
        byte[] ins=SPrec.getBytes();
        if(!BoundRecorder.exists())
        {
            try {
                BoundRecorder.createNewFile();
            }catch (Exception e)
            {
                e.printStackTrace();
                return "0";
            }
        }
        try {
            FileWriter fw = new FileWriter(BoundRecorder);
            BufferedWriter bw=new BufferedWriter(fw);
            bw.write(SPrec);
            bw.close();
        }catch (Exception e)
        {
            e.printStackTrace();
            return "0";
        }
        return "1";
    }

    @RequestMapping("/spbound")
    Map<String,Object> boundRetriever()
    {
        Map<String,Object> ans=new LinkedHashMap<>();
        String str="";
        int tmpval=0;
        int namecount=0;
        String[] NameSet={"SU","SL","PU","PL"};
        if (BoundRecorder.exists()) {
            try {
                FileReader fr=new FileReader(BoundRecorder);
                BufferedReader br=new BufferedReader(fr);
                str=br.readLine();

                for(int i=0;i<str.length()&&namecount<=3;i++)
                {
                    if(str.charAt(i)!=' ')
                    {
                        tmpval=tmpval*10+(str.charAt(i)-'0');
                        System.out.println(tmpval);
                    }
                    else{
                        //存入阈值记录
                        System.out.println(tmpval);
                        ans.put(NameSet[namecount++],tmpval);
                        tmpval=0;
                    }
                }
                fr.close();
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return ExpANS(ans);
            } catch (IOException e) {
                e.printStackTrace();
                return ExpANS(ans);
            }
        } else {
            return ExpANS(ans);
        }
        return ans;
    }

    //特例插入默认值
    Map<String,Object> ExpANS(Map<String,Object> ans)
    {
        ans.put("SU",SU.toString());
        ans.put("SL",SL.toString());
        ans.put("PU",PU.toString());
        ans.put("PL",PL.toString());
        return ans;
    }

}
