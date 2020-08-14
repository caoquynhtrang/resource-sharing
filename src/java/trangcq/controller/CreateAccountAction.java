/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import trangcq.registration.RegistrationDAO;
import trangcq.util.MailConfig;

/**
 *
 * @author USER
 */
public class CreateAccountAction extends ActionSupport {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(CreateAccountAction.class);

    private String txtEmail, txtPassword, txtConfirm, txtFullname, txtPhone, txtAddress;
    private final String SUCCESS = "success";
    private final String FAIL = "fail";
    private String dupEmail;

    public CreateAccountAction() {
    }

    @Override
    public void validate() {
        if (txtEmail.trim().length() < 11 || txtEmail.trim().length() > 50) {
            addFieldError("txtEmail", "Email string is required from 11 to 50 characters");
        }
        if (txtPassword.trim().length() < 6 || txtPassword.trim().length() > 30) {
            addFieldError("txtPassword", "Password string is required from 6 to 30 characters");
        }
        if (!txtConfirm.trim().equals(txtPassword.trim())) {
            addFieldError("txtConfirm", "Confirm must match password");
        }
        if (txtFullname.trim().length() < 5 || txtFullname.trim().length() > 50) {
            addFieldError("txtFullname", "Fullname string is required from 5 to 50 characters");
        }
        if (!txtPhone.matches("0\\d{9}")) {
            addFieldError("txtPhone", "Phone must format (0xxxxxxxxx)");
        }
        if (txtAddress.trim().length() < 5 || txtAddress.trim().length() > 50) {
            addFieldError("txtAddress", "Address string is required from 5 to 50 characters");
        }
    }

    public String execute() throws Exception {
        try {
            Map session = (Map) ActionContext.getContext().getSession();
            String encryPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex(txtPassword);
            RegistrationDAO dao = new RegistrationDAO();

            try {
                boolean result = dao.insertAccount(txtEmail, encryPassword, txtFullname, txtPhone, txtAddress);
                if (result) {
                    int aNumber = (int) ((Math.random() * 900000) + 100000);
                    MailConfig.sendMail(txtEmail, aNumber + "");
                    session.put("RANDOM", aNumber + "");
                    session.put("EMAIL", txtEmail);
                    return SUCCESS;
                }
            } catch (Exception e) {
                if (e.getMessage().contains("duplicate")) {
                    dupEmail = "Email input is duplicated!";
                    return Action.INPUT;
                }
            }

            return FAIL;
        } catch (Exception e) {
            LOGGER.fatal(e.getMessage());
        }
        return Action.ERROR;
    }

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

    public String getTxtConfirm() {
        return txtConfirm;
    }

    public void setTxtConfirm(String txtConfirm) {
        this.txtConfirm = txtConfirm;
    }

    public String getTxtFullname() {
        return txtFullname;
    }

    public void setTxtFullname(String txtFullname) {
        this.txtFullname = txtFullname;
    }

    public String getTxtPhone() {
        return txtPhone;
    }

    public void setTxtPhone(String txtPhone) {
        this.txtPhone = txtPhone;
    }

    public String getTxtAddress() {
        return txtAddress;
    }

    public void setTxtAddress(String txtAddress) {
        this.txtAddress = txtAddress;
    }

    public String getDupEmail() {
        return dupEmail;
    }

    public void setDupEmail(String dupEmail) {
        this.dupEmail = dupEmail;
    }

}
