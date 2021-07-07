package com.bnhp.facades;

import com.bnhp.beans.Category;
import com.bnhp.beans.Company;
import com.bnhp.beans.Coupons;

import com.bnhp.exceptions.CouponsSystemexception;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyFacade extends ClientFacade {
    private int companyId;

    public CompanyFacade() {
        super();
    }

    public CompanyFacade(int companyId) {
        this.companyId = companyId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean login(String email, String password) throws SQLException {

        return this.companiesDAO.isCompanyExist(email, password);


    }

    public void addCoupon(Coupons coupon) throws CouponsSystemexception, SQLException {
        if (chechCompId(coupon.getCompanyId())) {
            List<Coupons> c1 = this.couponsDAO.GetAllCouponsByCompId(coupon.getCompanyId());
            for (Coupons coupons : c1) {

                if ((coupons.getTitle().equals(coupon.getTitle())) && (coupon.getCompanyId() == (coupon.getCompanyId()))) {
                    throw new CouponsSystemexception("cant add new coupon with same title");
                }

            }
            this.couponsDAO.addCoupon(coupon);
        }
    }
    public boolean chechCompId(int compid) throws CouponsSystemexception {
        if (compid != companyId){
            throw new CouponsSystemexception("cant add new coupon for another company");

        }
        return true;
    }

    public void updateCoupon(Coupons coupons) throws SQLException, CouponsSystemexception {
        if (chechCompId(coupons.getCompanyId())) {
            Coupons c1 = this.couponsDAO.getOneCoupon(coupons.getId());
            if (c1 == null) {
                throw new CouponsSystemexception("cant update id");
            }
            if (c1.getCompanyId() == coupons.getCompanyId()) {
                this.couponsDAO.updateCoupon(coupons);
                return;
            }
            throw new CouponsSystemexception("cant update  company id");

        }
    }

    public void deleteCoupon(int couponId) throws SQLException, CouponsSystemexception {
        if (chechCompId(couponId)) {
            this.couponsDAO.deletePurchaseByCoupoId(couponId);
            this.couponsDAO.deleteCoupon(couponId);
        }
    }

    public List<Coupons> getCompanyCoupons() throws SQLException {
        return this.couponsDAO.GetAllCouponsByCompId(companyId);
    }

    public List<Coupons> getCompanyCoupons(Category category) throws SQLException {

        return this.couponsDAO.getCouponsByCategory(companyId, category);

    }

    public List<Coupons> getCompanyCoupons(double maxPrice) throws SQLException {
        List<Coupons> coupons = this.couponsDAO.GetAllCouponsByCompId(companyId);
        List<Coupons> couponsUnderMaxPrice = new ArrayList<>();
        for (Coupons c : coupons) {
            if (c.getPrice() <= maxPrice) {
                couponsUnderMaxPrice.add(c);

            }

        }
        return couponsUnderMaxPrice;
    }

    public Company getcompanydetails() throws SQLException {
        Company comp1 = this.companiesDAO.getOneCompany(companyId);
        comp1.setCoupons(this.couponsDAO.GetAllCouponsByCompId(companyId));
        return comp1;
    }

    public int getIdByEmailAndPassword(String email, String password) throws SQLException {
        return this.companiesDAO.getIdByEmailAndPassword(email, password);
    }


}
