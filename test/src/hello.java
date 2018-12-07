import java.util.HashMap;
import java.util.Map;

import com.db.Helpers.JsonTest;
import com.db.Helpers.SessionFactory;
import com.db.mysql.*;
import com.test.HM;

public class hello {
    public static void main(String[] args) {
        System.out.println("ok");
        SessionFactory.TestMybatis();
    }
}