package com.bnhp.dbdao;

import com.bnhp.beans.Customer;
import com.bnhp.dao.CustomersDAO;
import com.bnhp.utils.DBUtils;
import com.bnhp.beans.Coupons;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CustomersDBDAO implements CustomersDAO {


    private static final String QUERY_INSERT = "INSERT INTO `bnhp-pro1`.`customers` (`id`, `first_name`, `last_name`, `email`, `password`) VALUES (?, ?, ?, ?, ?);";
    private static final String QUERY_UPDATE = "UPDATE `bnhp-pro1`.`customers` SET `first_name` = ?,`last_name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);";
    private static final String QUERY_DELETE = "DELETE FROM `bnhp-pro1`.`customers` WHERE (`id` = ?);";
    private static final String QUERY_GET_SINGLE = "SELECT * FROM `bnhp-pro1`.`customers` WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL = "SELECT * FROM `bnhp-pro1`.`customers`";
    private static final String QUERY_IS_EXIST = "SELECT * FROM `bnhp-pro1`.`customers` WHERE (`email` = ? AND `password` = ?);";
    private static final String QUERY_GET_ID = "SELECT id FROM `bnhp-pro1`.`customers` WHERE (`email` = ? AND `password` = ?);";
    private static final String QUERY_IS_EMAIL_EXIST = "SELECT * FROM `bnhp-pro1`.`customers` WHERE (`email` = ? );";


    @Override
    public boolean isCustumerExist(String email, String password) throws SQLException {

        boolean result = false;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);

        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_IS_EXIST, map);

        if (resultSet.next()) {
            result = true;
        }


        return result;
    }

    @Override
    public void addCustumer(Customer customer) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customer.getId());
        map.put(2, customer.getFirstName());
        map.put(3, customer.getLastName());
        map.put(4, customer.getEmail());
        map.put(5, customer.getPassword());

        DBUtils.runStatement(QUERY_INSERT, map);

    }

    @Override
    public void updateCustumer(Customer customer) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();

        map.put(1, customer.getFirstName());
        map.put(2, customer.getLastName());
        map.put(3, customer.getEmail());
        map.put(4, customer.getPassword());
        map.put(5, customer.getId());
        DBUtils.runStatement(QUERY_UPDATE, map);

    }


    @Override
    public void deleteCustumer(int customerId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        DBUtils.runStatement(QUERY_DELETE, map);

    }

    @Override
    public List<Customer> getAllcustumers() throws SQLException {
        List<Customer> customers = new ArrayList<>();

        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ALL);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String firstName = resultSet.getString(2);
            String lasttName = resultSet.getString(3);
            String email = resultSet.getString(4);
            String password = resultSet.getString(5);
            Customer c1 = new Customer(id, firstName, lasttName, email, password);
            customers.add(c1);
        }
        return customers;
    }

    @Override
    public Customer getOnecustumer(int customerId) throws SQLException {
        Customer result = null;

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);

        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_SINGLE, map);
        try {
            resultSet.next();

            String firstname = resultSet.getString(2);
            String lastname = resultSet.getString(3);
            String email = resultSet.getString(4);
            String password = resultSet.getString(5);

            result = new Customer(customerId, firstname, lastname, email, password);


            return result;
        } catch (SQLException e) {

            return null;
        }


    }

    @Override
    public int getCustomerIdByEmailAndPassword(String email, String password) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);
        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ID, map);

        resultSet.next();

        int id = resultSet.getInt(1);

        return id;
    }

    @Override
    public boolean IsEmailExist(String email) throws SQLException {
        boolean result = false;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);


        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_IS_EMAIL_EXIST, map);

        if (resultSet.next()) {
            result = true;
        }


        return result;
    }


}
