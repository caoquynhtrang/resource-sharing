/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import java.util.Map;
import trangcq.cart.CartObject;

/**
 *
 * @author USER
 */
public class BorrowItemAction {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(BorrowItemAction.class);
    private int txtId;
    private final String SUCCESS = "success";

    public BorrowItemAction() {
    }

    public String execute() throws Exception {
        try {
            Map session = ActionContext.getContext().getSession();
            CartObject cart = (CartObject) session.get("CART");
            if (cart == null) {
                cart = new CartObject();
            }
            cart.addItemCart(txtId);
            session.put("CART", cart);
            return SUCCESS;
        } catch (Exception e) {
            LOGGER.fatal(e.getMessage());
        }
        return Action.ERROR;

    }

    public int getTxtId() {
        return txtId;
    }

    public void setTxtId(int txtId) {
        this.txtId = txtId;
    }

}
