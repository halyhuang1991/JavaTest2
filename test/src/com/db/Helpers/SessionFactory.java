package com.db.Helpers;

import java.io.FileInputStream;
// import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.Beans.Person;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import java.util.Map;

public class SessionFactory {
    private static SqlSession GetFactory() {
        String resource = "mybatis-config.xml";
        // 定位核心配置文件
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {

            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 创建 SqlSessionFactory
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        return sqlSession;
    }

    public static void TestMybatis() {
        /**
         * 1、获得 SqlSessionFactory 2、获得 SqlSession 3、调用在 mapper 文件中配置的 SQL 语句
         */
        try {
            String resource = "D:\\C\\github\\JavaTest2\\test\\src\\mybatis-config.xml";
            // 定位核心配置文件
            InputStream inputStream = new FileInputStream(resource);// Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            // 创建 SqlSessionFactory
            SqlSession sqlSession = sqlSessionFactory.openSession(false);
            // 获取到 SqlSession // 调用 mapper 中的方法：命名空间 + id
            Person pp = new Person();
            pp.setId(23);
            pp.setAge(22);
            pp.setName("UpdateOrInsert");
            // sqlSession.insert("com.Beans/mapper.UserMapper.insertUser", pp);
            sqlSession.insert("com.Beans/mapper.UserMapper.saveOrUpdate", pp);
            sqlSession.commit();

            System.out.println("-------------------------");
            sqlSession.close();
            sqlSession = sqlSessionFactory.openSession(false);
            try {
                // 把数据库表引擎变更为innoDB 否则事务回滚无效
                pp.setId(12);
                pp.setName("test");
                sqlSession.insert("com.Beans/mapper.UserMapper.insertUser", pp);
                pp.setId(1);
                pp.setName("ok");
                sqlSession.insert("com.Beans/mapper.UserMapper.insertUser", pp);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
            }
            // ------------search
            System.out.println("-------------------------");
            List<Person> personList = sqlSession.selectList("com.Beans/mapper.UserMapper.findAll");
            for (int i = 0; i < personList.size(); i++) {
                System.out.println(personList.get(i)); // .get(index)
            }
            System.out.println("-------------------------");
            pp.setId(4);
            personList = sqlSession.selectList("com.Beans/mapper.UserMapper.queryList", pp);
            for (Person p : personList) {
                System.out.println(p.getName());
            }
            // -------------第二种事务
            TransactionFactory transactionFactory = new JdbcTransactionFactory();
            Transaction newTransaction = null;
            try {
                newTransaction = transactionFactory.newTransaction(sqlSession.getConnection());
                pp.setId(1);
                pp.setName("ok");
                sqlSession.insert("com.Beans/mapper.UserMapper.insertUser", pp);
                newTransaction.commit();
            } catch (Exception e) {
                try {
                    newTransaction.rollback();
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }

            }
            // ----------------------------第三种事务
            Map<String, Integer> parameterMap = new HashMap<String, Integer>();
            parameterMap.put("id", 1);
            parameterMap.put("usercount", -1);
            sqlSession.selectOne("com.Beans/mapper.UserMapper.getUserCount", parameterMap);
            Integer result = parameterMap.get("usercount");
            System.out.println(result);

            // ---------------------------多表查询
            // sqlSessionFactory.getConfiguration().addMapper(SqlDao.class);
            SqlDao sqlDao = sqlSession.getMapper(SqlDao.class);
            List<Map<String, Object>> sqlresult = sqlDao.findMultiTable(1);
            System.out.println("name\tmath");
            System.out.println("findMultiTable==============findMultiTableByWhere");
            sqlresult = sqlDao.findMultiTableByWhere(" where p.id=1");
            for (Map<String, Object> one_sqlresult : sqlresult) {
                System.out.println(one_sqlresult.get("name") + "\t" + one_sqlresult.get("Math"));
            }
            sqlresult = sqlDao.getList1(1);
            for (Map<String, Object> one_sqlresult : sqlresult) {
                System.out.println(one_sqlresult.get("name") + "----");
            }
            sqlDao.UpdateUser1(1, "11111");
            sqlSession.commit();
            // ------------多参数查询
            System.out.println("=======多参数查询=============");
            // ---传入String 会在sql多加了个单引号 导致出错
            HashMap<String, Object> paramap = new HashMap<String, Object>();
            paramap.put("id", 3);
            paramap.put("name", "name111");
            // personList = sqlSession.selectList("com.db.Helpers.SqlDao.getPersonList1",
            // "name111");
            // for (int i = 0; i < personList.size(); i++) {
            // System.out.println(personList.get(i)); // .get(index)
            // }
            // sqlresult = sqlSession.selectList("com.db.Helpers.SqlDao.getPersonList",
            // paramap);
            // for (Map<String, Object> one_sqlresult : sqlresult) {
            // System.out.println(one_sqlresult.get("name") + "----");
            // }
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", "creator11");
            String[] ids = { "1", "2" };
            map.put("ids", ids);
            sqlSession.update("com.db.Helpers.SqlDao.updatePersonList1", map);
            sqlSession.commit();
            // sqlresult = sqlSession.selectList("com.db.Helpers.SqlDao.getPersonList2",
            // map);
            /*
             * sqlresult = sqlDao.getPersonList2(map); for (Map<String, Object>
             * one_sqlresult : sqlresult) { System.out.println(one_sqlresult.get("name") +
             * "----"); }
             */

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

}