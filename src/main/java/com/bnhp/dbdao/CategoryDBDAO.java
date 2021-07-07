package com.bnhp.dbdao;

import com.bnhp.beans.Category;
import com.bnhp.beans.Company;
import com.bnhp.dao.CategoryDAO;
import com.bnhp.dao.CompanyDAO;
import com.bnhp.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDBDAO implements CategoryDAO {
    private static final String QUERY_INSERT = "INSERT INTO `bnhp-pro1`.`categories` (`id`, `name`) VALUES (?, ?);";


    public void addCategory(int i, String str) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, i);
        map.put(2, str);


        DBUtils.runStatement(QUERY_INSERT, map);

    }


}
