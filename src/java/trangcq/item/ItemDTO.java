/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.item;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author USER
 */
public class ItemDTO implements Serializable {

    private int id;
    private String category, color, name;
    private Date importedDate;
    private int quantity, statusID;

    public ItemDTO() {
    }

    public ItemDTO(String category) {
        this.category = category;
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(importedDate);
        return strDate;
    }

    public ItemDTO(int id, String category, String color, String name, Date importedDate) {
        this.id = id;
        this.category = category;
        this.color = color;
        this.name = name;
        this.importedDate = importedDate;
    }

    public ItemDTO(int id, String category, String color, String name, Date importedDate, int quantity, int statusID) {
        this.id = id;
        this.category = category;
        this.color = color;
        this.name = name;
        this.importedDate = importedDate;
        this.quantity = quantity;
        this.statusID = statusID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(Date importedDate) {
        this.importedDate = importedDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

}
