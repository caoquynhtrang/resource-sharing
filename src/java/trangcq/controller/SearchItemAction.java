/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import trangcq.item.ItemDAO;
import trangcq.item.ItemDTO;

/**
 *
 * @author USER
 */
public class SearchItemAction {
    
    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SearchItemAction.class);


    private final String SUCCESS = "success";
    private String txtName;
    private String txtReceiveDate;
    private String txtReturnDate;
    private String categoryName;
    private int page;
    private int[] pageArr;

    private List<ItemDTO> listItem;

    public SearchItemAction() {
    }

    public String execute() throws Exception {

        try {
            if (txtName.isEmpty() && txtReceiveDate.isEmpty() && txtReturnDate.isEmpty() && categoryName.isEmpty()) {

            } else {

                Date receiveDate = null;
                try {
                    long time = ((java.util.Date) new SimpleDateFormat("yyyy-MM-dd").parse(txtReceiveDate)).getTime();
                    receiveDate = new Date(time);
                } catch (Exception e) {
                }
                Date returnDate = null;
                try {
                    long time = new SimpleDateFormat("yyyy-MM-dd").parse(txtReturnDate).getTime();
                    returnDate = new Date(time);
                    if (receiveDate != null && receiveDate.compareTo(returnDate) > 0) {
                        returnDate = null;
                        receiveDate = null;
                    }
                } catch (Exception e) {
                }

                ItemDAO dao = new ItemDAO();

                if (txtName.isEmpty() && receiveDate == null && returnDate == null && categoryName.isEmpty()) {

                } else {
                    if (page == 0) {
                        listItem = dao.searchItemPaging(txtName, receiveDate, returnDate, categoryName);
                    } else {
                        listItem = dao.searchItemPaging(txtName, receiveDate, returnDate, categoryName, page);
                    }
                    int totalItem = dao.countTotalItem(txtName, receiveDate, returnDate, categoryName);
                    int pageNumber = (int) Math.ceil((double) totalItem / 20);
                    pageArr = new int[pageNumber];

                }

            }

        } catch (Exception e) {
            LOGGER.fatal(e.getMessage());
        }

        return SUCCESS;

    }

    public int[] getPageArr() {
        return pageArr;
    }

    public void setPageArr(int[] pageArr) {
        this.pageArr = pageArr;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ItemDTO> getListItem() {
        return listItem;
    }

    public void setListItem(List<ItemDTO> listItem) {
        this.listItem = listItem;
    }

}
