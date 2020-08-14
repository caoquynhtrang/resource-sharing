/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import com.opensymphony.xwork2.Action;
import java.util.List;
import trangcq.requestItem.RequestItemDAO;
import trangcq.requestItem.RequestItemDTO;

/**
 *
 * @author USER
 */
public class ShowItemDetailAction {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SearchRequestHistoryAction.class);

    private final String SUCCESS = "success";
    private int id;
    private List<RequestItemDTO> listRequestItemDetail;

    public ShowItemDetailAction() {
    }

    public String execute() throws Exception {
        try {
            RequestItemDAO dao = new RequestItemDAO();
            listRequestItemDetail = dao.getRequestItemByRequestID(id);

            return SUCCESS;
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

    public List<RequestItemDTO> getListRequestItemDetail() {
        return listRequestItemDetail;
    }

    public void setListRequestItemDetail(List<RequestItemDTO> listRequestItemDetail) {
        this.listRequestItemDetail = listRequestItemDetail;
    }

}
