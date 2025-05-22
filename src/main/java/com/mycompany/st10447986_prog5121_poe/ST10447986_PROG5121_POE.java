/*
 * PROG5121 POE Part 2
 * Hredae Moodley
 * ST10447986
 */
package com.mycompany.st10447986_prog5121_poe;

import javax.swing.*;

public class ST10447986_PROG5121_POE {

    private static final Login loginLogic = new Login();
    private static int maxMessagesAllowed = 0;

    public static void main(String[] args) {
        while (true) {
            String[] options = {"Register", "Login", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "Select an option", "Main Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0 ->
                    handleRegistration();
                case 1 ->
                    handleLogin();
                case 2, JOptionPane.CLOSED_OPTION ->
                    System.exit(0);
            }
        }
    }

    private static void handleRegistration() {
        String name = JOptionPane.showInputDialog(null, "Enter your name:");
        if (name == null) {
            return;
        }
        loginLogic.setName(name);

        String surname = JOptionPane.showInputDialog(null, "Enter your surname:");
        if (surname == null) {
            return;
        }
        loginLogic.setSurname(surname);

        String username = JOptionPane.showInputDialog(null, "Enter a username (underscore and max 5 chars):");
        if (username == null) {
            return;
        }
        loginLogic.setUsername(username);

        String password = JOptionPane.showInputDialog(null, "Enter a password (8+ chars, uppercase, digit, special char):");
        if (password == null) {
            return;
        }
        loginLogic.setPassword(password);

        String phone = JOptionPane.showInputDialog(null, "Enter your cell number (+27XXXXXXXXX):");
        if (phone == null) {
            return;
        }
        loginLogic.setCellNumber(phone);

        String result = loginLogic.registerUser();
        JOptionPane.showMessageDialog(null, result);
    }

    private static void handleLogin() {
        String username = JOptionPane.showInputDialog(null, "Enter your username:");
        if (username == null) {
            return;
        }

        String password = JOptionPane.showInputDialog(null, "Enter your password:");
        if (password == null) {
            return;
        }

        boolean success = loginLogic.loginUser(username, password);
        JOptionPane.showMessageDialog(null, loginLogic.returnLoginStatus(success));

        if (success) {
            messagingApp();  // <-- Call messaging menu after login
        }
    }

    private static void messagingApp() {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

        /* ask ONCE how many messages are allowed this session */
        while (true) {
            String input = JOptionPane.showInputDialog("How many messages do you want to send?");
            if (input == null) {
                return;
            }
            try {
                maxMessagesAllowed = Integer.parseInt(input);
                if (maxMessagesAllowed > 0) {
                    break;
                }
            } catch (NumberFormatException ignored) {
            }
            JOptionPane.showMessageDialog(null, "Please enter a positive whole number.");
        }

        /*  main messaging menu  */
        while (true) {
            String menu = """
                          Choose an option:
                           1) Send Messages
                           2) Show recently sent messages
                           3) Quit
                          """;
            String choice = JOptionPane.showInputDialog(menu);
            if (choice == null) {
                break;
            }

            switch (choice) {
                case "1" -> {
                    if (Message.returnTotalMessages() >= maxMessagesAllowed) {
                        JOptionPane.showMessageDialog(null, "Message limit reached.");
                    } else {
                        createAndProcessMessage();
                    }
                }
                case "2" ->
                    JOptionPane.showMessageDialog(null, "Coming Soon.");
                case "3" -> {
                    JOptionPane.showMessageDialog(null,
                            "Total messages sent: " + Message.returnTotalMessages());
                    return;   // exit to main menu
                }
                default ->
                    JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }
    }

    /* helper – gathers input, builds Message object, invokes its workflow   */
    private static void createAndProcessMessage() {
        String recipient = JOptionPane.showInputDialog("Enter recipient number (e.g. +27123456789):");
        if (recipient == null) {
            return;
        }

        String text = JOptionPane.showInputDialog("Enter your message (max 250 characters):");
        if (text == null) {
            return;
        }

        /* create the message (validation happens inside) */
        Message msg = new Message(recipient, text);
        if (!msg.isValid()) {
            JOptionPane.showMessageDialog(null, msg.getValidationError());
            return; // skip sending or storing
        }
        msg.handleUserAction();
        // ask Send / Discard / Store
    }
}
