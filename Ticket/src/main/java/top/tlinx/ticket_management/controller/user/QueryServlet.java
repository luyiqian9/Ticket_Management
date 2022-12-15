package top.tlinx.ticket_management.controller.user;

import com.alibaba.fastjson2.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "QueryServlet", value = "/query/")   // 按列车号 | 终点站 查询Servlet
public class QueryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        List<Train> list = null;
        List<Map<String, Object>> res = new ArrayList<>();

        String flextid = req.getParameter("flextid");   // 列车号 | 终点站
        String query = req.getParameter("status");      //  tid表示用列车号 | spot表示用终点站

        SqlSession sqlSession = null;
        try {
            sqlSession = Mybatis.getInstance().openSession();
            TrainMapper Mapper = sqlSession.getMapper(TrainMapper.class);
            if("tid".equals(query)) {
                int tid = Integer.parseInt(flextid);
                list = Mapper.queryById(tid);
                if(list.isEmpty()) {
                    throw new GlobalException(500, "该列车不存在");
                }
            }
            else {
                list = Mapper.queryByEd(flextid);
                if(list.isEmpty()) {
                    throw new GlobalException(500, "没有到达该终点站的列车");
                }
            }
            System.out.println(list);
            for(Train train: list) {
                Map<String, Object> map = new HashMap<>();
                map.put("tid", train.getTid());
                map.put("st", train.getStartStation());
                map.put("ed", train.getEndStation());
                System.out.println(train.getStartStation() + ' ' + train.getEndStation());
                System.out.println(map);
                res.add(map);
            }
        } catch (SqlSessionException e) {
            e.printStackTrace();
            throw new GlobalException(500, "数据库错误");
        } finally {
            sqlSession.close();
        }

        json.put("error_msg", "查询成功");
        json.put("route", res);
        SendResp.sendResp(resp, json);
    }
}
