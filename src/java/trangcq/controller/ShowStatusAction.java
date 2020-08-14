/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import com.opensymphony.xwork2.Action;
import java.util.List;
import trangcq.status.StatusDAO;
import trangcq.status.StatusDTO;

/**
 *
 * @author USER
 */
public class ShowStatusAction {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SearchRequestHistoryAction.class);

    private final String SUCCESS = "success";
    private List<StatusDTO> listStatus;

    public ShowStatusAction() {
    }

    public String execute() throws Exception {
        try {
            StatusDAO dao = new StatusDAO();
            listStatus = dao.getAllStatus();

            return SUCCESS;
        } catch (Exception e) {
            LOGGER.fatal(e.getMessage());
        }
        return Action.ERROR;
    }

    public List<StatusDTO> getListStatus() {
        return listStatus;
    }

    public void setListStatus(List<StatusDTO> listStatus) {
        this.listStatus = listStatus;
    }

}
