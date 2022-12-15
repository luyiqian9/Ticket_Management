package top.tlinx.ticket_management.controller.user;

import com.alibaba.fastjson2.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import top.tlinx.ticket_management.mapper.UserMapper;
import top.tlinx.ticket_management.pojo.User;
import top.tlinx.ticket_management.utils.JwtUtil;
import top.tlinx.ticket_management.utils.Mybatis;
import top.tlinx.ticket_management.utils.SendResp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/user/login/")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        System.out.println(username + '\n' + password);
        JSONObject json= new JSONObject();

        UserMapper Mapper = Mybatis.getInstance().openSession().getMapper(UserMapper.class);
        User user = null;
        user = Mapper.selectByname(username);
        if(user == null) {
            json.put("error_msg", "用户不存在");
            SendResp.sendResp(resp, json);
            return;
        }

        if(!BCrypt.checkpw(password, user.getPassword())) {
            json.put("error_msg", "密码错误");
            SendResp.sendResp(resp, json);
            return;
        }

        json.put("error_msg", "登录成功");
        json.put("token", JwtUtil.createJWT(Integer.toString(user.getUid())));
        SendResp.sendResp(resp, json);
    }
}
