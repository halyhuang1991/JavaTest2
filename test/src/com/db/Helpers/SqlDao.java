package com.db.Helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface SqlDao {
    public List<Map<String, Object>> findMultiTable(Integer id);

    public List<Map<String, Object>> findMultiTableByWhere(String where);

    public List<Map<String, Object>> getPersonList2(HashMap<String, Object> map);

    public Integer UpdateUser1(@Param("id") Integer id, @Param("name") String name);

    @Select("call test.testProc(#{id})")
    @Options(statementType = StatementType.CALLABLE)
    List<Map<String, Object>> getList(int id);

    @Select("select * from person where id>#{id}")
    List<Map<String, Object>> getList1(int id);
}
