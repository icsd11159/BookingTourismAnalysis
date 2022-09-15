package com.company;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import static com.company.Menu.menu;

public class Main {
    private static final Scanner userScanner = new Scanner(System.in);
    private static final Integer date = null;
    private static final String destination = null;
    private static Logger logger = Logger.getLogger(Parsing.class.getName());
    CSVPrinter csvPrinter;

    private static final String url = "jdbc:sqlite:booking.db";
    public static void main(String[] args) throws IOException, SQLException {


        String flag = "Y";

        do {
            System.out.println("Tourism Booking Analysis App");

            int userChoice;
            /*********************************************************/

            userChoice = menu();
            //from here we can use a switch statement on the userchoice
            //using if/else statements to do actually functions for choices.
            switch (userChoice) {
                case 1:
                    SearchDestination();
                    break;
                case 2:
                    DisplayStatistics();

                case 3:
                    ExportFile();
                    break;
                case 4:
                    // Perform "quit" case.
                    System.exit(0);

                    break;
                default:
                    System.out.println("Wrong Choice...");
            }

            System.out.println("Continue Y OR N?");

            flag=userScanner.nextLine();
        } while (flag.equalsIgnoreCase("Y"));

        System.exit(0);
    }

    private static void ExportFile() {

        // File path and name.
        File filePath = new File("D:/BookingTourismAnalysis/BookingTourismAnalysis");
        String fileName = filePath.toString() + "\\searchesexport.csv";

        // Database and connection variables.
        File database = new File("booking.db");
        Connection connect = null;

        // Check if database file exists.
        if (!database.isFile()) {

            // Confirm incorrect database location and stop program execution.
            System.out.println("Error locating database.");
            System.exit(0);

        }

        try {

            // Connect to database.
            connect = DriverManager.getConnection(url);

        } catch (SQLException e) {

            // Confirm unsuccessful connection and stop program execution.
            System.out.println("Database connection unsuccessful.");
            System.exit(0);

        }

        // Check to see if the file path exists.
        if (filePath.isDirectory()) {

            // SQL to select data from the person table.
            String sqlSelect = "SELECT * FROM usersearh" ;

            try {

                // Execute query.
                Statement statement  = connect.createStatement();

                ResultSet results    = statement.executeQuery(sqlSelect);
                File filePath2 = new File("D:/BookingTourismAnalysis/BookingTourismAnalysis/searchesexport.csv");

                FileChannel channel = new RandomAccessFile(filePath2, "rw").getChannel();
                System.out.println("Checking for file in path: "+filePath2);


                if(filePath2.exists()==true){ //checkaroume an uparxei idi  arxeio me tis eggrafes

                    if(filePath2.canWrite()){ // kai an einai anoixto apo ton xristi gia na mporei na diagraftei gia na enhmerwsoume ton xristi
                        filePath2.deleteOnExit();

                    }else{
                        System.out.print("File is opened! Please close it so we can write the new entrys\n");
                    }
                }

                    BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName));



                // Add table headers to CSV file.
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader(results.getMetaData()).withQuoteMode(QuoteMode.ALL));

                // Add data rows to CSV file.
                while (results.next()) {

                    csvPrinter.printRecord(
                            results.getInt("id") ,
                                    results.getString("destination") ,
                                    results.getString("date") ,
                                    results.getInt("Accommodations"),
                                    results.getDouble("average_rating") ,
                                    results.getDouble("average_price"));

                }

                // Close CSV file.
                csvPrinter.flush();
                csvPrinter.close();

                // Message stating export successful.
                System.out.println("Data export successful.");

            } catch (SQLException e) {

                // Message stating export unsuccessful.
                System.out.println("Data export unsuccessful.");
                System.exit(0);

            } catch (IOException e) {

                // Message stating export unsuccessful.
                System.out.println("Data export unsuccessful.");
                System.exit(0);

            }

        } else {

            // Display a message stating file path does not exist and exit.
            System.out.println("File path does not exist.");
            System.exit(0);

        }



    }

    private static void DisplayStatistics() {
        logger.info("Display Statistics");
        SelectRecords app = new SelectRecords();
        app.selectAll();

    }

    private static void SearchDestination() throws IOException {
        logger.info("Add Destination");
        String destination = userScanner.nextLine();
        while (destination == null) {
            System.out.println("Input is empty,please give the destination");
            destination = userScanner.nextLine();
        }
        logger.info("Give the date Date yyyy-MM-dd :");
        // Creating a calendar
        String date = null;
        while (date == null) {
            date = userScanner.nextLine();
            System.out.println("You Select: " + destination);
            // Output user input

            List<Accommodation> total_accommodations = new ArrayList<>();
            List<Accommodation> accommodations;
            System.out.println("Start");
            Integer page = 0;
            while (!(accommodations = Parsing.parseBookingPage(destination, date, page)).isEmpty()) {
                page++;
                for (Accommodation accommodation1 : accommodations) {
                    total_accommodations.add(accommodation1);
                    logger.info(accommodation1.toString());
                }
            }
            logger.info(" ):Booking Analysis-->Booking Page ");
            page = 1;
            while (!(accommodations = Parsing.parseHotelsPage(destination, date, page)).isEmpty()) {
                page++;
                for (Accommodation accommodation1 : accommodations) {
                    total_accommodations.add(accommodation1);
                    logger.info(accommodation1.toString());
                }
            }logger.info(" ):Booking Analysis-->Hotels Page ");


            accommodations = Parsing.parseAgodaPage(destination);
            for (Accommodation accommodation1 : accommodations) {
                total_accommodations.add(accommodation1);
                logger.info(accommodation1.toString());
            }
            logger.info(" ):Booking Analysis-->Agoda ");

            //ekdromi
            accommodations = Parsing.parseEkdromiPage(destination);
            for (Accommodation accommodation1 : accommodations) {
                total_accommodations.add(accommodation1);
                logger.info(accommodation1.toString());
            }
            logger.info(" ):from Ekdromi Page ");
            Double average_rating = 0.0;
            Double average_price = 0.0;
            if ( total_accommodations.size() > 0 ) {
                Double ratingSum = 0.0;
                Double priceSum = 0.0;
                for (Accommodation accommodation1 : total_accommodations) {
                    if (accommodation1.getRating() != null ) {
                        ratingSum += accommodation1.getRating();
                    }
                    if (accommodation1.getPrice() != null ) {
                        priceSum += accommodation1.getPrice();
                    }
                }
                average_rating= ratingSum / total_accommodations.size();
                average_price = priceSum / total_accommodations.size();
            }
            logger.info("found: " + total_accommodations.size() + ", average_rating = " + average_rating + ", average_price = " + average_price);
            database1.Insert(destination, date, total_accommodations.size(), average_rating, average_price);


        }
    }


}