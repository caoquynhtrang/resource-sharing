/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import java.util.List;
import java.util.Map;
import trangcq.registration.RegistrationDTO;
import trangcq.request.RequestDAO;
import trangcq.request.RequestDTO;

/**
 *
 * @author USER
 */
public class ShowRequestHistoryAction {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SearchRequestHistoryAction.class);

    private final String SUCCESS = "success";

    private List<RequestDTO> listRequestHistory;

    public ShowRequestHistoryAction() {
    }

    public String execute() throws Exception {
        try {
            Map session = ActionContext.getContext().getSession();
            RegistrationDTO userDTO = (RegistrationDTO) session.get("USER");
            RequestDAO dao = new RequestDAO();
            listRequestHistory = dao.getAllRequestByUserID(userDTO.getId());

            return SUCCESS;
        } catch (Exception e) {
            LOGGER.fatal(e.getMessage());
        }
        return Action.ERROR;
    }

    public List<RequestDTO> getListRequestHistory() {
        return listRequestHistory;
    }

    public void setListRequestHistory(List<RequestDTO> listRequestHistory) {
        this.listRequestHistory = listRequestHistory;
    }

}
