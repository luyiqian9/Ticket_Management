package top.tlinx.ticket_management.utils;

import com.alibaba.fastjson2.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SendResp {
    public static void sendResp(HttpServletResponse response, JSONObject json) throws IOException {
        response.getWriter().println(json.toJSONString());
    }
}
