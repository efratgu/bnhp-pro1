package com.bnhp;

import com.bnhp.beans.Category;
import com.bnhp.beans.Company;
import com.bnhp.beans.Coupons;
import com.bnhp.beans.Customer;
import com.bnhp.dao.CategoryDAO;
import com.bnhp.dao.CompanyDAO;
import com.bnhp.dao.CouponsDAO;
import com.bnhp.dao.CustomersDAO;
import com.bnhp.db.DatabaseManager;
import com.bnhp.dbdao.CategoryDBDAO;
import com.bnhp.dbdao.CompanyDBDAO;
import com.bnhp.dbdao.CouponsDBDAO;
import com.bnhp.dbdao.CustomersDBDAO;
import com.bnhp.exceptions.CouponsSystemexception;
import com.bnhp.facades.AdminFacade;
import com.bnhp.facades.CompanyFacade;
import com.bnhp.facades.CustomerFacade;
import com.bnhp.job.CouponExpiredDailyJob;
import com.bnhp.security.ClientType;
import com.bnhp.security.LoginManager;
import com.bnhp.utils.ArtUtils;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;


public class Test {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        System.out.println("START");
        DatabaseManager.dropAndCreate();
       System.out.println("----------------------start thread-----------------------");Thread thread = new Thread(new CouponExpiredDailyJob());
       thread.setDaemon(true);
        thread.start();


        CompanyDAO companyDAO = new CompanyDBDAO();

        System.out.println(ArtUtils.INSERT);

        companyDAO.addCompany(new Company( "greg", "g@gmail.com", "123"));
        companyDAO.addCompany(new Company( "ksp", "k@gmail.com", "124"));
        companyDAO.addCompany(new Company( "isrotel", "i@gmail.com", "125"));

        System.out.println("---------------------print companies---------------------");
        companyDAO.getAllCompanies().forEach(System.out::println);

        System.out.println(ArtUtils.UPDATE);
        Company toUpdate = companyDAO.getOneCompany(1);
        toUpdate.setName("kafekafe");
        companyDAO.updateCompany(toUpdate);
        companyDAO.getAllCompanies().forEach(System.out::println);

        System.out.println(ArtUtils.DELETE);
        companyDAO.deleteCompany(3);
        companyDAO.getAllCompanies().forEach(System.out::println);

        System.out.println(ArtUtils.GET_SINGLE);
        System.out.println(companyDAO.getOneCompany(2));


        System.out.println(ArtUtils.GET_ALL);
        companyDAO.getAllCompanies().forEach(System.out::println);
        System.out.println("is exist");
        try {
            System.out.println(companyDAO.isCompanyExist("k@gmail.com", "127"));
        } catch (Exception e) {
            System.out.println(false);

        }
        System.out.println("is exist");
        try {
            System.out.println(companyDAO.isCompanyExist("k@gmail.com", "124"));
        } catch (Exception e) {
            System.out.println(false);

        }
        CustomersDAO customerDAO = new CustomersDBDAO();

        System.out.println(ArtUtils.INSERT);

        customerDAO.addCustumer(new Customer( "efrat", "gutman", "eg@gmail.com", "111"));
        customerDAO.addCustumer(new Customer("avraham", "iczkovits", "ai@gmail.com", "222"));
        customerDAO.addCustumer(new Customer( "shira", "haish", "sh@gmail.com", "333"));
        customerDAO.getAllcustumers().forEach(System.out::println);

        System.out.println(ArtUtils.UPDATE);
        Customer toUpdate2 = customerDAO.getOnecustumer(3);

        toUpdate2.setFirstName("sara");

        customerDAO.updateCustumer(toUpdate2);
        customerDAO.getAllcustumers().forEach(System.out::println);

        System.out.println(ArtUtils.DELETE);
        customerDAO.deleteCustumer(1);
        customerDAO.getAllcustumers().forEach(System.out::println);

        System.out.println(ArtUtils.GET_SINGLE);
        System.out.println(customerDAO.getOnecustumer(2));


        System.out.println(ArtUtils.GET_ALL);
        customerDAO.getAllcustumers().forEach(System.out::println);
        System.out.println("is exist");
        try {
            System.out.println(customerDAO.isCustumerExist("k@gmail.com", "127"));
        } catch (Exception e) {
            System.out.println(false);

        }
        System.out.println("is exist");
        try {
            System.out.println(customerDAO.isCustumerExist("sh@gmail.com", "333"));
        } catch (Exception e) {
            System.out.println(false);

        }
        CategoryDAO categorydao = new CategoryDBDAO();
        System.out.println(ArtUtils.INSERT);
        categorydao.addCategory(Category.food.ordinal() + 1, Category.food.name());
        categorydao.addCategory(Category.electricity.ordinal() + 1, Category.electricity.name());
        categorydao.addCategory(Category.restaurant.ordinal() + 1, Category.restaurant.name());
        categorydao.addCategory(Category.vacation.ordinal() + 1, Category.vacation.name());
        CouponsDAO couponsdao = new CouponsDBDAO();

        System.out.println(ArtUtils.INSERT);


        couponsdao.addCoupon(new Coupons( 1, Category.food, "coffee", "free coffee & cookies", Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusDays(12)), 20, 10, "hikjhgkjkjh"));
        couponsdao.addCoupon(new Coupons( 2, Category.food, "coca-cola", "one+one", Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusDays(12)), 20, 10, "hikjhgkjkjh"));
        couponsdao.addCoupon(new Coupons( 1, Category.electricity, "mobile", "galaxy s11", Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusDays(100)), 100, 1000, "dsdasa"));
        couponsdao.addCoupon(new Coupons( 2, Category.restaurant, "kafekafe", "dinner", Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusDays(365)), 34, 80, "hikjhgkjkjh"));
        couponsdao.addCoupon(new Coupons( 2, Category.vacation, "hotel", "eilat", Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusDays(44)), 34, 1000, "hikjhdddkjkjh"));

        couponsdao.addCoupon(new Coupons( 1, Category.electricity, "ksp", "i-robot", Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().minusDays(44)), 88, 1000, "hikjhdddkjkjh"));
        couponsdao.addCoupon(new Coupons( 3, Category.electricity, "phones", "i-phon", Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().minusDays(20)), 8, 1000, "hikjhdddkjkjh"));

        couponsdao.getAllCoupons().forEach(System.out::println);


        System.out.println(ArtUtils.UPDATE);
        Coupons toUpdate3 = couponsdao.getOneCoupon(3);

        toUpdate3.setTitle("joe coffee");

        couponsdao.updateCoupon(toUpdate3);
        couponsdao.getAllCoupons().forEach(System.out::println);


        System.out.println(ArtUtils.DELETE);
        couponsdao.deleteCoupon(3);

        couponsdao.getAllCoupons().forEach(System.out::println);

        System.out.println(ArtUtils.GET_SINGLE);
        System.out.println(couponsdao.getOneCoupon(2));


        System.out.println(ArtUtils.GET_ALL);
        couponsdao.getAllCoupons().forEach(System.out::println);
        System.out.println(ArtUtils.INSERT);
        couponsdao.addCouponPurchase(2, 2);
        couponsdao.addCouponPurchase(3, 1);
        couponsdao.addCouponPurchase(2, 5);
        couponsdao.addCouponPurchase(2, 4);
        System.out.println(ArtUtils.DELETE);
        couponsdao.deleteCouponPurchase(2, 2);

       System.out.println("--------------------login admin facade-------------------------");


        LoginManager loginManager = LoginManager.getInstance();
        System.out.println("--------------------try wrong  email+password-------------------------");

       AdminFacade adminFacade1 = (AdminFacade) loginManager.login("ggg@admin", "ad", ClientType.administrator);
        if (adminFacade1 == null) {
            System.out.println("wrong email or password");

        }
        else{
            System.out.println("---------------login success------------------------------- ");
        }
        System.out.println("--------------------try  good email+password-------------------------");
        AdminFacade adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin", ClientType.administrator);

        if (adminFacade == null) {
            System.out.println("wrong email or password");

        }
        else{
            System.out.println("---------------login success------------------------------- ");
        }

            System.out.println("--------------------try add same name------------------- ");
            Company comp1 = new Company("kafekafe","gg@gmail.com","121");
           try {
                adminFacade.addCompany(comp1);
           } catch (CouponsSystemexception e) {
               System.out.println(e.getMessage());;
            }
            System.out.println("---------------------try add same email----------------------");
            Company comp2 = new Company("joe","k@gmail.com","121");
            try {
                adminFacade.addCompany(comp2);
            } catch (CouponsSystemexception e) {
               System.out.println(e.getMessage());;
            }
        System.out.println("----------------------try add new company-----------------------");
        Company comp3 = new Company( "isrotel", "i@gmail.com", "434");
        try {
            adminFacade.addCompany(comp3);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());
            ;
        }
            System.out.println(adminFacade.getAllCompanies());
              System.out.println("----------------try update company  code------------------");
            Company comp4 = new Company(5,"kafekafe","kafe@gmail.com","123");
             try {
            adminFacade.updateCompany(comp4);
             } catch (CouponsSystemexception couponsSystemexception) {
                System.out.println(couponsSystemexception.getMessage());
             }
             System.out.println("try update company name");
             Company comp5 = new Company(1,"fdfds","kafe@gmail.com","123");
            try {
               adminFacade.updateCompany(comp5);
            } catch (CouponsSystemexception couponsSystemexception) { System.out.println(couponsSystemexception.getMessage());
             }
             System.out.println("try update company password");
             Company comp6 = new Company(2,"ksp","k@gmail.com","999");
             try {
                adminFacade.updateCompany(comp6);
             } catch (CouponsSystemexception couponsSystemexception) {
                 System.out.println(couponsSystemexception.getMessage()); }
        System.out.println("-------------print all companies--------------------------------");
            System.out.println(adminFacade.getAllCompanies());
            Company company1 = adminFacade.getOneCompany(1);
             System.out.println("delete company 1");
                 adminFacade.deleteCompany(1);
        System.out.println("-------------add company 1--------------------------------");
        try {
            adminFacade.addCompany(company1);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());;
        }
        System.out.println(adminFacade.getAllCompanies());
        System.out.println(adminFacade.getOneCompany(1));
          System.out.println("new customer with same email");
          Customer cust1 = new Customer("dd","ddd","ai@gmail.com","444");
            try {
               adminFacade.addCustomer(cust1);
           } catch (CouponsSystemexception e) {
               System.out.println(e.getMessage());
           }
        System.out.println("new customer ");
        Customer cust2 = new Customer("beny", "yuda", "by@gmail.com", "444");
        try {
            adminFacade.addCustomer(cust2);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(adminFacade.getAllCustomers());
        System.out.println(adminFacade.getOneCompany(1));
        System.out.println("update customer code");
        Customer cust3 = new Customer(1, "beny", "yuda", "by@gmail.com", "444");
        try {
            adminFacade.updateCustomer(cust3);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("update customer ");
        Customer cust4 = new Customer(4, "binyamin", "yuda", "by@gmail.com", "444");
        try {
            adminFacade.updateCustomer(cust4);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("print all customers");
        System.out.println(adminFacade.getAllCustomers());
        System.out.println("get one customers");
          Customer customer3 = adminFacade.getOneCustomer(3);
        System.out.println(customer3);
       System.out.println("delete customer 3");
           adminFacade.deleteCustomer(3);
        System.out.println("new customer 3");
                try {
            adminFacade.addCustomer(customer3);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("login company facade");

        LoginManager loginManager1 = LoginManager.getInstance();
        System.out.println("-------------------login wrong email-----------------------");
           CompanyFacade companyFacade1= (CompanyFacade) loginManager.login("com.admin@admin","admin",ClientType.company);
           if(companyFacade1==null){
               System.out.println("wrong email or password");

           }
        System.out.println("-------------------login good email-----------------------");
        CompanyFacade companyFacade = (CompanyFacade) loginManager.login("g@gmail.com", "123", ClientType.company);
        if (companyFacade == null) {
            System.out.println("wrong email or password");

        }
        System.out.println("------------------login success----------------------------- ");
        System.out.println("------------------add good coupon --------------------------");
        Coupons coup1 = couponsdao.getOneCoupon(2);
        coup1.setId(1);
        coup1.setCompanyId(1);
        coup1.setTitle("golda");
        coup1.setDescription("ice cream");
        coup1.setPrice(90);
        coup1.setCategory(Category.restaurant);
        System.out.println(coup1);
        try {
            companyFacade.addCoupon(coup1);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());
        }
           System.out.println("--------------------add coupon with same title--------------");
          Coupons coup = couponsdao.getOneCoupon(1);

             coup.setId(6);

           coup.setPrice(90);

           try {
               companyFacade.addCoupon(coup);
           } catch (CouponsSystemexception e) {
                System.out.println(e.getMessage());
            }

        System.out.println(companyFacade.getCompanyCoupons());
        System.out.println("---------add  expired coupon for thread check -----------");
        Coupons coup8 = couponsdao.getOneCoupon(2);
        coup8.setId(3);
        coup8.setCompanyId(1);
        coup8.setTitle("breakfast");
        coup8.setDescription("sandwich");
        coup8.setPrice(90);
        coup8.setEndDate(Date.valueOf(LocalDate.now().minusDays(90)));
        coup8.setCategory(Category.restaurant);
        System.out.println(companyFacade.getCompanyId());
        try {
            companyFacade.addCoupon(coup8);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(coup8);
        System.out.println("--------------another coupon----------------------------");
        Coupons coup2 = couponsdao.getOneCoupon(2);
        coup2.setId(7);
        coup2.setCompanyId(1);
        coup2.setCategory(Category.vacation);
        coup2.setTitle("plaza");
        coup2.setDescription("free breakfast");
        coup2.setEndDate(Date.valueOf(LocalDate.now().minusDays(30)));
        coup2.setPrice(3);
        System.out.println(coup2);
        try {
            companyFacade.addCoupon(coup2);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("another coupon same title like other company");
        Coupons coup3 = couponsdao.getOneCoupon(2);
        coup3.setCompanyId(1);

        coup3.setId(8);
        System.out.println(coup3);
        try {
            companyFacade.addCoupon(coup3);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("update code coupon");
        coup3.setId(10);
        try {
            companyFacade.updateCoupon(coup3);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("update company code ");
        coup3.setId(9);
        coup3.setCompanyId(3);
        try {
            companyFacade.updateCoupon(coup3);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("update copon ");
        Coupons coup4 = couponsdao.getOneCoupon(1);
        coup4.setTitle("nestle");
        coup4.setEndDate(Date.valueOf(LocalDate.now().minusDays(40)));
        try {
            companyFacade.updateCoupon(coup4);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());
        }
       System.out.println("delete coupon");
        try {
            companyFacade.deleteCoupon(1);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(companyFacade.getCompanyCoupons());
       System.out.println("add coupon ");
        Coupons coup5 = new Coupons( 1, Category.values()[3], "eilat", "boat", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(40)), 90, 8, "hjghjghj");

        try {
            companyFacade.addCoupon(coup5);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());
        }


        System.out.println("copons from 1 category");
        System.out.println(companyFacade.getCompanyCoupons(Category.vacation));
        System.out.println("max price 9");
        System.out.println(companyFacade.getCompanyCoupons(9));
        System.out.println("company details");
        System.out.println(companyFacade.getcompanydetails());
        System.out.println("-------------------login customer facade-----------------------");
        LoginManager loginManager2 = LoginManager.getInstance();
        System.out.println("-------------------login wrong email & password-----------------------");
        CustomerFacade customerFacade1 = (CustomerFacade) loginManager.login("ia@gmail.com", "222", ClientType.customer);
        if (customerFacade1 == null) {
            System.out.println("wrong email or password");

        }
        else {
            System.out.println("login success ");
        }
        System.out.println("-------------------login good email & password-----------------------");
        CustomerFacade customerFacade = (CustomerFacade) loginManager.login("ai@gmail.com", "222", ClientType.customer);
        if (customerFacade == null) {
            System.out.println("wrong email or password");

        }
        else {
            System.out.println("login success ");
        }

       System.out.println("purchase coupon first time");
        Coupons coupon2 = couponsdao.getOneCoupon(2);
        try {
            customerFacade.purchaseCoupon(coupon2);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());
            ;
        }

        System.out.println("buy twice");
        try {
            customerFacade.purchaseCoupon(coupon2);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());
            ;
        }
        coupon2.setEndDate(Date.valueOf(LocalDate.now().minusDays(40)));
        couponsdao.updateCoupon(coupon2);
        System.out.println("buy zero amount");
        Coupons cop6 = couponsdao.getOneCoupon(8);
        cop6.setAmount(0);
        couponsdao.updateCoupon(cop6);
        try {
            customerFacade.purchaseCoupon(cop6);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());
            ;
        }
          System.out.println("buy expired coupon ");

        Coupons cop7 = couponsdao.getOneCoupon(9);
           cop7.setEndDate(Date.valueOf(LocalDate.now().minusDays(40)));
           couponsdao.updateCoupon(cop7);
           try {
               customerFacade.purchaseCoupon(cop7);
           } catch (CouponsSystemexception e) {
               System.out.println(e.getMessage());;
          }
        System.out.println("purchase coupon");
        cop7.setEndDate(Date.valueOf(LocalDate.now().plusDays(40)));
        couponsdao.updateCoupon(cop7);
        try {
            customerFacade.purchaseCoupon(cop7);
        } catch (CouponsSystemexception e) {
            System.out.println(e.getMessage());
            ;
        }

        System.out.println("print all coupons of customer");
        System.out.println(customerFacade.getCustomerCoupons());
        System.out.println("print coupon from category");
        System.out.println(customerFacade.getCustomerCouponsbyCategory(Category.vacation));
        System.out.println("print coupons of customer with max price");
        System.out.println(customerFacade.getCustomerCoupons(20));
        System.out.println("print customer details");
        System.out.println(customerFacade.getcustomerdetails());


        System.out.println("end");


 }
}