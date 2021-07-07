package com.bnhp.dao;

import com.bnhp.beans.Company;

import java.sql.SQLException;
import java.util.ArrayList;


public interface CompanyDAO {

    boolean isCompanyExist(String email, String password) throws SQLException;

    void addCompany(Company company) throws SQLException;

    void updateCompany(Company company) throws SQLException;

    void deleteCompany(int companyId) throws SQLException;

    ArrayList<Company> getAllCompanies() throws SQLException;

    Company getOneCompany(int companyId) throws SQLException;

    boolean isNameOrEmailExist(String name, String email) throws SQLException;

    int getIdByEmailAndPassword(String email, String password) throws SQLException;
};
