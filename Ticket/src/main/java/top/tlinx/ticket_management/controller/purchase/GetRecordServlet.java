package top.tlinx.ticket_management.controller.purchase;

import com.alibaba.fastjson2.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;
import top.tlinx.ticket_management.exception.GlobalException;
import top.tlinx.ticket_management.mapper.PurMapper;
import top.tlinx.ticket_management.utils.Mybatis;
import top.tlinx.ticket_management.utils.SendResp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "GetRecordServlet", value = "/record/")
public class GetRecordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String suid = (String) session.getAttribute("user_id");
        int uid = Integer.parseInt(suid);
        System.out.println("uid = " + uid);

        List<HashMap<String, Objects>> list = new ArrayList<>();
        JSONObject json = new JSONObject();

        SqlSession sqlSession = null;
        try {
            sqlSession = Mybatis.getInstance().openSession();
            PurMapper Mapper = sqlSession.getMapper(PurMapper.class);
            list = Mapper.select(uid);
            if(list.isEmpty()) {
                throw new GlobalException(500, "当前无订单信息");
            }
            System.out.println(list);
            json.put("info", list);
        } catch (SqlSessionException e) {
            throw new GlobalException(500, "数据库错误");
        }

        json.put("error_msg", "查询成功");
        SendResp.sendResp(resp, json);
    }
}
