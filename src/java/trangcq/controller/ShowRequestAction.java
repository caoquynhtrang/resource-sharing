/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import com.opensymphony.xwork2.Action;
import java.util.List;
import trangcq.request.RequestDAO;
import trangcq.request.RequestDTO;

/**
 *
 * @author USER
 */
public class ShowRequestAction {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SearchRequestHistoryAction.class);

    private final String SUCCESS = "success";
    private List<RequestDTO> listRequest;
    private int page;
    private int[] pageArr;

    public ShowRequestAction() {
    }

    public String execute() throws Exception {
        try {
            RequestDAO requestDAO = new RequestDAO();
            if (page == 0) {
                listRequest = requestDAO.getAllRequestPaging();
            } else {
                listRequest = requestDAO.getAllRequestPaging(page);
            }
            int totalRequest = requestDAO.countTotalRequest();
            int pageNumber = (int) Math.ceil((double) totalRequest / 20);
            pageArr = new int[pageNumber];

            return SUCCESS;
        } catch (Exception e) {
            LOGGER.fatal(e.getMessage());
        }
        return Action.ERROR;

    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int[] getPageArr() {
        return pageArr;
    }

    public void setPageArr(int[] pageArr) {
        this.pageArr = pageArr;
    }

    public List<RequestDTO> getListRequest() {
        return listRequest;
    }

    public void setListRequest(List<RequestDTO> listRequest) {
        this.listRequest = listRequest;
    }

}
