/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.requestItem;

import java.io.Serializable;
import java.sql.SQLException;
import javax.naming.NamingException;
import trangcq.item.ItemDAO;
import trangcq.item.ItemDTO;

/**
 *
 * @author USER
 */
public class RequestItemDTO implements Serializable {

    private int amount, itemId, requestId;
    private ItemDTO itemDTO;

    public RequestItemDTO() {
    }

    public RequestItemDTO(int amount, int itemId, int requestId) throws SQLException, NamingException {
        this.amount = amount;
        this.itemId = itemId;
        this.requestId = requestId;
        ItemDAO dao = new ItemDAO();
        itemDTO = dao.getItemWithQuantityByID(itemId);
        
    }

    public RequestItemDTO(int amount, int itemId) {
        this.amount = amount;
        this.itemId = itemId;
    }

    
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public ItemDTO getItemDTO() {
        return itemDTO;
    }

    public void setItemDTO(ItemDTO itemDTO) {
        this.itemDTO = itemDTO;
    }
    
    

}
