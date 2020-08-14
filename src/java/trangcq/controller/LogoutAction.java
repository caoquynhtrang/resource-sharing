/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import java.util.Map;

/**
 *
 * @author USER
 */
public class LogoutAction {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(LogoutAction.class);

    private final String SUCCESS = "success";

    public LogoutAction() {
    }

    public String execute() throws Exception {
        try {
            String url = SUCCESS;
            Map session = ActionContext.getContext().getSession();
            if (session != null) {
                session.remove(this);
            }
            return url;
        } catch (Exception e) {
            LOGGER.fatal(e.getMessage());
        }
        return Action.ERROR;
    }

}
