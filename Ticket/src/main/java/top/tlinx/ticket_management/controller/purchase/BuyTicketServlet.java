package top.tlinx.ticket_management.controller.purchase;

import com.alibaba.fastjson2.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;
import top.tlinx.ticket_management.exception.GlobalException;
import top.tlinx.ticket_management.mapper.PurMapper;
import top.tlinx.ticket_management.mapper.TrainMapper;
import top.tlinx.ticket_management.pojo.Purchase;
import top.tlinx.ticket_management.pojo.Train;
import top.tlinx.ticket_management.utils.LegalParse;
import top.tlinx.ticket_management.utils.Mybatis;
import top.tlinx.ticket_management.utils.SendResp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "BuyTicketServlet", value = "/buy/")    //    购 | 退车票Servlet
public class BuyTicketServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int tid = LegalParse.isLegal(req.getParameter("tid"));
        int status = Integer.parseInt(req.getParameter("status"));

        HttpSession session = req.getSession();
        int user_id = Integer.parseInt((String)session.getAttribute("user_id"));
        JSONObject json = new JSONObject();
        String error_msg = "";

        SqlSession sqlSession = null;
        try {
            sqlSession = Mybatis.getInstance().openSession();
            PurMapper purMapper = sqlSession.getMapper(PurMapper.class);    // Mapper 注入
            TrainMapper trainMapper = sqlSession.getMapper(TrainMapper.class);

            Train train = trainMapper.selectByid(tid);
            if(train == null) {
                throw new GlobalException(500, "该列车不存在");
            }
            if(train.getStartTime().getTime() < new Date().getTime() && status == 1) {
                throw new GlobalException(500, "该列车已发出 不可购买");
            }
            if(train.getRemainTicks() <= 0 && status == 1) {
                throw new GlobalException(500, "该列车已无余票");
            }

            int re_ticks = train.getRemainTicks();
            if(status == 1) {
                re_ticks --;
                error_msg = "购票成功";

                Purchase purchase = new Purchase(null, user_id, tid, status);
                purMapper.insert(purchase);  //   购/退车票
                sqlSession.commit();
            }
            else {
                re_ticks++;
                error_msg = "退票成功";

                int pid = LegalParse.isLegal(req.getParameter("pid"));
                purMapper.update(pid);   // 退票则更新 status 字段为 0
                sqlSession.commit();
            }

            Train nwtrain = new Train(train.getTid(), train.getLoad(), re_ticks, train.getStartStation(),
                        train.getEndStation(), train.getStartTime(), train.getEndTime(), train.getRuntime());

            trainMapper.update(nwtrain);
            sqlSession.commit();
        } catch (SqlSessionException e) {
            throw new GlobalException(500, "数据库错误");
        } finally {
            sqlSession.close();
        }

        json.put("error_msg", error_msg);
        SendResp.sendResp(resp, json);
    }
}
