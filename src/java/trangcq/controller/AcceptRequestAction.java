/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import com.opensymphony.xwork2.Action;
import java.util.ArrayList;
import java.util.List;
import trangcq.request.RequestDAO;
import trangcq.request.RequestDTO;
import trangcq.requestItem.RequestItemDAO;
import trangcq.requestItem.RequestItemDTO;

/**
 *
 * @author USER
 */
public class AcceptRequestAction {
    
    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(AcceptRequestAction.class);
    
    private final String SUCCESS = "success";
    private final String FAIL = "fail";
    private int id;
    private List<String> confirmErr;
    
    public AcceptRequestAction() {
    }
    
    public String execute() throws Exception {
        try {
            String url = SUCCESS;
            
            RequestItemDAO requestItemDAO = new RequestItemDAO();
            RequestDTO request = new RequestDAO().getRequestByID(id);
            List<RequestItemDTO> result = requestItemDAO.getRequestItemByRequestID(id);
            confirmErr = new ArrayList<>();
            for (RequestItemDTO requestItemDTO : result) {
                int amount = requestItemDTO.getAmount();
                int quantity = requestItemDTO.getItemDTO().getQuantity();
                int currentBorrowQuantity = requestItemDAO.getTotalAmount(requestItemDTO.getItemId(), request.getReceiveDate(), request.getReturnDate());
                int leftQuantity = quantity - (amount + currentBorrowQuantity);
                if (leftQuantity < 0) {
                    confirmErr.add("Item: " + requestItemDTO.getItemDTO().getName() + " is invalid! (Remainings: " + (quantity - currentBorrowQuantity) + "!)");
                }
                
            }
            if (confirmErr.isEmpty()) {
                RequestDAO requestDAO = new RequestDAO();
                boolean rs = requestDAO.acceptRequest(id);
                if (rs) {
                    url = SUCCESS;
                }
            } else{
                return FAIL;
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
    
    public List<String> getConfirmErr() {
        return confirmErr;
    }
    
    public void setConfirmErr(List<String> confirmErr) {
        this.confirmErr = confirmErr;
    }
    
}
