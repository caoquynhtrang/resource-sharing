/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.conn;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author USER
 */
public class MyConnection implements Serializable {

    public static Connection getMyConnection() throws SQLException, NamingException {
        Connection conn = null;
//        Context context = new InitialContext();
//        Context tomContext = (Context) context.lookup("java:comp/env");
//        DataSource ds = (DataSource) tomContext.lookup("SE1402");
//        conn = ds.getConnection();

        try {
            URI dbUri = new URI(System.getenv("DATABASE_URL"));

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

            conn = DriverManager.getConnection(dbUrl, username, password);
        } catch (Exception e) {
        }

        return conn;
    }
}
