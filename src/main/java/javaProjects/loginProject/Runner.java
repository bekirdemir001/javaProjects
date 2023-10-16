package javaProjects.loginProject;
import javaProjects.ReusableMethods;

import java.sql.SQLException;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) throws SQLException {
        start();
    }

    public static void start() throws SQLException {
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);
        String select;

        do {
            userService.showMenu();
            select = scanner.nextLine();

            switch (select){
                case "1":
                    userService.register();
                    break;
                case "2":
                    userService.login();
                    break;
                case "3":
                    System.out.println("----------------------------------------------------");
                    ReusableMethods.slowPrint("***** Have a Nice Day *****", 50);
                    System.out.println();
                    System.out.println("-----------------------------------------------------");
                    break;
                default:
                    System.out.println("You have logged in incorrectly. Please try again");
            }
        }while (!select.equals("3"));
    }
}