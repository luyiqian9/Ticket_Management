package top.tlinx.ticket_management.controller.admin;

import com.alibaba.fastjson2.JSONObject;
import org.apache.ibatis.session.SqlSession;
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

@WebServlet(name = "ReadServlet", value = "/read/")   // 统计售票信息Servlet
public class ReadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        List<Map<String,Object>> res = new ArrayList<>();

        SqlSession sqlSession = null;
        try {
            sqlSession = Mybatis.getInstance().openSession();
            TrainMapper Mapper = sqlSession.getMapper(TrainMapper.class);

            List<Train> list = Mapper.query();

            for(Train train : list){
                HashMap<String, Object> map = new HashMap<>();
                map.put("tid",train.getTid());
                map.put("st_time", train.getStartTime());
                map.put("soldout", train.getLoad() - train.getRemainTicks());
                map.put("remainTicks",train.getRemainTicks());
                res.add(map);
            }
            System.out.println(res);

        } catch (Exception e) {
            e.printStackTrace();
            json.put("error_msg", "查询失败");
            SendResp.sendResp(resp, json);
            return ;
        } finally {
            sqlSession.close();
        }

        json.put("error_msg", "查询成功");
        json.put("train", res);
        SendResp.sendResp(resp, json);
    }
}
