package miniProjects.login;

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
        System.out.println("3. Logout");
        System.out.println("Your selection: ");
    }

    public void register(){
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
        boolean isValid;
        boolean existEmail;
        do {
            System.out.println("Enter Your Email");
            email = scanner.nextLine().trim();
            isValid = validateEmail(email);

            existEmail = emailList.contains(email);
            if (existEmail){
                isValid = false;
                System.out.println("This email has been used before. Please try a new email");
            }

        }while (!isValid);

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


}
