/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import com.opensymphony.xwork2.Action;
import trangcq.request.RequestDAO;

/**
 *
 * @author USER
 */
public class DeleteRequestHistoryAction {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(DeleteRequestHistoryAction.class);

    private final String SUCCESS = "success";
    private final String FAIL = "fail";
    private int id;

    public DeleteRequestHistoryAction() {
    }

    public String execute() throws Exception {
        try {
            String url = FAIL;
            RequestDAO dao = new RequestDAO();
            boolean result = dao.deleteRequestHistory(id);
            if (result) {
                url = SUCCESS;
            }
            return url;
        } catch (Exception e) {
            LOGGER.fatal(e.getMessage());
        }
        return Action.ERROR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
