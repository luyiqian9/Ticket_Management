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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "QueryServlet", value = "/query/")
public class QueryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        Map<String, Object> map = new HashMap<>();
        List<Train> list = null;
        List<Map<String, Object>> res = null;

        String flextid = req.getParameter("flextid");
        String query = req.getParameter("status");

        SqlSession sqlSession = null;
        try {
            sqlSession = Mybatis.getInstance().openSession();
            TrainMapper Mapper = sqlSession.getMapper(TrainMapper.class);
            if("tid".equals(query)) {
                int tid = Integer.parseInt(flextid);
                list = Mapper.queryById(tid);
            }
            else {
                list = Mapper.queryByEd(flextid);
            }
//            System.out.println(list);
            for(Train train: list) {
                map.put("st", train.getStartStation());
                map.put("ed", train.getEndStation());
                System.out.println(train.getStartStation() + ' ' + train.getEndStation());
                res.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(500, "数据库错误");
        }

        json.put("error_msg", "查询成功");
        json.put("route", res);
        SendResp.sendResp(resp, json);
    }
}
