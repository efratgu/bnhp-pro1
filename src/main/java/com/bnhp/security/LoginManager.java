package com.bnhp.security;

import com.bnhp.facades.AdminFacade;
import com.bnhp.facades.ClientFacade;
import com.bnhp.facades.CompanyFacade;
import com.bnhp.facades.CustomerFacade;

import java.sql.SQLException;

public class LoginManager {
    private static LoginManager instance = null;

    private LoginManager() {
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }

        }
        return instance;
    }

    public ClientFacade login(String email, String password, ClientType clienttype) throws SQLException {
        switch (clienttype) {
            case company:
                CompanyFacade companyfacade = new CompanyFacade();
                if (companyfacade.login(email, password)) {
                    int companyId = companyfacade.getIdByEmailAndPassword(email, password);
                    companyfacade.setCompanyId(companyId);
                    return companyfacade;
                }
                break;
            case administrator:
                AdminFacade adminFacade = new AdminFacade();
                if (adminFacade.login(email, password)) {
                    return adminFacade;
                }
                break;
            case customer: {
                CustomerFacade customerFacade = new CustomerFacade();
                if (customerFacade.login(email, password)) {
                    int customerId = customerFacade.getCustomerIdByEmailAndPassword(email, password);
                    customerFacade.setCustomerId(customerId);
                    return customerFacade;
                }


            }
            default:
                break;


        }
        return null;
    }
}
