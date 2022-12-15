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
import java.util.List;

@WebServlet(name = "TrainInfoServlet", value = "/traininfo/")   // 获取所有列车信息Servlet
public class TrainInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        SqlSession sqlSession = null;
        List<Train> list;

        try {
            sqlSession = Mybatis.getInstance().openSession();
            TrainMapper Mapper = sqlSession.getMapper(TrainMapper.class);
            list = Mapper.query();
        } catch (SqlSessionException e) {
            e.printStackTrace();
            throw new GlobalException(500, "数据库错误");
        } finally {
            sqlSession.close();
        }

        json.put("error_msg", "查询成功");
        json.put("train", list);
        SendResp.sendResp(resp, json);
    }
}
