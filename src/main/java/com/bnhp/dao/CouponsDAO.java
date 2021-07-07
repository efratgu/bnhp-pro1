package com.bnhp.dao;

import com.bnhp.beans.Category;
import com.bnhp.beans.Coupons;

import java.sql.SQLException;
import java.util.List;

public interface CouponsDAO {
    void addCoupon(Coupons coupon) throws SQLException;

    void updateCoupon(Coupons coupon) throws SQLException;

    void deleteCoupon(int couponId) throws SQLException;

    List<Coupons> getAllCoupons() throws SQLException;

    Coupons getOneCoupon(int couponId) throws SQLException;

    void addCouponPurchase(int customerId, int couponId) throws SQLException;

    void deleteCouponPurchase(int customerId, int couponId) throws SQLException;

    void deletePurchaseBycustomid(int customerId) throws SQLException;

    List<Coupons> GetAllCouponsByCompId(int companyId) throws SQLException;

    void deletePurchaseByCoupoId(int id) throws SQLException;

    List<Coupons> getCouponsByCategory(int id, Category category) throws SQLException;

    boolean isPurchaseExist(int id, int customerId) throws SQLException;

    List<Coupons> getAllCutomerPurchase(int customerId) throws SQLException;

    List<Coupons> gatAllCouponsBycategory(int id, Category category) throws SQLException;


    List<Coupons> getAllExpiredCoupons() throws SQLException;
}
