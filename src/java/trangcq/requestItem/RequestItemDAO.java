/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.requestItem;

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
public class RequestItemDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public RequestItemDAO() {
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

    public boolean insertRequestItem(RequestItemDTO dto) throws SQLException, NamingException {
        boolean result = false;
        try {
            String sql = "INSERT INTO \"public\".\"RequestItem\" ( \"Amount\", \"ItemID\", \"RequestID\") "
                    + "VALUES (?, ?, ? ) ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, dto.getAmount());
            preStm.setInt(2, dto.getItemId());
            preStm.setInt(3, dto.getRequestId());
            result = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getTotalAmount(int idItem, Date receiveDate, Date returnDate) throws SQLException, NamingException {
        int amount = 0;
        try {
            String sql = "SELECT COALESCE((Select SUM(\"Amount\") "
                    + "FROM \"public\".\"RequestItem\" RI "
                    + "WHERE NOT ( "
                    + "(SELECT \"ReceiveDate\" FROM \"public\".\"Request\" R WHERE R.\"ID\" = RI.\"RequestID\")  >= ? OR "
                    + "(SELECT \"ReturnDate\" FROM \"public\".\"Request\" R WHERE R.\"ID\" = RI.\"RequestID\") <= ? ) "
                    + "and (SELECT \"StatusID\" FROM \"public\".\"Request\" R WHERE R.\"ID\" = RI.\"RequestID\") = 4 "
                    + "AND RI.\"ItemID\" = ?), 0) AS Amount";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setDate(1, returnDate);
            preStm.setDate(2, receiveDate);
            preStm.setInt(3, idItem);
            rs = preStm.executeQuery();
            if (rs.next()) {
                amount = rs.getInt("Amount");
            }
        } finally {
            closeConnection();
        }
        return amount;
    }

    //kiểm tra từ item xem số lượng phù hợp ko
    public List<RequestItemDTO> getRequestItemByRequestID(int requestID) throws SQLException, NamingException {
        List<RequestItemDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT "
                    + "	\"Amount\", "
                    + "	\"ItemID\" "
                    + "FROM \"public\".\"RequestItem\" Where \"RequestID\" = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, requestID);
            rs = preStm.executeQuery();
            while (rs.next()) {
                int amount = rs.getInt("Amount");
                int itemID = rs.getInt("ItemID");
                RequestItemDTO dto = new RequestItemDTO(amount, itemID, requestID);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

}
