import org.mindrot.jbcrypt.BCrypt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

public class Test {
    public static void main(String[] args) {
        Date date = new Date();
//        System.out.println(date);
        String s = "2022-12-12T11:22:33";
//        String s1 = "22:59:22";
        System.out.println(s);
        StringBuilder sb = new StringBuilder(s);
        sb.setCharAt(10, ' ');
        s = sb.toString();
        System.out.println(s);
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date st = null, ed = null;
        try {
            st = sdf.parse(s);
//            ed = sdf.parse(s1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("s = " + st);

//        long mills = ed.getTime() - st.getTime();
//        long hour = mills / (60 * 60 * 1000);
//        long minute = (mills / 1000 / 60) % 60 ;
//        String runtime = "  ";
//        System.out.println("hour = " + hour);
//        System.out.println("minutes = " + minute);
//        Formatter formatter = new Formatter();
//        runtime = String.valueOf(formatter.format("%d时%d分", hour, minute));
//
//        System.out.println("runtime = " + runtime);


//        String s = "123";
//        String ps1 = BCrypt.hashpw(s, BCrypt.gensalt());
//        String ps2 = BCrypt.hashpw(s, BCrypt.gensalt());
//        System.out.println("ps1 = " + ps1);
//        System.out.println("ps2 = " + ps2);
//        if(BCrypt.checkpw("1234","$2a$10$mKeR/3sCP2D4wGPWAs9MBuEZlrXZah6Vj8.nCzuyQ5PbZ0sOA7j1m"))
//            System.out.println("yes");
//        else
//            System.out.println("no");
    }
}
