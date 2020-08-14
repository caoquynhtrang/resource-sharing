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

/**
 *
 * @author USER
 */
public class SendCodeMailAction {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SearchRequestHistoryAction.class);

    private final String SUCCESS = "success";
    private final String FAIL = "fail";
    private String txtCodeEmail;

    public SendCodeMailAction() {
    }

    public String execute() throws Exception {
        try {
            String url = FAIL;
            Map session = (Map) ActionContext.getContext().getSession();
            String codeEmail = (String) session.get("RANDOM");
            String email = (String) session.get("EMAIL");

            if (txtCodeEmail.equalsIgnoreCase(codeEmail)) {
                RegistrationDAO dao = new RegistrationDAO();
                boolean result = dao.updateStatusInAccount(email);
                if (result) {
                    url = SUCCESS;
                    session.remove("RANDOM");
                    session.remove("EMAIL");
                }

            }

            return url;
        } catch (Exception e) {
            LOGGER.fatal(e.getMessage());
        }   
        return Action.ERROR;
    }

    public String getTxtCodeEmail() {
        return txtCodeEmail;
    }

    public void setTxtCodeEmail(String txtCodeEmail) {
        this.txtCodeEmail = txtCodeEmail;
    }

}
