package edu_parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertAppSqlite {

    /**
     * Connect to the test.db database
     *
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:/Users/juseong-yun/IdeaProjects/git/edu_parser/java_sqlite.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Insert a new row into the warehouses table
     *
     * @param name
     */
    public void insert(String name) {
        String sql = "INSERT INTO TBL_TEMP('name') VALUES(?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        InsertAppSqlite app = new InsertAppSqlite();
        // insert three new rows
        app.insert("Raw Materials");
        app.insert("Semifinished Goods");
    }

}