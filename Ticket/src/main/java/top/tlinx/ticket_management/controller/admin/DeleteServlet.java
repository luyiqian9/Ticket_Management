package top.tlinx.ticket_management.controller.admin;

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

@WebServlet(name = "DeleteServlet", value = "/delete/")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int tid = Integer.parseInt(req.getParameter("tid"));

        JSONObject json = new JSONObject();
        SqlSession sqlSession = null;
        try {
            sqlSession = Mybatis.getInstance().openSession();
            TrainMapper Mapper = sqlSession.getMapper(TrainMapper.class);
            Train train = Mapper.selectByid(tid);
            if(train == null) {
                throw new GlobalException(500, "该列车不存在");
            }
            Mapper.delete(tid);
            sqlSession.commit();
        } catch (SqlSessionException e) {
            e.printStackTrace();
            json.put("error_msg", "删除失败");
            SendResp.sendResp(resp, json);
            return ;
        } finally {
            sqlSession.close();
        }

        System.out.println("delete success");
        json.put("error_msg", "删除成功");
        SendResp.sendResp(resp, json);
    }
}
