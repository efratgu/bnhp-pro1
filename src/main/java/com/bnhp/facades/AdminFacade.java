package com.bnhp.facades;

import com.bnhp.beans.Company;
import com.bnhp.beans.Coupons;
import com.bnhp.beans.Customer;
import com.bnhp.dao.CustomersDAO;
import com.bnhp.exceptions.CouponsSystemexception;

import java.sql.SQLException;
import java.util.List;

public class AdminFacade extends ClientFacade {
    public AdminFacade() {
    }

    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    public void addCompany(Company company) throws CouponsSystemexception, SQLException {
        if (this.companiesDAO.isNameOrEmailExist(company.getName(), company.getEmail())) {
            throw new CouponsSystemexception("cant add company with the same name or email");
        }
        this.companiesDAO.addCompany(company);
    }

    public void updateCompany(Company company) throws SQLException, CouponsSystemexception {
        Company c1 = this.companiesDAO.getOneCompany(company.getId());
        if (c1 == null) {
            throw new CouponsSystemexception("cant update id");
        }
        if (c1.getName().equals(company.getName())) {
            this.companiesDAO.updateCompany(company);
            return;
        }
        throw new CouponsSystemexception("cant update  name");

    }

    public void deleteCompany(int companyId) throws SQLException {

        List<Coupons> coupons = this.couponsDAO.GetAllCouponsByCompId(companyId);
        for (Coupons c : coupons) {
            this.couponsDAO.deletePurchaseByCoupoId(c.getId());
            this.couponsDAO.deleteCoupon(c.getId());

        }
        this.companiesDAO.deleteCompany(companyId);


    }

    public List<Company> getAllCompanies() throws SQLException {
        return this.companiesDAO.getAllCompanies();
    }

    public Company getOneCompany(int companiId) throws SQLException {
        return this.companiesDAO.getOneCompany(companiId);
    }

    public void addCustomer(Customer customer) throws SQLException, CouponsSystemexception {
        if (this.customersDAO.IsEmailExist(customer.getEmail())) {
            throw new CouponsSystemexception("cant add new customer with exist email");
        }
        this.customersDAO.addCustumer(customer);


    }

    public void updateCustomer(Customer customer) throws SQLException, CouponsSystemexception {
        Customer c1 = this.customersDAO.getOnecustumer(customer.getId());
        if (c1 == null) {
            throw new CouponsSystemexception("cant update id");

        }
        this.customersDAO.updateCustumer(customer);


    }

    public void deleteCustomer(int customerId) throws SQLException {

        this.couponsDAO.deletePurchaseBycustomid(customerId);
        this.customersDAO.deleteCustumer(customerId);

    }

    public List<Customer> getAllCustomers() throws SQLException {
        return this.customersDAO.getAllcustumers();
    }

    public Customer getOneCustomer(int customerId) throws SQLException {
        return this.customersDAO.getOnecustumer(customerId);

    }


}
