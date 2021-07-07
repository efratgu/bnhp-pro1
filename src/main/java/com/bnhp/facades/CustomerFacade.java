package com.bnhp.facades;

import com.bnhp.beans.Category;
import com.bnhp.beans.Company;
import com.bnhp.beans.Coupons;
import com.bnhp.beans.Customer;
import com.bnhp.dao.CustomersDAO;
import com.bnhp.exceptions.CouponsSystemexception;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerFacade extends ClientFacade {
    private int customerId;

    public CustomerFacade() {
        super();
    }

    public CustomerFacade(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean login(String email, String password) throws SQLException {
        return this.customersDAO.isCustumerExist(email, password);
    }

    public void purchaseCoupon(Coupons coupon) throws CouponsSystemexception, SQLException {
        if (this.couponsDAO.isPurchaseExist(coupon.getId(), customerId)) {
            throw new CouponsSystemexception("cant buy coupon twice");
        }

        if (coupon.getAmount() == 0) {
            throw new CouponsSystemexception("cant buy coupon whith amount 0");
        }
        if (coupon.getEndDate().before(Date.valueOf(LocalDate.now()))) {
            throw new CouponsSystemexception("cant buyexpired time coupon ");
        }
        this.couponsDAO.addCouponPurchase(customerId, coupon.getId());
        coupon.setAmount(coupon.getAmount() - 1);
        this.couponsDAO.updateCoupon(coupon);

    }

    public List<Coupons> getCustomerCoupons() throws SQLException {
        return this.couponsDAO.getAllCutomerPurchase(customerId);
    }

    public List<Coupons> getCustomerCouponsbyCategory(Category category) throws SQLException {
        return this.couponsDAO.gatAllCouponsBycategory(customerId, category);
    }

    public List<Coupons> getCustomerCoupons(double maxPrice) throws SQLException {
        List<Coupons> customerCoupons = getCustomerCoupons();
        List<Coupons> maxPriceCoupons = new ArrayList<>();
        for (Coupons c : customerCoupons) {
            if (c.getPrice() < maxPrice) {
                maxPriceCoupons.add(c);
            }
        }
        return maxPriceCoupons;
    }

    public Customer getcustomerdetails() throws SQLException {

        Customer cust1 = this.customersDAO.getOnecustumer(customerId);
        cust1.setCoupon(this.couponsDAO.getAllCutomerPurchase(customerId));

        return cust1;
    }

    public int getCustomerIdByEmailAndPassword(String email, String password) throws SQLException {
        return this.customersDAO.getCustomerIdByEmailAndPassword(email, password);
    }

}
