/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.registration;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
import trangcq.conn.MyConnection;
import trangcq.role.RoleDTO;
import trangcq.status.StatusDTO;

/**
 *
 * @author USER
 */
public class RegistrationDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public RegistrationDAO() {
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

    public RegistrationDTO checkLogin(String email, String password) throws SQLException, NamingException {
        RegistrationDTO result = null;
        try {
            String sql = "SELECT "
                    + "	Re.\"ID\", "
                    + "	\"Email\", "
                    + "	\"Password\", "
                    + "	\"Fullname\", "
                    + "	R.\"ID\" AS RoleID, "
                    + "	R.\"Name\" AS RoleName "
                    + "FROM \"public\".\"Registration\" Re JOIN \"public\".\"Role\" R ON Re.\"RoleID\" = R.\"ID\" "
                    + "WHERE \"Email\" = ? And \"Password\" = ? and \"StatusID\" = 1 ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            preStm.setString(2, password);
            rs = preStm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("ID");
                String fullname = rs.getString("Fullname");
                int roleID = rs.getInt("RoleID");
                String roleName = rs.getString("RoleName");
                result = new RegistrationDTO(id, email, password, fullname, new RoleDTO(roleID, roleName), new StatusDTO(1));
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean insertAccount(String email, String password, String fullname, String phone, String address) throws SQLException, NamingException {
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        boolean result = false;
        try {
            String sql = "INSERT INTO \"public\".\"Registration\" (\"Email\", \"Password\", \"Fullname\", \"Phone\", \"Address\", \"CreateDate\", \"RoleID\", \"StatusID\") "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            preStm.setString(2, password);
            preStm.setString(3, fullname);
            preStm.setString(4, phone);
            preStm.setString(5, address);
            preStm.setTimestamp(6, currentDate);
            preStm.setInt(7, 3);
            preStm.setInt(8, 3);
            result = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean updateStatusInAccount(String email) throws SQLException, NamingException {
        boolean result = false;
        try {
            String sql = "UPDATE \"public\".\"Registration\" SET "
                    + "	\"StatusID\" = 1 "
                    + " Where \"Email\" = ? ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            result = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }

    public RegistrationDTO getUserInforByEmail(String email) throws SQLException, NamingException {
        RegistrationDTO result = null;
        try {
            String sql = "SELECT "
                    + "	\"ID\", "
                    + "	\"Email\", "
                    + "	\"Password\", "
                    + "	\"Fullname\", "
                    + "	\"Phone\", "
                    + "	\"Address\", "
                    + "	\"CreateDate\", "
                    + "	\"RoleID\", "
                    + "	\"StatusID\" "
                    + "FROM \"public\".\"Registration\" "
                    + "WHERE \"Email\" = ? ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            rs = preStm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("ID");
                email = rs.getString("Email");
                String password = rs.getString("Password");
                String fullname = rs.getString("Fullname");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address");
                Timestamp createDate = rs.getTimestamp("CreateDate");
                int roleID = rs.getInt("RoleID");
                String roleName = rs.getString("RoleName");
                result = new RegistrationDTO(id, email, password, fullname, phone, address, createDate, new RoleDTO(roleID, roleName), new StatusDTO(1));
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public RegistrationDTO checkLoginWithGoogle(String emailUser) throws SQLException, NamingException {
        RegistrationDTO result = null;
        try {
            String sql = "SELECT "
                    + "	\"ID\", "
                    + "	\"Email\", "
                    + "	\"Fullname\", "
                    + "	\"Avatar\", "
                    + " \"StatusID\", "
                    + " \"RoleID\" "
                    + "FROM \"public\".\"Registration\"  "
                    + "WHERE \"Email\" = ? ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, emailUser);
            rs = preStm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("ID");
                String email = rs.getString("Email");
                String fullname = rs.getString("Fullname");
                String avatar = rs.getString("Avatar");
                int statusID = rs.getInt("StatusID");
                int roleID = rs.getInt("RoleID");
                result = new RegistrationDTO();
                result.setId(id);
                result.setEmail(email);
                result.setFullname(fullname);
                result.setAvatar(avatar);
                result.setStatus(new StatusDTO(statusID));
                result.setRole(new RoleDTO(roleID));
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int insertAccountWithGoogle(String email, String fullname, int roleID, int statusID, String avatar) throws SQLException, NamingException {
        int id = -1;
        try {
            String sql = "INSERT INTO \"public\".\"Registration\" (\"Email\", \"Fullname\", \"RoleID\", \"StatusID\", \"Avatar\", \"CreateDate\") "
                    + "VALUES (?, ?, ?, ?, ?, ? ) Returning \"ID\"";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            preStm.setString(2, fullname);
            preStm.setInt(3, roleID);
            preStm.setInt(4, statusID);
            preStm.setString(5, avatar);
            preStm.setDate(6, new Date(System.currentTimeMillis()));
            rs = preStm.executeQuery();
            if (rs.next()) {
                id = rs.getInt("ID");
            }

        } finally {
            closeConnection();
        }
        return id;
    }

}
