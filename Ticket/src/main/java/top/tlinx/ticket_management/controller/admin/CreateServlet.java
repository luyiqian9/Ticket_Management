package top.tlinx.ticket_management.controller.admin;

import com.alibaba.fastjson2.JSONObject;
import org.apache.ibatis.session.SqlSession;
import top.tlinx.ticket_management.exception.GlobalException;
import top.tlinx.ticket_management.mapper.TrainMapper;
import top.tlinx.ticket_management.pojo.Train;
import top.tlinx.ticket_management.utils.Mybatis;
import top.tlinx.ticket_management.utils.SendResp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

@WebServlet(name = "CreateServlet", value = "/create/")    // 添加列车Servlet
public class CreateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int load = Integer.parseInt(req.getParameter("load"));
        int remain = load;
        System.out.println("load = " + load);
        System.out.println("remain = " + remain);
        String start = req.getParameter("start_station");
        System.out.println("start_station = " + start);
        String end = req.getParameter("end_station");
        System.out.println("end_station = " + end);
        String st_time = req.getParameter("st_time");
        System.out.println("st_time = " + st_time);
        String ed_time = req.getParameter("ed_time");
        System.out.println("ed_time = " + ed_time);

        StringBuilder sb = new StringBuilder(st_time);    // 修改前端时间值以此转化成Date类型
        StringBuilder sb1 = new StringBuilder(ed_time);
        sb.setCharAt(10, ' '); sb1.setCharAt(10, ' ');
        st_time = sb.toString();
        ed_time = sb1.toString();

        JSONObject json = new JSONObject();

        System.out.println(st_time);
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");   // 转化时间格式
        Date st = null, ed = null;
        try {
            st = sdf.parse(st_time);
            ed = sdf.parse(ed_time);
        } catch (ParseException e) {
            e.printStackTrace();
            json.put("error_msg", "时间格式错误");
            SendResp.sendResp(resp, json);
            return ;
        }
        System.out.println(st);

        long mills = ed.getTime() - st.getTime();   // 计算列车行驶时间
        if(mills < 0) {
            throw new GlobalException(500, "到站时间不可先于发车时间");
        }
        long hour = mills / (60 * 60 * 1000);
        long minute = (mills / 1000 / 60) % 60 ;
        String runtime = null;
        Formatter formatter = new Formatter();
        runtime = String.valueOf(formatter.format("%d时%d分", hour, minute));
        System.out.println("runtime = " + runtime);

        Train train = new Train(null, load, remain, start, end, st, ed, runtime);

        SqlSession sqlSession = null;    // Mybatis 查询数据库
        try{
            sqlSession = Mybatis.getInstance().openSession();
            TrainMapper Mapper = sqlSession.getMapper(TrainMapper.class);
            Mapper.insert(train);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error_msg", "添加失败");
            SendResp.sendResp(resp, json);
            return ;
        } finally {
            sqlSession.close();
        }

        json.put("error_msg", "添加成功");
        SendResp.sendResp(resp, json);
    }
}
