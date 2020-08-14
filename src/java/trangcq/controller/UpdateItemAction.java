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
public class UpdateItemAction {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SearchRequestHistoryAction.class);

    private final String SUCCESS = "success";
    private int id;
    private int amount;

    public UpdateItemAction() {
    }

    public String execute() throws Exception {
        try {
            Map session = ActionContext.getContext().getSession();
            CartObject cart = (CartObject) session.get("CART");
            if (cart == null) {
                cart = new CartObject();
            }
            if (amount > 0) {
                cart.updateItem(id, amount);
                session.put("CART", cart);
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
