package top.tlinx.ticket_management.controller.user;

import com.alibaba.fastjson2.JSONObject;
import com.sun.mail.util.MailSSLSocketFactory;
import top.tlinx.ticket_management.exception.GlobalException;
import top.tlinx.ticket_management.utils.SendResp;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.Random;


@WebServlet(name = "AccessCodeServlet", value = "/getcode/")
public class AccessCodeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sendto = req.getParameter("sendto");
        sendto = sendto.trim();
        if(sendto.length() == 0) {
            throw new GlobalException(500, "邮箱不合法");
        }

        // 随机验证码
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < 7;i ++) {
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        String passcode = sb.toString();

        // 调用service方法 这里直接写实现
        Properties prop = new Properties();//创建一封邮件
        //以下三项的Key的值都是固定的
        prop.setProperty("mail.host","smtp.qq.com");//设置邮件服务器
        prop.setProperty("mail.transport.protocol","smtp");//设置邮件发送协议
        prop.setProperty("mail.smtp.auth","true");//需要验证用户名和密码

        //还要设置SSL加密，加上以下代码即可，其他邮箱不需要
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new GlobalException(500, "服务器异常 请稍后重试");
        }
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable","true");
        prop.put("mail.smtp.ssl.socketFactory",sf);

        //==========使用JavaMail发送邮件的6个步骤======
        //1、创建定义整个应用程序所需要的环境信息的Session对象
        //只有qq才有，其他邮箱不需要
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication(){
                //发件人邮箱的用户名和授权码(只有qq是授权码，其它的是密码)
                return new PasswordAuthentication("2667514756@qq.com","wsezvscjcrdrecdi");
            }
        });
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
//        session.setDebug(true);

        //2、通过Session得到transport对象
        Transport ts = null;
        try {
            ts = session.getTransport();
            //3、使用邮箱的用户名和授权码连上邮件服务器
            ts.connect("SMTP.qq.com","2667514756@qq.com","wsezvscjcrdrecdi");
            //创建邮件对象
            MimeMessage message = new MimeMessage(session);
            //指明邮件的发件人
            message.setFrom(new InternetAddress("2667514756@qq.com"));
            //指明邮件的收件人
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(sendto));
            //邮件的标题
            message.setSubject("邮箱验证码");
            //邮件的文本内容  这个可以写css样式<h1 style='color:red'>...</h1>
            message.setContent("欢迎注册 您的验证码为: " + passcode,"text/html;charset=UTF-8");
            //5、发送邮件         地址
            ts.sendMessage(message,message.getAllRecipients());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(500, "服务器异常 请稍后重试");
        } finally {
            //6、关闭连接
            try {
                ts.close();
            } catch (MessagingException e) {
                e.printStackTrace();
                throw new GlobalException(500, "服务器异常 请稍后重试");
            }
        }

        /**
         *  存入Session 有效期设置为 5分钟
         **/
        HttpSession hsession = req.getSession();
        hsession.setAttribute(sendto, passcode);
        hsession.setMaxInactiveInterval(5 * 60);

        String value = "JSESSIONID=" + hsession.getId() + "; Path=/;" + "SameSite=None;Secure";  // 解决跨域cookie被屏蔽问题
        resp.setHeader("Set-Cookie", value);

        System.out.println("hsessionid = " + hsession.getId());
        System.out.println(hsession.getAttribute(sendto));

        JSONObject json = new JSONObject();
        json.put("error_msg", "发送成功");
        SendResp.sendResp(resp, json);
    }
}
