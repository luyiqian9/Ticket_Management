package top.tlinx.ticket_management.controller.user;

import com.alibaba.fastjson2.JSONObject;
import org.apache.ibatis.session.SqlSession;
import top.tlinx.ticket_management.mapper.UserMapper;
import top.tlinx.ticket_management.pojo.User;
import top.tlinx.ticket_management.utils.Mybatis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetinfoServlet", value = "/user/getinfo/")   // 获取用户信息Servlet
public class GetinfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        SqlSession sqlSession = null;
        String userid = (String)req.getSession(false).getAttribute("user_id");
        int uid = Integer.parseInt(userid);

        try {
            sqlSession = Mybatis.getInstance().openSession();
            UserMapper Mapper = sqlSession.getMapper(UserMapper.class);
            User user = Mapper.selectByid(uid);
            json.put("error_msg", "success");
            json.put("user", user);
            resp.getWriter().println(json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }
}
