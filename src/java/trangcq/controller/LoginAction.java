/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import java.util.Map;
import trangcq.registration.RegistrationDAO;
import trangcq.registration.RegistrationDTO;

/**
 *
 * @author USER
 */
public class LoginAction {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(LoginAction.class);

    public static final String FAIL = "fail";
    public static final String SUCCESS = "success";

    private String txtEmail;
    private String txtPassword;

    public String getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(String txtEmail) {
        this.txtEmail = txtEmail;
    }

    public String getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(String txtPassword) {
        this.txtPassword = txtPassword;
    }

    public LoginAction() {

    }

    public String execute() throws Exception {
        try {
            String url = FAIL;
            String encryPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex(txtPassword);
            if (txtEmail != null && txtPassword != null && txtEmail.trim().length() > 0 && txtPassword.trim().length() > 0) {
                RegistrationDAO dao = new RegistrationDAO();
                RegistrationDTO result = dao.checkLogin(txtEmail, encryPassword);

                if (result != null) {
                    Map session = ActionContext.getContext().getSession();
                    session.put("USER", result);
                    url = SUCCESS;
                }

            }
            return url;
        } catch (Exception e) {
            LOGGER.fatal(e.getMessage());
        }
        return Action.ERROR;
    }

}
