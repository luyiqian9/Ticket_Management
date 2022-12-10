package top.tlinx.ticket_management.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.Reader;

public class Mybatis {
    private static SqlSessionFactory sqlSessionFactory;
    public static synchronized SqlSessionFactory getInstance(){
        if (sqlSessionFactory == null){
            Reader reader = null;
            try {
                reader = Resources.getResourceAsReader("mybatis-config.xml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }
        return sqlSessionFactory;
    }
}
