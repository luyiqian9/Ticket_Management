package top.tlinx.ticket_management.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.apache.ibatis.session.SqlSession;
import top.tlinx.ticket_management.exception.GlobalException;
import top.tlinx.ticket_management.mapper.UserMapper;
import top.tlinx.ticket_management.pojo.User;
import top.tlinx.ticket_management.utils.JwtUtil;
import top.tlinx.ticket_management.utils.Mybatis;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(filterName = "LoginFilter",urlPatterns = "/*")
public class JwtAuthenticationTokenFilter implements Filter {

    private final String[] noAuthMapping = {
                "/user/register/",
                "/user/login/",
    };
    public void init(FilterConfig config) {
    }

    private boolean canRequest(String url){
        for(String s : noAuthMapping) {
            Pattern pattern = Pattern.compile(s);
            Matcher matcher = pattern.matcher(url);
            if (matcher.find())
                return true;
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        req.setCharacterEncoding("utf-8");

        resp.setContentType("application/json;charset=utf-8");
        String token = req.getHeader("Authorization");
        if (Objects.isNull(token) || !token.startsWith("Bearer ")) {
            if (canRequest(req.getRequestURI()))
                chain.doFilter(request, response);
            else
                onErrorLogin(resp);
            return;
        }

        token = token.substring(7);

        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (SqlSession sqlSession = Mybatis.getInstance().openSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.selectByid(Integer.parseInt(userid));
            if (user == null) {
                throw new RuntimeException("用户未登录");
            }
            req.getSession().setAttribute("user_id",userid);

        }catch (RuntimeException e){
            e.printStackTrace();
            if (!canRequest(req.getRequestURI())){
                onErrorLogin(resp);
                return;
            }
        }

        try {
            chain.doFilter(request, response);
        }catch (Exception e){
            if (e instanceof GlobalException){
                onError(resp, (GlobalException) e);
                e.printStackTrace();
            }
        }

    }

    @Override
    public void destroy() {

    }

    private void onError(HttpServletResponse resp,GlobalException e){
        HashMap<String,Object> map = new HashMap<>();
        map.put("code", e.getCode());
        map.put("error_msg", e.getMsg());
        try {
            ObjectMapper mapper = new ObjectMapper();
            resp.getWriter().println(mapper.writeValueAsString(map));
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private void onErrorLogin(HttpServletResponse resp){
            HashMap<String,String> map = new HashMap<>();
            map.put("code", "403");
            map.put("error_msg", "登陆失败");

            try {
                ObjectMapper mapper = new ObjectMapper();
                resp.getWriter().println(mapper.writeValueAsString(map));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}
