package com.mycompany.st10447986_prog5121_poe;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    private Message validMessageSend;
    private Message invalidNumberMessageDiscard;

    @Before
    public void setUp() {
        // Test data 1
        validMessageSend = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight");

        // Test data 2
        invalidNumberMessageDiscard = new Message("08575975889", "Hi Keegan, did you receive the payment?");
    }

    @Test
    public void testMessageLengthValid() {
        String result = validMessageSend.checkMessageLength();
        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testMessageLengthInvalid() {
        String longText = "a".repeat(260);
        Message m = new Message("+27718693002", longText);
        String result = m.checkMessageLength();
        assertEquals("Message exceeds 250 characters by 10, please reduce size.", result);
    }

    @Test
    public void testValidRecipientNumber() {
        String result = validMessageSend.checkRecipientCell();
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testInvalidRecipientNumber() {
        String result = invalidNumberMessageDiscard.checkRecipientCell();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }

    @Test
    public void testMessageHashFormat() {
        String hash = validMessageSend.createMessageHash();
        assertTrue(hash.matches("\\d{2}:\\d+:[A-Z]+[A-Z]+"));
    }

    @Test
    public void testMessageIDGenerated() {
        String id = validMessageSend.getMessageID();
        assertNotNull(id);
        assertEquals(10, id.length());
        assertTrue(id.matches("\\d{10}"));
    }

    @Test
    public void testSendMessage() {
        String result = validMessageSend.sendMessage(1);
        assertEquals("Message successfully sent.", result);
    }

    @Test
    public void testDiscardMessage() {
        String result = invalidNumberMessageDiscard.sendMessage(2);
        assertEquals("Press 0 to delete message.", result);
    }

    @Test
    public void testStoreMessage() {
        String result = validMessageSend.sendMessage(3);
        assertEquals("Message successfully stored.", result);
    }

    @Test
    public void testTotalMessagesCount() {
        int initialCount = Message.returnTotalMessages();
        validMessageSend.sendMessage(1);
        validMessageSend.sendMessage(3);
        assertEquals(initialCount + 2, Message.returnTotalMessages());
    }
}
