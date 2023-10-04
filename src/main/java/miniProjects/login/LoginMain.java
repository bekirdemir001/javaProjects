package miniProjects.login;

import java.util.Scanner;

public class LoginMain {
    /*
    Project:

    Design a site sign in and login page

    menu: A menu is displayed for user action selection

    register: Obtain name, surname, username and password information from the user.
              Username, password and email are kept in a list.
              Same username or email is not accepted.

    login: Username/email and password are entered.
           If the username or email is not found, a "Not registered, sign up" warning is given.
           If the password registered in the same index as the username/email is verified, the site can be logged in.

    email validation: No spaces
                      Contain @
                      Ends with gmail.com, hotmail.com or yahoo.com
                      The username part of the email (the part before the @) can only contain uppercase and lowercase letters, numbers or symbols such as -,_.

    password validation: No spaces
                         At least 6 characters
                         At least one lowercase letter
                         At least one uppercase letter
                         At least one number
                         At least one symbol
     */

    public static void main(String[] args) {

        start();




    }

    public static void start(){
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

                    break;
                case "3":
                    System.out.println("***** Have a Nice Day *****");
                    break;
                default:
                    System.out.println("You have logged in incorrectly. Please try again");
            }
        }while (!select.equals("3"));

    }


}
