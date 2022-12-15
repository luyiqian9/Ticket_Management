package top.tlinx.ticket_management.controller.user;


import com.alibaba.fastjson2.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.mindrot.jbcrypt.BCrypt;
import top.tlinx.ticket_management.mapper.UserMapper;
import top.tlinx.ticket_management.pojo.User;
import top.tlinx.ticket_management.utils.Mybatis;
import top.tlinx.ticket_management.utils.SendResp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/user/register/")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String pwd = req.getParameter("password");
        JSONObject json = new JSONObject();    // 构造响应json

        username = username.trim();
        if(username == null || "".equals(username)) {
            json.put("error_msg", "用户名不能为空");
            SendResp.sendResp(resp, json);
            return;
        }
        if(pwd == null || "".equals(pwd)) {
            json.put("error_msg", "密码不能为空");
            SendResp.sendResp(resp, json);
            return;
        }

        String password = BCrypt.hashpw(pwd, BCrypt.gensalt());   // 密码加密
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();   // Mybatis 封装
            UserMapper Mapper = sqlSession.getMapper(UserMapper.class);
            User user = new User(null, username, password, 0);
            Mapper.insert(user);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
            json.put("error_msg", "用户名重复 注册失败");
            SendResp.sendResp(resp, json);
            return ;
        } finally {
            sqlSession.close();
        }

        json.put("error_msg", "注册成功");
        SendResp.sendResp(resp, json);
    }
}
