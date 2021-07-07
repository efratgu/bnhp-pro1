package com.bnhp.job;

import com.bnhp.beans.Coupons;
import com.bnhp.dao.CouponsDAO;
import com.bnhp.dbdao.CouponsDBDAO;


import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class CouponExpiredDailyJob implements Runnable {
    private CouponsDAO couponDAO = new CouponsDBDAO();
    private boolean quit;

    @Override
    public void run() {
        List<Coupons> coupons = new ArrayList<>();
        ;

        while (true) {
            System.out.println("working");

            try {
                coupons = couponDAO.getAllExpiredCoupons();
              //  System.out.println("expired coupons list");
              //  System.out.println(coupons);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            for (Coupons c : coupons) {


                try {
                    couponDAO.deletePurchaseByCoupoId(c.getId());
                } catch (SQLException e) {
                    System.out.println(e.getMessage());;
                }
                try {
                    couponDAO.deleteCoupon(c.getId());
                } catch (SQLException e) {
                    System.out.println(e.getMessage());

                }

            }

            try {
              //  Thread.sleep(1000 * 60 * 60 * 24);
                   Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }

    }
}

