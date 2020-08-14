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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import trangcq.cart.CartObject;
import trangcq.item.ItemDTO;
import trangcq.registration.RegistrationDTO;
import trangcq.request.RequestDAO;
import trangcq.request.RequestDTO;
import trangcq.requestItem.RequestItemDAO;
import trangcq.requestItem.RequestItemDTO;

/**
 *
 * @author USER
 */
public class ConfirmItemAction {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ConfirmItemAction.class);

    private final String SUCCESS = "success";
    private final String FAIL = "fail";
    private String txtReceiveDate;
    private String txtReturnDate;
    private List<String> confirmError;

    public ConfirmItemAction() {
    }

    public String execute() throws Exception {
        try {
            String url = FAIL;
            Date receiveDate = null;
            Date returnDate = null;
            if (txtReceiveDate.isEmpty() && txtReturnDate.isEmpty()) {

            } else {

                try {
                    long time = ((java.util.Date) new SimpleDateFormat("yyyy-MM-dd").parse(txtReceiveDate)).getTime();
                    receiveDate = new Date(time);
                } catch (Exception e) {
                }

                try {
                    long time = new SimpleDateFormat("yyyy-MM-dd").parse(txtReturnDate).getTime();
                    returnDate = new Date(time);
                    if (receiveDate != null && receiveDate.compareTo(returnDate) > 0) {
                        returnDate = null;
                    }
                } catch (Exception e) {
                }
            }
            Map session = ActionContext.getContext().getSession();
            if (session != null) {
                CartObject cart = (CartObject) session.get("CART");
                RegistrationDTO userDTO = (RegistrationDTO) session.get("USER");
                if (cart != null) {
                    Map<Integer, Integer> items = cart.getItems();
                    Map<Integer, ItemDTO> borrowItem = cart.getBorrowItem();
                    RequestDAO requestDAO = new RequestDAO();
                    RequestItemDAO requestItemDAO = new RequestItemDAO();
                    confirmError = new ArrayList<>();

                    for (Integer id : items.keySet()) {
                        int totalAmount = requestItemDAO.getTotalAmount(id, receiveDate, returnDate);
                        int amount = items.get(id);

                        ItemDTO itemInfo = borrowItem.get(id);
                        int quantity = itemInfo.getQuantity();

                        int leftQuantity = quantity - totalAmount;
                        if (leftQuantity - amount < 0) {
                            confirmError.add("Item: " + itemInfo.getName() + " is invalid! (Remainings: " + leftQuantity + "!)");
                        }
                    }

                    if (confirmError.isEmpty()) {
                        RequestDTO requestDTO = new RequestDTO(returnDate, receiveDate, userDTO.getId(), 3);
                        if (items != null) {
                            int idRequestInsert = requestDAO.insertRequest(requestDTO);
                            requestDTO.setId(idRequestInsert);
                            if (idRequestInsert != -1) {
                                Set<Integer> keyList = items.keySet();

                                for (Integer id : keyList) {
                                    RequestItemDTO requestItemDTO = new RequestItemDTO(items.get(id), id, idRequestInsert);
                                    requestItemDAO.insertRequestItem(requestItemDTO);
                                }
                                url = SUCCESS;
                                session.put("CART", cart);
                                //session.remove("CART");
                            }
                        }
                    }
                }
            }

            return url;
        } catch (Exception e) {
            LOGGER.fatal(e.getMessage());
        }
        return Action.ERROR;
    }

    public List<String> getConfirmError() {
        return confirmError;
    }

    public void setConfirmError(List<String> confirmError) {
        this.confirmError = confirmError;
    }

    public String getTxtReceiveDate() {
        return txtReceiveDate;
    }

    public void setTxtReceiveDate(String txtReceiveDate) {
        this.txtReceiveDate = txtReceiveDate;
    }

    public String getTxtReturnDate() {
        return txtReturnDate;
    }

    public void setTxtReturnDate(String txtReturnDate) {
        this.txtReturnDate = txtReturnDate;
    }

}
