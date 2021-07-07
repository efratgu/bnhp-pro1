package com.bnhp.dao;

import com.bnhp.beans.Customer;

import java.sql.SQLException;
import java.util.List;


public interface CustomersDAO {

    boolean isCustumerExist(String email, String password) throws SQLException;

    void addCustumer(Customer customer) throws SQLException;

    void updateCustumer(Customer customer) throws SQLException;

    void deleteCustumer(int customerId) throws SQLException;

    List<Customer> getAllcustumers() throws SQLException;

    Customer getOnecustumer(int customerId) throws SQLException;

    int getCustomerIdByEmailAndPassword(String email, String password) throws SQLException;

    boolean IsEmailExist(String email) throws SQLException;
};
