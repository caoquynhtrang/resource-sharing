/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.request;

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
import trangcq.registration.RegistrationDTO;
import trangcq.status.StatusDTO;

/**
 *
 * @author USER
 */
public class RequestDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public RequestDAO() {
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

    public int insertRequest(RequestDTO dto) throws SQLException, NamingException {
        int id = -1;
        try {
            String sql = "INSERT INTO \"public\".\"Request\" ( \"ReturnDate\", \"ReceiveDate\", \"UserID\", \"StatusID\", \"ImportedDate\") "
                    + "VALUES ( ?, ?, ?, ?, CURRENT_DATE)"
                    + "Returning \"ID\" ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setDate(1, dto.getReturnDate());
            preStm.setDate(2, dto.getReceiveDate());
            preStm.setInt(3, dto.getUserID());
            preStm.setInt(4, 3);
            rs = preStm.executeQuery();
            if (rs.next()) {
                id = rs.getInt("ID");
            }
        } finally {
            closeConnection();
        }
        return id;
    }

    public int countTotalRequest() throws SQLException, NamingException {
        int countPage = 0;
        try {
            String sql = "SELECT COUNT(\"ID\") as totalRows "
                    + "FROM \"public\".\"Request\" ";
                   
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);

            rs = preStm.executeQuery();
            if (rs.next()) {
                countPage = rs.getInt("totalRows");
            }
        } finally {
            closeConnection();
        }
        return countPage;
    }

    public List<RequestDTO> getAllRequestPaging() throws SQLException, NamingException {
        return getAllRequestPaging(1);
    }

    public List<RequestDTO> getAllRequestPaging(int pageIndex) throws SQLException, NamingException {
        List<RequestDTO> result = new ArrayList<>();
        int pageSize = 20;
        try {
            String sql = "SELECT "
                    + "	\"ReturnDate\", "
                    + "	\"ID\", "
                    + "	\"ReceiveDate\", "
                    + "	\"UserID\", "
                    + "	\"StatusID\", "
                    + "	\"ImportedDate\", "
                    + "	(SELECT \"Fullname\" FROM \"public\".\"Registration\" WHERE \"ID\" = \"UserID\") AS UserName, "
                    + "	(SELECT \"Name\" FROM \"public\".\"Status\" WHERE \"ID\" = \"StatusID\") AS NameStatus "
                    + "FROM \"public\".\"Request\" "
                    + "ORDER BY \"ReceiveDate\" DESC "
                    + "OFFSET ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, pageSize * (pageIndex - 1));
            preStm.setInt(2, pageSize);
            rs = preStm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                Date returnDate = rs.getDate("ReturnDate");
                Date receiveDate = rs.getDate("ReceiveDate");
                int userID = rs.getInt("UserID");
                int statusID = rs.getInt("StatusID");
                Date importedDate = rs.getDate("ImportedDate");
                String statusName = rs.getString("NameStatus");
                String fullname = rs.getString("UserName");
                RequestDTO dto = new RequestDTO(id, returnDate, receiveDate, userID, statusID, importedDate, new StatusDTO(statusName), new RegistrationDTO(fullname));

                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean acceptRequest(int id) throws SQLException, NamingException {
        boolean result = false;
        try {
            String sql = "UPDATE \"public\".\"Request\" SET "
                    + "	   \"StatusID\" = 4 "
                    + "	   Where \"ID\" = ? ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, id);
            result = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean deleteRequest(int id) throws SQLException, NamingException {
        boolean result = false;
        try {
            String sql = "UPDATE \"public\".\"Request\" SET "
                    + "	   \"StatusID\" = 5 "
                    + "	   Where \"ID\" = ? ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, id);
            result = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<RequestDTO> getAllRequestByUserID(int userID) throws SQLException, NamingException {
        List<RequestDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT "
                    + "	\"ReturnDate\", "
                    + "	\"ID\", "
                    + "	\"ReceiveDate\", "
                    + "	\"StatusID\", "
                    + "	\"ImportedDate\", "
                    + "	(SELECT \"Fullname\" FROM \"public\".\"Registration\" WHERE \"ID\" = \"UserID\") AS UserName, "
                    + "	(SELECT \"Name\" FROM \"public\".\"Status\" WHERE \"ID\" = \"StatusID\") AS NameStatus "
                    + "FROM \"public\".\"Request\" Where \"UserID\" = ? "
                    + "ORDER BY \"ReceiveDate\" DESC";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, userID);
            rs = preStm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                Date returnDate = rs.getDate("ReturnDate");
                Date receiveDate = rs.getDate("ReceiveDate");
                int statusID = rs.getInt("StatusID");
                Date importedDate = rs.getDate("ImportedDate");
                String statusName = rs.getString("NameStatus");
                String fullname = rs.getString("UserName");
                RequestDTO dto = new RequestDTO(id, returnDate, receiveDate, userID, statusID, importedDate, new StatusDTO(statusName), new RegistrationDTO(fullname));
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    public RequestDTO getRequestByID(int requestId) throws SQLException, NamingException {
        RequestDTO result = null;
        try {
            String sql = "SELECT "
                    + "	\"ReturnDate\", "
                    + "	\"ID\", "
                    + "	\"UserID\", "
                    + "	\"ReceiveDate\", "
                    + "	\"StatusID\", "
                    + "	\"ImportedDate\", "
                    + "	(SELECT \"Fullname\" FROM \"public\".\"Registration\" WHERE \"ID\" = \"UserID\") AS UserName, "
                    + "	(SELECT \"Name\" FROM \"public\".\"Status\" WHERE \"ID\" = \"StatusID\") AS NameStatus "
                    + "FROM \"public\".\"Request\" Where \"ID\" = ? ";
                    
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, requestId);
            rs = preStm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("ID");
                int userID = rs.getInt("UserID");
                Date returnDate = rs.getDate("ReturnDate");
                Date receiveDate = rs.getDate("ReceiveDate");
                int statusID = rs.getInt("StatusID");
                Date importedDate = rs.getDate("ImportedDate");
                String statusName = rs.getString("NameStatus");
                String fullname = rs.getString("UserName");
                result = new RequestDTO(id, returnDate, receiveDate, userID, statusID, importedDate, new StatusDTO(statusName), new RegistrationDTO(fullname));
                
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean deleteRequestHistory(int id) throws SQLException, NamingException {
        boolean result = false;
        try {
            String sql = "UPDATE \"public\".\"Request\" SET "
                    + "	   \"StatusID\" = 2 "
                    + "	   Where \"ID\" = ? ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, id);
            result = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<RequestDTO> searchRequest(String nameItem, String fullname, Date dateReceive, int idStatus) throws SQLException, NamingException {
        List<RequestDTO> result = new ArrayList<>();
        int count = 1;
        try {
            String sql = "SELECT "
                    + "	R.\"ID\", "
                    + "	\"ReturnDate\", "
                    + "	\"ReceiveDate\", "
                    + "	\"UserID\", "
                    + "	\"StatusID\", "
                    + "	R.\"ImportedDate\", "
                    + "	(SELECT \"Fullname\" FROM \"public\".\"Registration\" WHERE \"ID\" = \"UserID\") AS UserName, "
                    + "	(SELECT \"Name\" FROM \"public\".\"Status\" WHERE \"ID\" = \"StatusID\") AS NameStatus "
                    + "FROM \"public\".\"Request\" R, \"public\".\"RequestItem\" RI "
                    + "WHERE R.\"ID\" = RI.\"RequestID\" ";
            if (nameItem != null && !nameItem.isEmpty()) {
                sql += "AND (SELECT LOWER(\"Name\") From \"public\".\"Item\" I "
                        + "WHERE I.\"ID\" = RI.\"ItemID\") LIKE ? ";
            }
            if (fullname != null && !fullname.isEmpty()) {
                sql += "And (SELECT LOWER(\"Fullname\") From \"public\".\"Registration\" RE "
                        + "Where RE.\"ID\" = R.\"UserID\") Like ? ";
            }
            if (dateReceive != null) {
                sql += "And R.\"ReceiveDate\" = ? ";
            }
            if (idStatus != 0) {
                sql += "And R.\"StatusID\" = ? ";
            }
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            if (nameItem != null && !nameItem.isEmpty()) {
                preStm.setString(count, "%" + nameItem + "%");
                count++;
            }
            if (fullname != null && !fullname.isEmpty()) {
                preStm.setString(count, "%" + fullname + "%");
                count++;
            }
            if (dateReceive != null) {
                preStm.setDate(count, dateReceive);
                count++;
            }
            if (idStatus != 0) {
                preStm.setInt(count, idStatus);
                count++;
            }
            rs = preStm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                Date returnDate = rs.getDate("ReturnDate");
                Date receiveDate = rs.getDate("ReceiveDate");
                int userID = rs.getInt("UserID");
                int statusID = rs.getInt("StatusID");
                Date importedDate = rs.getDate("ImportedDate");
                String statusName = rs.getString("NameStatus");
                String fullnameUser = rs.getString("UserName");
                RequestDTO dto = new RequestDTO(id, returnDate, receiveDate, userID, statusID, importedDate, new StatusDTO(statusName), new RegistrationDTO(fullnameUser));
                result.add(dto);
            }

        } finally {
            closeConnection();
        }
        return result;
    }

    public List<RequestDTO> searchRequestHistory(String nameItem, Date importedDateRes, int userID) throws SQLException, NamingException {
        List<RequestDTO> result = new ArrayList<>();
        int count = 2;
        try {
            String sql = "SELECT "
                    + "	R.\"ID\", "
                    + "	\"ReturnDate\", "
                    + "	\"ReceiveDate\", "
                    + "	\"UserID\", "
                    + "	\"StatusID\", "
                    + "	R.\"ImportedDate\", "
                    + "	(SELECT \"Fullname\" FROM \"public\".\"Registration\" WHERE \"ID\" = \"UserID\") AS UserName, "
                    + "	(SELECT \"Name\" FROM \"public\".\"Status\" WHERE \"ID\" = \"StatusID\") AS NameStatus "
                    + "FROM \"public\".\"Request\" R, \"public\".\"RequestItem\" RI "
                    + "WHERE R.\"ID\" = RI.\"RequestID\" And \"UserID\" = ? ";
            if (nameItem != null && !nameItem.isEmpty()) {
                sql += "AND (SELECT LOWER(\"Name\") From \"public\".\"Item\" I "
                        + "WHERE I.\"ID\" = RI.\"ItemID\") LIKE ? ";
            }
            if (importedDateRes != null) {
                sql += "And R.\"ImportedDate\" = ? ";
            }
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, userID);
            if (nameItem != null && !nameItem.isEmpty()) {
                preStm.setString(count, "%" + nameItem + "%");
                count++;
            }
            if (importedDateRes != null) {
                preStm.setDate(count, importedDateRes);
                count++;
            }
            rs = preStm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                Date returnDate = rs.getDate("ReturnDate");
                Date receiveDate = rs.getDate("ReceiveDate");
                int statusID = rs.getInt("StatusID");
                Date importedDate = rs.getDate("ImportedDate");
                String statusName = rs.getString("NameStatus");
                String fullnameUser = rs.getString("UserName");
                RequestDTO dto = new RequestDTO(id, returnDate, receiveDate, userID, statusID, importedDate, new StatusDTO(statusName), new RegistrationDTO(fullnameUser));
                result.add(dto);
            }

        } finally {
            closeConnection();
        }
        return result;
    }

}
