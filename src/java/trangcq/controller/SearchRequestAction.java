/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import com.opensymphony.xwork2.Action;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import trangcq.request.RequestDAO;
import trangcq.request.RequestDTO;

/**
 *
 * @author USER
 */
public class SearchRequestAction {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SearchRequestAction.class);

    private final String SUCCESS = "success";
    private String txtNameItem;
    private String txtFullname;
    private String txtReceiveDate;
    private int txtStatus;
    private List<RequestDTO> listRequest;

    public SearchRequestAction() {
    }

    public String execute() throws Exception {
        try {
            if (txtNameItem.isEmpty() && txtReceiveDate.isEmpty() && txtFullname.isEmpty() && txtStatus == 0) {

            } else {

                Date receiveDate = null;
                try {
                    long time = ((java.util.Date) new SimpleDateFormat("yyyy-MM-dd").parse(txtReceiveDate)).getTime();
                    receiveDate = new Date(time);
                } catch (Exception e) {
                }

                RequestDAO dao = new RequestDAO();
                listRequest = dao.searchRequest(txtNameItem, txtFullname, receiveDate, txtStatus);
            }
            return SUCCESS;
        } catch (Exception e) {
            LOGGER.fatal(e.getMessage());
        }
        return Action.ERROR;
    }

    public String getTxtNameItem() {
        return txtNameItem;
    }

    public void setTxtNameItem(String txtNameItem) {
        this.txtNameItem = txtNameItem;
    }

    public String getTxtFullname() {
        return txtFullname;
    }

    public void setTxtFullname(String txtFullname) {
        this.txtFullname = txtFullname;
    }

    public String getTxtReceiveDate() {
        return txtReceiveDate;
    }

    public void setTxtReceiveDate(String txtReceiveDate) {
        this.txtReceiveDate = txtReceiveDate;
    }

    public int getTxtStatus() {
        return txtStatus;
    }

    public void setTxtStatus(int txtStatus) {
        this.txtStatus = txtStatus;
    }

    public List<RequestDTO> getListRequest() {
        return listRequest;
    }

    public void setListRequest(List<RequestDTO> listRequest) {
        this.listRequest = listRequest;
    }

}
