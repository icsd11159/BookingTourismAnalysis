package com.company;

import java.sql.*;


public class database1 {
    private static final String url = "jdbc:sqlite:booking.db";

    public static void connect() {

        Connection conn;
        conn = null;

        try {
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            CreateTable();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void CreateTable() {
        StringBuilder Builder = new StringBuilder().append("CREATE TABLE IF NOT EXISTS 'usersearh'(").append("'id' INTEGER PRIMARY KEY AUTOINCREMENT,").append("'destination' TEXT,").append("'date' TEXT,").append("'Accommodations' INTEGER,").append("'average_rating' REAL,").append("'average_price' REAL,");
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(Builder.toString());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void Insert(String destination, String date, Integer Accommodations, Double average_rating, Double average_price){
        try {
            // SQLite connection string
            Connection conn = DriverManager.getConnection(url);
            String insert = new StringBuilder().append("insert into 'usersearh' ('destination', 'date', 'Accommodations', 'average_rating', 'average_price','Timestamp') VALUES ('").append(destination).append("', '").append(date).append("', '").append(Accommodations).append("', '").append(average_rating).append("', '").append(average_price).append("')").toString();
            System.out.println(insert);
            conn.createStatement().execute(insert);

        } catch (SQLException e) {

        }
    }

    //public static void selectAll(String destination, Integer date){
       // String url = "jdbc:sqlite:booking.db";
        //StringBuilder Builder  = new StringBuilder();
       /// try (Connection conn = DriverManager.getConnection(url);
           //  Statement stmt  = conn.createStatement();
           //  ResultSet rs    = stmt.executeQuery(String.valueOf(Builder))){
            // loop through the result set
           // while (rs.next()) {
               // System.out.println(rs.getInt("id") +  "\t" +
                     //   rs.getString("destination") + "\t" +
                     //   rs.getString("date") + "\t" +
                     //   rs.getInt("Accommodations") + "\t" +
                     //   rs.getDouble("average_rating") + "\t" +
                      ///  rs.getString("average_price"));
          //  }
       // }//// catch (SQLException e) {
           // System.out.println(e.getMessage());
      //  }
   // }
  public static void close() {
      try {
         Connection conn = DriverManager.getConnection(url);
         if (conn != null) {
               conn.close();
          }
       } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      }
    }

    }




















