import java.util.Scanner;

public class Login {

    private String username;
    private String password;
    private boolean isLoggedIn;

    // constructor takes in an int parameter that indicates whether user wants to:
    // 0 = login
    // 1 = create new account
    // 2 = continue as guest
    public Login(int startOption) {

        isLoggedIn = false;

        if (startOption == 0) {
            isLoggedIn = validateLogin();
            if (isLoggedIn) {
                // TODO: go to home feed
            }
        }
        else if (startOption == 1) {
            //createUser();
        }
        else {
            // TODO: continue as guest
        }
    }

    // validate login credentials
    private boolean validateLogin() {
        // don't check password until username is valid
        if (checkUsername()) {
            if (checkPassword(username)) {
                return true;
            }
            else {
                System.out.println("Password did not match username.");
                return false;
            }
        }
        else return false;

    }

    // create new user and add to database
    private void createUser(String username, String password, String fname, String lname, String image) {

        // create a new User object
        User newUser = new User(username, password, fname, lname, image);
        // TODO: add newUser to database
    }


    // HELPER FUNCTIONS

    // used in checkUsername() and createUser() to check if username exists in database
    // returns true if username already exists
    private boolean usernameExists(String username) {
        // TODO: check database for username

        return true;
    }

    // used in validateLogin() to get username from user input and check if it's valid
    private boolean checkUsername() {
        // TODO: get input from text fields instead of console once we have working code for GUI
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username:");
        if (!sc.hasNext()) {
            // if user does not enter a username, show error message and try again
            System.out.print("Username cannot be empty. ");
            sc.reset();
            return checkUsername();
        }
        else {
            if (usernameExists(sc.next())) {
                // username exists so initialize member variable and return true
                username = sc.next();
                sc.close();
                return true;
            } else {
                // username does not exist, so show error message and try again
                System.out.print("Username does not exist. ");
                sc.reset();
                return checkUsername();
            }
        }
    }

    // used in validateLogin() to check if password matches username
    private boolean checkPassword(String username) {
        // TODO: check if password matches username in database

        return true;
    }
}
