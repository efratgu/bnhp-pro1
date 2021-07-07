package com.bnhp.dao;


import com.bnhp.beans.Category;
import com.bnhp.beans.Company;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoryDAO {

    void addCategory(int i, String str) throws SQLException;


}
