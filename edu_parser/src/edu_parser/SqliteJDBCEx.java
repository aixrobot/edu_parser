package edu_parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqliteJDBCEx {
    public static void main(String args[]) throws FileNotFoundException, IOException {
        SelectSql();
    }

    public static void SelectSql(){

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/Users/juseong-yun/IdeaProjects/git/edu_parser/java_sqlite.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM TBL_TEMP;" );

            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");

                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }
}
