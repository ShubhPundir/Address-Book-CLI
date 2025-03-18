package com.find;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void testMainOutput() {
        // Redirect system output for testing
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(outContent));

        // Run the main method
        App.main(new String[]{});

        // Restore original System.out
        System.setOut(originalOut);

        // Debug: Print actual captured output
        String rawOutput = outContent.toString();
        System.out.println("Actual Output: [" + rawOutput + "]");

        // Normalize output by trimming spaces, removing unexpected characters
        String actualOutput = rawOutput.trim().replace("\r\n", "\n");

        // Assertion: Expected output should match "Hello World!"
        assertEquals("Hello World!", actualOutput);
    }
}
