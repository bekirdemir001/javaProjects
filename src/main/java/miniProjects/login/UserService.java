package miniProjects.login;

import miniProjects.DatabaseUtilities;
import miniProjects.ReusableMethods;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService {
    List<String> usernameList = new ArrayList<>();
    List<String> emailList = new ArrayList<>();
    List<String> passwordList = new ArrayList<>();

    public void showMenu(){
        System.out.println("***** WELCOME *****");
        System.out.println("1. Sign In");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.println("Your selection: ");
    }

    public void register() throws ClassNotFoundException, SQLException {

        //Get name
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Your Name and Surname");
        String name = scanner.nextLine();

        //Get username
        String username;
        boolean existUsername;
        do {
            System.out.println("Enter Your Username");
            username = scanner.nextLine();
            existUsername = usernameList.contains(username);
            if (existUsername){
                System.out.println("This username has been used before. Please try a new username");
            }
        }while (existUsername);

        //Get email
        String email;
        boolean isValidEmail;
        boolean existEmail;
        do {
            System.out.println("Enter Your Email");
            email = scanner.nextLine().trim();
            isValidEmail = validateEmail(email);

            existEmail = emailList.contains(email);
            if (existEmail){
                isValidEmail = false;
                System.out.println("This email has been used before. Please try a new email");
            }
        }while (!isValidEmail);

        //Get password
        String password;
        boolean isValidPassword;

        do {
            System.out.println("Enter Your Password");
            password = scanner.nextLine();
            isValidPassword = validatePassword(password);
        }while (!isValidPassword);

        //Create users and add their information into the lists
        User user = new User(name, username, email, password);
        usernameList.add(username);
        emailList.add(email);
        passwordList.add(password);

        //Get connection to the local database and add users information into the local database
        List<User> users = new ArrayList<>();
        users.add(new User(name, username, email, password));
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/jdbc","postgres","Bekrarum3401-");

        Statement st = con.createStatement();

        String sql = "INSERT INTO users VALUES (?, ?, ?, ?)";


        PreparedStatement preparedStatement = con.prepareStatement(sql);

        for (User each : users) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);

            preparedStatement.addBatch();

        }
        preparedStatement.executeBatch();

        con.close();
        st.close();
        preparedStatement.close();

        System.out.println("-------------------------------------------------------------------------");
        ReusableMethods.slowPrint("Congratulations, your registration has been completed.", 50);
        System.out.println();
        ReusableMethods.slowPrint("You can log in to the system with your username or email and password.", 50);
        System.out.println();
        System.out.println("-------------------------------------------------------------------------");

    }

    public void login(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter username or email");
        String usernameOrEmail = scanner.nextLine();

        boolean isEmail = emailList.contains(usernameOrEmail);
        boolean isUsername = usernameList.contains(usernameOrEmail);

        if (isEmail || isUsername){
            int counter = 5;
            while (true){
                System.out.println("Enter your password");
                String password = scanner.nextLine();

                int index;
                if (isUsername){
                    index = usernameList.indexOf(usernameOrEmail);
                } else {
                    index = emailList.indexOf(usernameOrEmail);
                }

                if (passwordList.get(index).equals(password)){
                    System.out.println("You logged in to the system successfully");
                    break;
                }else {
                    counter--;
                    if (counter>0){
                        System.out.println("You entered wrong password! Please try again");
                        System.out.println("Your remaining number of attempts: " + counter);
                    }else {
                        System.out.println("There is no remaining attempt. Good Bye!");
                        break;
                    }
                }
            }
        }else {
            System.out.println("The user registered to the system was not found!");
            System.out.println("Please check your credentials or sign in");
        }

    }

    public static boolean validateEmail(String email){
        boolean isValid;

        boolean space = email.contains(" ");
        boolean isContainAt = email.contains("@");
        if (space){
            System.out.println("Email contains no space");
            isValid = false;
        } else if (!isContainAt) {
            System.out.println("Email contains '@'");
            isValid = false;
        }else {
            String firstPart = email.split("@")[0];
            String secondPart = email.split("@")[1];

            boolean checkFirstPart = firstPart.replaceAll("[a-zA-Z0-9_.-]", "").length() ==0;
            boolean checkSecondPart = secondPart.equals("gmail.com") ||
                                        secondPart.equals("hotmail.com") ||
                                        secondPart.equals("yahoo.com");

            if (!checkFirstPart){
                System.out.println("Email has no characters except lowercase, uppercase, numbers or symbols such as _.-");
            } else if (!checkSecondPart) {
                System.out.println("Email must end with gmail.com, hotmail.com or yahoo.com");
            }

            isValid = checkFirstPart && checkSecondPart;
        }
        return isValid;
    }


    public static boolean validatePassword(String password){
        boolean isValidPassword;

        boolean space = password.contains(" ");
        boolean lengthGreaterThan6 = password.length() >= 6;
        boolean existUppercase = password.replaceAll("[^A-Z]", "").length()>0;
        boolean existLowercase = password.replaceAll("[^a-z]", "").length()>0;
        boolean existDigit = password.replaceAll("[^0-9]", "").length()>0;
        boolean existSymbol = password.replaceAll("[\\P{Punct}]", "").length()>0;

        if (space){
            System.out.println("Password has no space");
        } else if (!lengthGreaterThan6) {
            System.out.println("Password contains at least 6 character");
        }else if (!existUppercase) {
            System.out.println("Password contains at least 1 uppercase");
        }else if (!existLowercase) {
            System.out.println("Password contains at least 1 lowercase");
        }else if (!existDigit) {
            System.out.println("Password contains at least 1 digit");
        }else if (!existSymbol) {
            System.out.println("Password contains at least 1 symbol");
        }

        isValidPassword = !space && lengthGreaterThan6 && existUppercase && existLowercase && existDigit && existSymbol;

        if (!isValidPassword){
            System.out.println("Please try again");
        }

        return isValidPassword;
    }

    public static boolean createTableInDatabase(){
        String sql = "CREATE TABLE users (name VARCHAR(30), username VARCHAR(30), email VARCHAR(30), password)";
        DatabaseUtilities.createConnection();
        DatabaseUtilities.executeQuery(sql);
        DatabaseUtilities.closeConnection();
        return false;
    }

}
