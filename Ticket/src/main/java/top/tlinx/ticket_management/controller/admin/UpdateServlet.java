package top.tlinx.ticket_management.controller.admin;

import com.alibaba.fastjson2.JSONObject;
import org.apache.ibatis.session.SqlSession;
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

@WebServlet(name = "UpdateServlet", value = "/update/")   // 修改列车信息Servlet
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int tid = Integer.parseInt(req.getParameter("tid"));
        System.out.println("tid = " + tid);
        int load = Integer.parseInt(req.getParameter("load"));
        int remain = load;
        System.out.println("remain = " + remain);
        String start = req.getParameter("start_station");
        System.out.println("start = " + start);

//        byte[] bytes = start.getBytes(StandardCharsets.ISO_8859_1);
//        //3.2 字节数组解码
//        start = new String(bytes, StandardCharsets.UTF_8);

        String end = req.getParameter("end_station");
        System.out.println("end = " + end);
        String st_time = req.getParameter("st_time");
        System.out.println("st_time = " + st_time);
        String ed_time = req.getParameter("ed_time");
        System.out.println("ed_time = " + ed_time);

        JSONObject json = new JSONObject();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
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

        long mills = ed.getTime() - st.getTime();
        long hour = mills / (60 * 60 * 1000);
        long minute = (mills / 1000 / 60) % 60 ;
        String runtime = null;
        Formatter formatter = new Formatter();
        runtime = String.valueOf(formatter.format("%d时%d分", hour, minute));
        System.out.println("runtime = " + runtime);

        Train train = new Train(tid, load, remain, start, end, st, ed, runtime);

        SqlSession sqlSession = null;
        try{
            sqlSession = Mybatis.getInstance().openSession();
            TrainMapper Mapper = sqlSession.getMapper(TrainMapper.class);
            Mapper.update(train);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error_msg", "修改失败");
            SendResp.sendResp(resp, json);
            return ;
        } finally {
            sqlSession.close();
        }

        json.put("error_msg", "修改成功");
        SendResp.sendResp(resp, json);
    }
}
