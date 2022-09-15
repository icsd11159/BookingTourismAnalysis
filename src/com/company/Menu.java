package com.company;
import java.util.Scanner;


public class Menu {

    public static int menu() {

        int selection;
        Scanner userScanner = new Scanner(System.in);

        /***************************************************/

        System.out.println("Choose from these choices");
        System.out.println("-------------------------\n");
        System.out.println("1 - Insert the destination and date");
        System.out.println("2 - Display statistics");
        System.out.println("3 - Show recent search");
        System.out.println("4 - Quit");

        selection = userScanner.nextInt();
        return selection;
    }


    }


