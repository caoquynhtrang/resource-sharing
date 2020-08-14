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
public class DeleteItemAction {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(DeleteItemAction.class);

    private final String SUCCESS = "success";
    private int id;

    public DeleteItemAction() {
    }

    public String execute() throws Exception {
        try {
            Map session = ActionContext.getContext().getSession();
            if (session != null) {
                CartObject cart = (CartObject) session.get("CART");
                if (cart != null) {
                    cart.removeItem(id);
                    if(cart.getItems().isEmpty()){
                        cart = null;
                    }
                    session.put("CART", cart);
                }
            }
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

}
