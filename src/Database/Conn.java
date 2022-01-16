package Database;

import java.sql.*;

public class Conn {
    // Protected attribute so it can be accessed within the package
    protected Connection conn;

    // Initialize a connection to the database
    public Conn() {
        String connURL = "jdbc:sqlserver://localhost;databaseName=CodeCademy;integratedSecurity=true;";

        try {
            // Import db driver and connect to database with connection string
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.conn = DriverManager.getConnection(connURL);
        } catch (Exception e) {
            // Print any exeptions that may occur.
            e.printStackTrace();
        }
    }
}
