
package com.mycompany.st10447986_prog5121_poe;

// Login.java
public class Login {
    // User information fields
    private String name;
    private String surname;
    private String username;
    private String password;
    private String cellNumber;
    private boolean loginStatus;
    
    // Storing user info
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }
    
    public boolean checkName() {
        return name != null && name.matches(".*[A-Z].*");
    }
    
    public boolean checkSname() {
        return surname != null && surname.matches(".*[A-Z].*");
    }
    
    //Username has an underscore and is no more than 5 char long.
    public boolean checkUserName() {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    //Checks at least 8 char long, contains a number, capital and special char
    public boolean checkPasswordComplexity() {
        return password != null &&
               password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*[0-9].*") &&
               password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }
    
    /*
    OpenAI (2025). ChatGPT. [online] chatgpt.com. 
    Available at: https://chatgpt.com/. AI genreted method to check SA cell number.
    */
    public boolean checkCellPhoneNumber() {
        return cellNumber != null && cellNumber.matches("\\+27\\d{9}");
    }

    // Register the user if all fields pass validation
    public String registerUser() {
        boolean validUsername = checkUserName();
        boolean validPassword = checkPasswordComplexity();
        boolean validCell = checkCellPhoneNumber();
        boolean validName = checkName();
        boolean validSname = checkSname();

        if (!validName) {
            return "Name should include an uppercase letter";
        }
        if (!validSname) {
            return "Surname should include an uppercase letter.";
        }
        if (!validUsername) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!validPassword) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!validCell) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        loginStatus = true; // Set login status as successful registration
        return "User has been registered successfully.";
    }

    // Login verification
    public boolean loginUser(String inputUsername, String inputPassword) {
        return loginStatus && inputUsername.equals(username) && inputPassword.equals(password);
    }

    public String returnLoginStatus(boolean isSuccess) {
        if (isSuccess) {
            return "Welcome " + name + " " + surname + ", it is great to see you again";
        } else {
            return "Login failed. Please check your username and password.";
        }
    }
}
