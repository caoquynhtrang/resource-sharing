/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import trangcq.registration.RegistrationDTO;
import trangcq.request.RequestDAO;
import trangcq.request.RequestDTO;

/**
 *
 * @author USER
 */
public class SearchRequestHistoryAction {
    
    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SearchRequestHistoryAction.class);
    
    private final String SUCCESS = "success";
    private String txtNameItem;
    private String txtImportedDate;
    private List<RequestDTO> listRequestHistory;
    
    public SearchRequestHistoryAction() {
    }
    
    public String execute() throws Exception {
        try {
            if (txtNameItem.isEmpty() && txtImportedDate.isEmpty()) {
                
            } else {
                
                Date importedDate = null;
                try {
                    long time = ((java.util.Date) new SimpleDateFormat("yyyy-MM-dd").parse(txtImportedDate)).getTime();
                    importedDate = new Date(time);
                } catch (Exception e) {
                }
                
                RequestDAO dao = new RequestDAO();
                Map session = ActionContext.getContext().getSession();
                RegistrationDTO userInfo = (RegistrationDTO) session.get("USER");
                if(userInfo != null){
                    listRequestHistory = dao.searchRequestHistory(txtNameItem, importedDate, userInfo.getId());
                    return SUCCESS;
                }
                
            }
            
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
    
    public String getTxtImportedDate() {
        return txtImportedDate;
    }
    
    public void setTxtImportedDate(String txtImportedDate) {
        this.txtImportedDate = txtImportedDate;
    }
    
    public List<RequestDTO> getListRequestHistory() {
        return listRequestHistory;
    }
    
    public void setListRequestHistory(List<RequestDTO> listRequestHistory) {
        this.listRequestHistory = listRequestHistory;
    }
    
}
