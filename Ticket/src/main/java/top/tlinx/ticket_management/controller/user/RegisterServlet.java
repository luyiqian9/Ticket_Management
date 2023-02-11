package top.tlinx.ticket_management.controller.user;

import com.alibaba.fastjson2.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.mindrot.jbcrypt.BCrypt;
import top.tlinx.ticket_management.exception.GlobalException;
import top.tlinx.ticket_management.mapper.UserMapper;
import top.tlinx.ticket_management.pojo.User;
import top.tlinx.ticket_management.utils.Mybatis;
import top.tlinx.ticket_management.utils.SendResp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/user/register/")    // 用户注册Servlet
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String pwd = req.getParameter("password");
        String email = req.getParameter("email");
        String AccessCode = req.getParameter("accesscode");
        JSONObject json = new JSONObject();    // 构造响应json

        username = username.trim();   // 去字符串前后空格
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

        // 验证 accesscode 真实性
        HttpSession session = req.getSession(false);
        if(session == null) {
            System.out.println("session is null");
            throw new GlobalException(500, "还未获取验证码 请先获取验证码");
        }
        String code = (String) session.getAttribute(email);
        System.out.println("email = " + email + "\nacCode = " + code);
        if(code == null || !code.equals(AccessCode)) {
            throw new GlobalException(500, "验证码已过期或不匹配");
        }

        String password = BCrypt.hashpw(pwd, BCrypt.gensalt());   // 密码加密
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();   // Mybatis 封装
            UserMapper Mapper = sqlSession.getMapper(UserMapper.class);
            User user = new User(null, username, password, 0, email);
            Mapper.insert(user);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
            json.put("error_msg", "用户名重复或邮箱已被使用 注册失败");
            SendResp.sendResp(resp, json);
            return ;
        } finally {
            sqlSession.close();
        }

        json.put("error_msg", "注册成功");
        session.removeAttribute(email);    // 验证码使用后销毁
//        System.out.println("code = " + session.getAttribute(email));
        SendResp.sendResp(resp, json);
    }
}
