/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.cart;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import trangcq.item.ItemDAO;
import trangcq.item.ItemDTO;

/**
 *
 * @author USER
 */
public class CartObject implements Serializable {

    private ItemDTO itemDTO;
    private Map<Integer, Integer> items;
    private Map<Integer, ItemDTO> borrowItem;

    public Map<Integer, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Integer, Integer> items) {
        this.items = items;
    }

    public Map<Integer, ItemDTO> getBorrowItem() {
        return borrowItem;
    }

    public void setBorrowItem(Map<Integer, ItemDTO> borrowItem) {
        this.borrowItem = borrowItem;
    }

    public void addItemCart(int id) throws SQLException, NamingException {
        if (id == 0) {
            return;
        }
        if (this.items == null) {
            this.items = new HashMap<>();
        }
        int amount = 1;
        if (this.items.containsKey(id)) {
            amount = this.items.get(id) + 1;
        }
        this.items.put(id, amount);

        ItemDAO itemDAO = new ItemDAO();
        itemDTO = itemDAO.getItemWithQuantityByID(id);
        if (borrowItem == null) {
            borrowItem = new HashMap<>();
        }
        borrowItem.put(id, itemDTO);
    }

    public boolean updateItem(int id, int amount) {
        if (amount <= 0) {
            return false;
        }
        if (items.containsKey(id)) {
            items.put(id, amount);
        }
        return true;
    }
    
    public void removeItem(int id) {
        if (this.items == null) {
            return;
        }
        if (this.items.containsKey(id)) {
            this.items.remove(id);
        }

        if (this.borrowItem == null) {
            return;
        }
        if (this.borrowItem.containsKey(id)) {
            this.borrowItem.remove(id);
        }
    }
    
    

}
