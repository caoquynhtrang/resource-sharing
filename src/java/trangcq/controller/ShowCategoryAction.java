/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.controller;

import com.opensymphony.xwork2.Action;
import java.util.List;
import trangcq.item.ItemDAO;

/**
 *
 * @author USER
 */
public class ShowCategoryAction {

    static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SearchRequestHistoryAction.class);

    private final String SUCCESS = "success";
    private List<String> listCategory;

    public ShowCategoryAction() {
    }

    public String execute() throws Exception {
        try {
            ItemDAO dao = new ItemDAO();
            listCategory = dao.getAllCategory();

            return SUCCESS;
        } catch (Exception e) {
            LOGGER.fatal(e.getMessage());
        }
        return Action.ERROR;
    }

    public List<String> getListCategory() {
        return listCategory;
    }

    public void setListCategory(List<String> listCategory) {
        this.listCategory = listCategory;
    }

}
