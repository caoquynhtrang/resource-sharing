/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.item;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import trangcq.conn.MyConnection;

/**
 *
 * @author USER
 */
public class ItemDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public ItemDAO() {
    }

    public void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public List<String> getAllCategory() throws SQLException, NamingException {
        List<String> result = null;
        try {
            String sql = "SELECT "
                    + "DISTINCT \"Category\" "
                    + "FROM \"public\".\"Item\" ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                String category = rs.getString("Category");
                result.add(category);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int countTotalItem(String name, Date receiveDate, Date returnDate, String category) throws SQLException, NamingException {

        int countPage = 0;
        int count = 1;
        try {
            String sql = "SELECT COUNT(I.\"ID\") as totalRows "
                    + "FROM \"public\".\"Item\" I "
                    + "WHERE   I.\"StatusID\" = 1 ";

            if (name != null && !name.isEmpty()) {
                sql += "AND I.\"Name\" like ? ";
            }
            if (!category.isEmpty()) {
                sql += "AND I.\"Category\" like ? ";
            }
            if (receiveDate != null && returnDate != null) {
                sql += "AND \"Quantity\" > (SELECT COALESCE((SELECT  SUM(\"Amount\") "
                        + "                 From \"public\".\"RequestItem\" RI, \"public\".\"Request\" R "
                        + "                 WHERE NOT(R.\"ReceiveDate\" >= ? OR R.\"ReturnDate\" <= ?) "
                        + "                 And RI.\"ItemID\" = I.\"ID\" and R.\"StatusID\" = 4 "
                        + "                 GROUP BY RI.\"ItemID\"),0)) ";
            }

            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            if (name != null && !name.isEmpty()) {
                preStm.setString(count, "%" + name + "%");
                count++;
            }
            if (!category.isEmpty()) {
                preStm.setString(count, "%" + category + "%");
                count++;
            }
            if (receiveDate != null && returnDate != null) {
                preStm.setDate(count, returnDate);
                count++;
                preStm.setDate(count, receiveDate);
                count++;
            }

            rs = preStm.executeQuery();
            if (rs.next()) {
                countPage = rs.getInt("totalRows");
            }

        } finally {
            closeConnection();
        }
        return countPage;
    }

    public List<ItemDTO> searchItemPaging(String name, Date receiveDate, Date returnDate, String category) throws SQLException, NamingException {
        return searchItemPaging(name, receiveDate, returnDate, category, 1);
    }

    public List<ItemDTO> searchItemPaging(String name, Date receiveDate, Date returnDate, String category, int pageIndex) throws SQLException, NamingException {
        List<ItemDTO> result = new ArrayList<>();
        int pageSize = 20;
        int count = 1;
        try {
            String sql = "SELECT I.\"ID\", I.\"Name\", I.\"Color\", I.\"Category\", I.\"ImportedDate\", I.\"Quantity\" "
                    + "FROM \"public\".\"Item\" I "
                    + "WHERE   I.\"StatusID\" = 1 ";

            if (name != null && !name.isEmpty()) {
                sql += "AND LOWER(I.\"Name\") like ? ";
            }
            if (!category.isEmpty()) {
                sql += "AND I.\"Category\" = ? ";
            }
            if (receiveDate != null && returnDate != null) {
                sql += "AND \"Quantity\" > (SELECT COALESCE((SELECT  SUM(\"Amount\") "
                        + "                 From \"public\".\"RequestItem\" RI, \"public\".\"Request\" R "
                        + "                 WHERE NOT(R.\"ReceiveDate\" >= ? OR R.\"ReturnDate\" <= ?) "
                        + "                 And RI.\"ItemID\" = I.\"ID\" and R.\"StatusID\" = 4 "
                        + "                 GROUP BY RI.\"ItemID\"),0)) ";
            }
            sql += "ORDER BY \"ID\" "
                    + "OFFSET ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            if (name != null && !name.isEmpty()) {
                preStm.setString(count, "%" + name + "%");
                count++;
            }
            if (!category.isEmpty()) {
                preStm.setString(count, category);
                count++;
            }
            if (receiveDate != null && returnDate != null) {
                preStm.setDate(count, returnDate);
                count++;
                preStm.setDate(count, receiveDate);
                count++;
            }
            preStm.setInt(count, pageSize * (pageIndex - 1));
            preStm.setInt(count + 1, pageSize);
            rs = preStm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nameItem = rs.getString("Name");
                String color = rs.getString("Color");
                String categoryItem = rs.getString("Category");
                Date importedDate = rs.getDate("ImportedDate");
                int quantity = rs.getInt("Quantity");
                ItemDTO dto = new ItemDTO(id, categoryItem, color, nameItem, importedDate);
                dto.setQuantity(quantity);
                result.add(dto);

            }

        } finally {
            closeConnection();
        }
        return result;
    }

    public ItemDTO getItemWithQuantityByID(int id) throws SQLException, NamingException {
        ItemDTO result = null;
        try {
            String sql = "SELECT "
                    + "	\"ID\", "
                    + "	\"Name\", "
                    + "	\"Color\", "
                    + "	\"Category\", "
                    + "	\"Quantity\", "
                    + " \"ImportedDate\" "
                    + "FROM \"public\".\"Item\" "
                    + "WHERE \"ID\" = ? ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, id);
            rs = preStm.executeQuery();
            if (rs.next()) {
                String name = rs.getString("Name");
                String color = rs.getString("Color");
                String category = rs.getString("Category");
                int quantity = rs.getInt("Quantity");
                Date importedDate = rs.getDate("ImportedDate");
                result = new ItemDTO(id, category, color, name, importedDate);
                result.setQuantity(quantity);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<ItemDTO> getAllItemByID(int id) throws SQLException, NamingException {
        List<ItemDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT "
                    + "	\"ID\", "
                    + "	\"Name\", "
                    + "	\"Color\", "
                    + "	\"Category\", "
                    + "	\"Quantity\", "
                    + " \"ImportedDate\" "
                    + " \"StatusID\""
                    + "FROM \"public\".\"Item\" "
                    + "WHERE \"ID\" = ? ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, id);
            rs = preStm.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Name");
                String color = rs.getString("Color");
                String category = rs.getString("Category");
                int quantity = rs.getInt("Quantity");
                Date importedDate = rs.getDate("ImportedDate");
                int statusID = rs.getInt("StatusID");
                ItemDTO dto = new ItemDTO(id, category, color, name, importedDate, quantity, statusID);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

}
