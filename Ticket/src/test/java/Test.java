import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;

public class Test {
    public static void main(String[] args) {
        String s = "123";
        String ps1 = BCrypt.hashpw(s, BCrypt.gensalt());
        String ps2 = BCrypt.hashpw(s, BCrypt.gensalt());
        System.out.println("ps1 = " + ps1);
        System.out.println("ps2 = " + ps2);
        if(BCrypt.checkpw("1234","$2a$10$mKeR/3sCP2D4wGPWAs9MBuEZlrXZah6Vj8.nCzuyQ5PbZ0sOA7j1m"))
            System.out.println("yes");
        else
            System.out.println("no");
    }
}
