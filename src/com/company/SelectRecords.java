package com.company;

import java.sql.*;

public class SelectRecords {

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:booking.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public void selectAll(){
        String url = "jdbc:sqlite:booking.db";
        String sql = "SELECT * FROM usersearh";

        try {
            Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("destination") + "\t" +
                        rs.getString("date") + "\t" +
                        rs.getInt("Accommodations") + "\t" +
                        rs.getDouble("average_rating") + "\t" +
                        rs.getDouble("average_price"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
