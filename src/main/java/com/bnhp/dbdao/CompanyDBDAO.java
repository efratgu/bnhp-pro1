package com.bnhp.dbdao;

import com.bnhp.beans.Category;
import com.bnhp.beans.Company;
import com.bnhp.beans.Coupons;
import com.bnhp.dao.CompanyDAO;
import com.bnhp.utils.DBUtils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CompanyDBDAO implements CompanyDAO {


    private static final String QUERY_INSERT = "INSERT INTO `bnhp-pro1`.`company` (`id`, `name`, `email`, `password`) VALUES (?, ?, ?, ?);\n;\n";
    private static final String QUERY_UPDATE = "UPDATE `bnhp-pro1`.`company` SET `name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);";
    private static final String QUERY_DELETE = "DELETE FROM `bnhp-pro1`.`company` WHERE (`id` = ?);";
    private static final String QUERY_GET_SINGLE = "SELECT * FROM `bnhp-pro1`.`company` WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL = "SELECT * FROM `bnhp-pro1`.`company`";
    private static final String QUERY_IS_EXIST = "SELECT * FROM `bnhp-pro1`.`company` WHERE (`email` = ? AND `password` = ?);";
    private static final String QUERY_IS_EXIST_BY_NAME_OR_EMAIL = "SELECT * FROM `bnhp-pro1`.`company` WHERE (`name` = ? OR `email` = ?);";
    private static final String QUERY_GET_ID = "SELECT id FROM `bnhp-pro1`.`company` WHERE (`email` = ? AND `password` = ?);";

    @Override
    public boolean isCompanyExist(String email, String password) throws SQLException {
        boolean result = false;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);

        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_IS_EXIST, map);
        try {
            resultSet.next();
            int id = resultSet.getInt(1);
            if (id != 0) {

            }
        } catch (SQLException e) {

            return false;
        }
        return true;
    }









    @Override
    public void addCompany(Company company) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, company.getId());
        map.put(2, company.getName());
        map.put(3, company.getEmail());
        map.put(4, company.getPassword());
        DBUtils.runStatement(QUERY_INSERT, map);

    }

    @Override
    public void updateCompany(Company company) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, company.getName());
        map.put(2, company.getEmail());
        map.put(3, company.getPassword());
        map.put(4, company.getId());
        DBUtils.runStatement(QUERY_UPDATE, map);

    }

    @Override
    public void deleteCompany(int companyId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        DBUtils.runStatement(QUERY_DELETE, map);

    }

    @Override
    public ArrayList<Company> getAllCompanies() throws SQLException {
        List<Company> companies = new ArrayList<>();


        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ALL);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String email = resultSet.getString(3);
            String password = resultSet.getString(4);
            Company c1 = new Company(id, name, email,password);
           companies.add(c1);
        }


        return (ArrayList<Company>) companies;
    }

    @Override
    public Company getOneCompany(int companyId) throws SQLException {
        Company result = null;

        Map<Integer, Object> map = new HashMap<>();
        map.put(1,companyId);

        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_SINGLE, map);
        try {
            resultSet.next();
            String name = resultSet.getString(2);
            String email = resultSet.getString(3);
            String password = resultSet.getString(4);
            result = new Company(companyId, name, email, password);


            return result;
        }
        catch (SQLException e){

            return null;
        }



    }

    @Override
    public boolean isNameOrEmailExist(String name, String email) throws SQLException {
        boolean result = false;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, name);
        map.put(2, email);

        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_IS_EXIST_BY_NAME_OR_EMAIL, map);
        try {
            resultSet.next();
            int id = resultSet.getInt(1);
            if (id != 0){
                result=true;}
        }
        catch (SQLException e){

            return false;
        }





        return result;
    }

    @Override
    public int getIdByEmailAndPassword(String email, String password) throws SQLException {


        Map<Integer, Object> map = new HashMap<>();
        map.put(1,email);
        map.put(2,password);
        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ID, map);

        resultSet.next();

        int id = resultSet.getInt(1);

        return id;

    }

}
