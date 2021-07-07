package com.bnhp.dbdao;

import com.bnhp.beans.Coupons;
import com.bnhp.dao.CouponsDAO;
import com.bnhp.beans.Category;
import com.bnhp.utils.DBUtils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CouponsDBDAO implements CouponsDAO {


    private static final String QUERY_INSERT = "INSERT INTO `bnhp-pro1`.`coupons` (`id`, `company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String QUERY_UPDATE = "UPDATE `bnhp-pro1`.`coupons` SET  `company_id`=?, `category_id`=?, `title`=?, `description`=?, `start_date`=?, `end_date`=?, `amount`=?, `price`=?, `image`=? WHERE (`id` = ?);";
    private static final String QUERY_DELETE = "DELETE FROM `bnhp-pro1`.`coupons` WHERE (`id` = ?);";
    private static final String QUERY_GET_SINGLE = "SELECT * FROM `bnhp-pro1`.`coupons` WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL = "SELECT * FROM `bnhp-pro1`.`coupons`";
    private static final String QUERY_INSERT_PURCHASE = "INSERT INTO `bnhp-pro1`.`customers_vs_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?);";
    private static final String QUERY_DELETE_PURCHASE = "DELETE FROM `bnhp-pro1`.`customers_vs_coupons` WHERE (`customer_id` = ? AND `coupon_id` = ?)";

    private static final String QUERY_DELETE_PURCHASE_BY_CUSTOMID = "DELETE FROM `bnhp-pro1`.`customers_vs_coupons` WHERE (`customer_id` = ?);";
    private static final String QUERY_GET_ALL_COUPONS_BY_COMPID = "SELECT * FROM `bnhp-pro1`.`coupons` WHERE (`company_id` = ?);";
    private static final String QUERY_DELETE_PURCHASE_BY_COUPONID = "DELETE FROM `bnhp-pro1`.`customers_vs_coupons` WHERE (`coupon_id` = ?);";
    private static final String QUERY_GET_ALL_COUPONS_BY_CATEGORY = "SELECT * FROM `bnhp-pro1`.`coupons` WHERE (`company_id` = ? AND `category_id` = ?);";
    private static final String QUERY_IS_EXIST_PURCHASE = "SELECT * FROM `bnhp-pro1`.`customers_vs_coupons` WHERE (`coupon_id` = ? AND `customer_id` = ?);";
    private static final String QUERY_GET_ALL_CUSTOMER_PURCHASE = "SELECT  s2.id,s2.company_id,s2.category_id,s2.title,s2.description,s2.start_date,s2.end_date,s2.amount,s2.price,s2.image" +
            " FROM `bnhp-pro1`.customers_vs_coupons s1\n" +
            "left join \n" +
            "`bnhp-pro1`.coupons s2\n" +
            "on s1.coupon_id  = s2.id\n" +
            "where (s1.customer_id = ?)";
    private static final String QUERY_GET_ALL_CUSTOMER_PURCHASE_BY_CATEGORY = "SELECT  s2.id,s2.company_id,s2.category_id,s2.title,s2.description,s2.start_date,s2.end_date,s2.amount,s2.price,s2.image" +
            "             FROM `bnhp-pro1`.customers_vs_coupons s1\n" +
            "            left join\n" +
            "            `bnhp-pro1`.coupons s2\n" +
            "            on s1.coupon_id  = s2.id\n" +
            "            where (s1.`customer_id`= ? AND s2.`category_id` = ? )";
    private static final String QUERY_GET_ALL_EXPIRED_COUPONS = "SELECT * FROM `bnhp-pro1`.coupons\n" +
            "where end_date < current_date();";


    @Override
    public void addCoupon(Coupons coupon) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, coupon.getId());
        map.put(2, coupon.getCompanyId());
        map.put(3, coupon.getCategory().ordinal() + 1);
        map.put(4, coupon.getTitle());
        map.put(5, coupon.getDescription());
        map.put(6, coupon.getStartDate());
        map.put(7, coupon.getEndDate());
        map.put(8, coupon.getAmount());
        map.put(9, coupon.getPrice());
        map.put(10, coupon.getImage());
        DBUtils.runStatement(QUERY_INSERT, map);

    }

    @Override
    public void updateCoupon(Coupons coupon) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();

        map.put(1, coupon.getCompanyId());
        map.put(2, coupon.getCategory().ordinal() + 1);
        map.put(3, coupon.getTitle());
        map.put(4, coupon.getDescription());
        map.put(5, coupon.getStartDate());
        map.put(6, coupon.getEndDate());
        map.put(7, coupon.getAmount());
        map.put(8, coupon.getPrice());
        map.put(9, coupon.getImage());
        map.put(10, coupon.getId());
        DBUtils.runStatement(QUERY_UPDATE, map);

    }

    @Override
    public void deleteCoupon(int couponId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, couponId);
        DBUtils.runStatement(QUERY_DELETE, map);

    }

    @Override
    public List<Coupons> getAllCoupons() throws SQLException {
        List<Coupons> coupons = new ArrayList<>();

        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ALL);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            int companyid = resultSet.getInt(2);
            int categoryid = resultSet.getInt(3) - 1;
            String title = resultSet.getString(4);
            String description = resultSet.getString(5);
            Date startdate = resultSet.getDate(6);
            Date endtdate = resultSet.getDate(7);
            int amount = resultSet.getInt(8);
            double price = resultSet.getDouble(9);
            String image = resultSet.getString(10);

            Coupons c1 = new Coupons(id, companyid, Category.values()[categoryid], title, description, startdate, endtdate, amount, price, image);
            coupons.add(c1);
        }
        return coupons;
    }

    @Override
    public Coupons getOneCoupon(int couponId) throws SQLException {
        Coupons result = null;

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, couponId);

        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_SINGLE, map);
        try {
            resultSet.next();

            int companyid = resultSet.getInt(2);
            int categoryid = resultSet.getInt(3) - 1;
            String title = resultSet.getString(4);
            String description = resultSet.getString(5);
            Date startdate = resultSet.getDate(6);
            Date endtdate = resultSet.getDate(7);
            int amount = resultSet.getInt(8);
            double price = resultSet.getDouble(9);
            String image = resultSet.getString(10);

            result = new Coupons(couponId, companyid, Category.values()[categoryid], title, description, startdate, endtdate, amount, price, image);
            return result;
        } catch (SQLException E) {
            return null;
        }


    }

    @Override
    public void addCouponPurchase(int customerId, int couponId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        map.put(2, couponId);
        DBUtils.runStatement(QUERY_INSERT_PURCHASE, map);

    }

    @Override
    public void deleteCouponPurchase(int customerId, int couponId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        map.put(2, couponId);
        DBUtils.runStatement(QUERY_DELETE_PURCHASE, map);
    }


    @Override
    public void deletePurchaseBycustomid(int customerId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);

        DBUtils.runStatement(QUERY_DELETE_PURCHASE_BY_CUSTOMID, map);
    }

    @Override
    public List<Coupons> GetAllCouponsByCompId(int companyId) throws SQLException {
        List<Coupons> coupons = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ALL_COUPONS_BY_COMPID, map);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            int companyid = resultSet.getInt(2);
            int categoryid = resultSet.getInt(3) - 1;
            String title = resultSet.getString(4);
            String description = resultSet.getString(5);
            Date startdate = resultSet.getDate(6);
            Date endtdate = resultSet.getDate(7);
            int amount = resultSet.getInt(8);
            double price = resultSet.getDouble(9);
            String image = resultSet.getString(10);

            Coupons c1 = new Coupons(id, companyid, Category.values()[categoryid], title, description, startdate, endtdate, amount, price, image);
            coupons.add(c1);
        }
        return coupons;
    }

    @Override
    public void deletePurchaseByCoupoId(int id) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);

        DBUtils.runStatement(QUERY_DELETE_PURCHASE_BY_COUPONID, map);
    }

    @Override
    public List<Coupons> getCouponsByCategory(int id, Category category) throws SQLException {
        List<Coupons> coupons = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<>();

        map.put(1, id);
        map.put(2, category.ordinal() + 1);
        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ALL_COUPONS_BY_CATEGORY, map);
        while (resultSet.next()) {
            int couponid = resultSet.getInt(1);
            int companyid = resultSet.getInt(2);
            int categoryid = resultSet.getInt(3) - 1;
            String title = resultSet.getString(4);
            String description = resultSet.getString(5);
            Date startdate = resultSet.getDate(6);
            Date endtdate = resultSet.getDate(7);
            int amount = resultSet.getInt(8);
            double price = resultSet.getDouble(9);
            String image = resultSet.getString(10);

            Coupons c1 = new Coupons(id, companyid, Category.values()[categoryid], title, description, startdate, endtdate, amount, price, image);
            coupons.add(c1);
        }
        return coupons;
    }

    @Override
    public boolean isPurchaseExist(int id, int customerId) throws SQLException {
        boolean result = false;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);
        map.put(2, customerId);

        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_IS_EXIST_PURCHASE, map);
        try {
            resultSet.next();
            int coupid = resultSet.getInt(1);
            if (coupid != 0) {
                result = true;

            }

        } catch (SQLException e) {

            return false;
        }


        return result;
    }

    @Override
    public List<Coupons> getAllCutomerPurchase(int customerId) throws SQLException {
        List<Coupons> coupons = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ALL_CUSTOMER_PURCHASE, map);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            int companyid = resultSet.getInt(2);
            int categoryid = resultSet.getInt(3) - 1;
            String title = resultSet.getString(4);
            String description = resultSet.getString(5);
            Date startdate = resultSet.getDate(6);
            Date endtdate = resultSet.getDate(7);
            int amount = resultSet.getInt(8);
            double price = resultSet.getDouble(9);
            String image = resultSet.getString(10);

            Coupons c1 = new Coupons(id, companyid, Category.values()[categoryid], title, description, startdate, endtdate, amount, price, image);
            coupons.add(c1);
        }
        return coupons;


    }


    @Override
    public List<Coupons> gatAllCouponsBycategory(int id, Category category) throws SQLException {
        List<Coupons> coupons = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, id);
        map.put(2, category.ordinal() + 1);

        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ALL_CUSTOMER_PURCHASE_BY_CATEGORY, map);
        while (resultSet.next()) {
            int couponid = resultSet.getInt(1);
            int companyid = resultSet.getInt(2);
            int categoryid = resultSet.getInt(3) - 1;
            String title = resultSet.getString(4);
            String description = resultSet.getString(5);
            Date startdate = resultSet.getDate(6);
            Date endtdate = resultSet.getDate(7);
            int amount = resultSet.getInt(8);
            double price = resultSet.getDouble(9);
            String image = resultSet.getString(10);

            Coupons c1 = new Coupons(couponid, companyid, Category.values()[categoryid], title, description, startdate, endtdate, amount, price, image);
            coupons.add(c1);
        }
        return coupons;
    }

    @Override
    public List<Coupons> getAllExpiredCoupons() throws SQLException {
        List<Coupons> coupons = new ArrayList<>();


        ResultSet resultSet = DBUtils.runStatementWithResultSet(QUERY_GET_ALL_EXPIRED_COUPONS);
        while (resultSet.next()) {
            int couponid = resultSet.getInt(1);
            int companyid = resultSet.getInt(2);
            int categoryid = resultSet.getInt(3) - 1;
            String title = resultSet.getString(4);
            String description = resultSet.getString(5);
            Date startdate = resultSet.getDate(6);
            Date endtdate = resultSet.getDate(7);
            int amount = resultSet.getInt(8);
            double price = resultSet.getDouble(9);
            String image = resultSet.getString(10);

            Coupons c1 = new Coupons(couponid, companyid, Category.values()[categoryid], title, description, startdate, endtdate, amount, price, image);
            coupons.add(c1);
        }
        return coupons;
    }


}
