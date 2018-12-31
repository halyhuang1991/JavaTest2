
// import com.db.Helpers.JsonTest;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import com.Refretor.TestRefretor;
import com.db.DailyTools.GenHtmlOrDes;
import com.db.Helpers.SessionFactory;
import com.db.JDBC.JDBCTest;
import com.db.JDBC.TestAccess;
import com.db.mysql.DB2Db;
// import com.test.HM;
import com.db.mysql.MyDB;
import com.test.HM;
import com.test.Email.sendEmail;
import com.test.Redis.TestJedis;
import com.test.Webserver.*;

import com.test.spring.Springtest;

public class hello {
    public static void OverRideOut() {
        PrintStream myStream = new PrintStream(System.out) {
            @Override
            public void println(String x) {
                try {
                    x = new String(x.getBytes("gbk"), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                super.println(x);
            }
        };
        System.setOut(myStream);
    }

    public static void main(String[] args) {
        OverRideOut();
        System.out.println("ok");
        TestAccess.test();
    }
}