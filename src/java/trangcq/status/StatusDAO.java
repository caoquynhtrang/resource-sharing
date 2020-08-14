/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.status;

import java.io.Serializable;
import java.sql.Connection;
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
public class StatusDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public StatusDAO() {
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

    public List<StatusDTO> getAllStatus() throws SQLException, NamingException {
        List<StatusDTO> result = null;
        try {
            String sql = "SELECT "
                    + "	\"ID\", "
                    + "	\"Name\" "
                    + "FROM \"public\".\"Status\" ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                StatusDTO dto = new StatusDTO(id, name);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
