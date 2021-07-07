package com.bnhp.facades;

import com.bnhp.beans.Customer;
import com.bnhp.dao.CompanyDAO;
import com.bnhp.dao.CouponsDAO;
import com.bnhp.dao.CustomersDAO;
import com.bnhp.dbdao.CompanyDBDAO;
import com.bnhp.dbdao.CouponsDBDAO;
import com.bnhp.dbdao.CustomersDBDAO;

import java.sql.SQLException;
import java.util.List;

public abstract class ClientFacade {
    protected CompanyDAO companiesDAO;
    protected CustomersDAO customersDAO;
    protected CouponsDAO couponsDAO;

    public ClientFacade() {
        this.companiesDAO = new CompanyDBDAO();
        this.customersDAO = new CustomersDBDAO();
        this.couponsDAO = new CouponsDBDAO();
    }

    public abstract boolean login(String email, String password) throws SQLException;
}


