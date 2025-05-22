package com.mycompany.st10447986_prog5121_poe;

import javax.swing.*;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents ONE user‑created message + static helpers for totals
 */
public class Message {

    /* ────────────────  static tracking  ──────────────── */
    private static int totalMessages = 0;          // counts SENT or STORED messages
    private static final ArrayList<String> allMessages = new ArrayList<>();

    /* ────────────────  instance fields  ──────────────── */
    private final String messageID;    // 10‑digit random
    private final String recipient;    // validated cell number
    private final String text;         // validated ≤ 250 chars
    private final String hash;         // created from spec
    private final boolean isValid;
    private final String validationError;
    private final int originalLength;


    /* ────────────────  ctor does validation only  ──────────────── */
    public Message(String recipient, String text) {
        this.messageID = generateMessageID();
        this.originalLength = text.length(); // store unmodified length

        boolean valid = true;
        StringBuilder errors = new StringBuilder();

        if (!recipient.matches("\\+27\\d{9}")) {
            valid = false;
            errors.append("Cell phone number is incorrectly formatted or does not contain an international code.");
            this.recipient = "INVALID";
        } else {
            this.recipient = recipient;
        }

        if (text.length() > 250) {
            valid = false;
            if (errors.length() > 0) {
                errors.append(" ");
            }
            int excess = text.length() - 250;
            errors.append("Message exceeds 250 characters by ").append(excess).append(", please reduce size.");
            this.text = text.substring(0, 250); // truncate to avoid error in later processing
        } else {
            this.text = text;
        }

        this.hash = createMessageHash();
        this.isValid = valid;
        this.validationError = errors.toString();
    }

    public boolean isValid() {
        return isValid;
    }

    public String getValidationError() {
        return validationError;
    }


    /* ────────────────  helper – random 10‑digit id  ──────────────── */
    private String generateMessageID() {
        long num = (long) (new Random().nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return String.format("%010d", num);
    }

    public String createMessageHash() {
        String[] words = text.split("\\s+");
        String first = words.length > 0 ? words[0].toUpperCase() : "NONE";
        String last = words.length > 1 ? words[words.length - 1].toUpperCase() : first;
        return messageID.substring(0, 2) + ":" + (totalMessages + 1) + ":" + first + last;
    }

    /* ────────────────  user workflow  ──────────────── */
    public void handleUserAction() {
        String[] opts = {"Send Message", "Disregard Message", "Store Message to send later"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose an action for the message",
                "Message Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opts,
                opts[0]);

        switch (choice) {
            case 0 ->
                doSend();
            case 1 ->
                JOptionPane.showMessageDialog(null, "Message discarded.");
            case 2 ->
                doStore();
            default ->
                JOptionPane.showMessageDialog(null, "No action taken.");
        }
    }

    private void doSend() {
        ++totalMessages;
        allMessages.add(toString());
        JOptionPane.showMessageDialog(null, "Message sent.\n\n" + toString());
    }

    private void doStore() {
        ++totalMessages;
        allMessages.add(toString());
        storeToJson();
        JOptionPane.showMessageDialog(null, "Message stored for later.\n\n" + toString());
    }

    /*
    OpenAI (2025). ChatGPT. [online] chatgpt.com. 
    Available at: https://chatgpt.com/. JSON persistence.
     */
    private void storeToJson() {
        JSONObject json = new JSONObject();
        json.put("MessageID", messageID);
        json.put("Recipient", recipient);
        json.put("Message", text);
        json.put("Hash", hash);

        try (FileWriter fw = new FileWriter("messages.json", true)) {
            fw.write(json.toJSONString() + System.lineSeparator());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to messages.json");
        }
    }

    public static String printMessages() {
        return String.join("\n\n", allMessages);
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    @Override
    public String toString() {
        return """
               MessageID  : %s
               MessageHash: %s
               Recipient  : %s
               Message    : %s
               """.formatted(messageID, hash, recipient, text);
    }

    // Check message length and return a testable message
    public String checkMessageLength() {
        if (originalLength <= 250) {
            return "Message ready to send.";
        } else {
            int excess = originalLength - 250;
            return "Message exceeds 250 characters by " + excess + ", please reduce size.";
        }
    }

// Check if recipient number is correctly formatted
    public String checkRecipientCell() {
        if (recipient.equals("INVALID")) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        } else {
            return "Cell phone number successfully captured.";
        }
    }

// Message ID getter for testing
    public String getMessageID() {
        return messageID;
    }

// Simulated action logic for unit testing
    public String sendMessage(int option) {
        switch (option) {
            case 1:
                ++totalMessages;
                allMessages.add(toString());
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete message.";
            case 3:
                ++totalMessages;
                allMessages.add(toString());
                storeToJson();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

}
