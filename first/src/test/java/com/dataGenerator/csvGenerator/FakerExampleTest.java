package com.dataGenerator.csvGenerator;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;

public class FakerExampleTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Faker faker;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        faker = new Faker();
    }

    

    @Test
    public void testFakerGeneratesValidData() {
        // Test that Faker generates non-empty values
        assertNotNull(faker.name().fullName());
        assertFalse(faker.name().fullName().isEmpty());

        assertNotNull(faker.address().fullAddress());
        assertFalse(faker.address().fullAddress().isEmpty());

        assertNotNull(faker.phoneNumber().cellPhone());
        assertFalse(faker.phoneNumber().cellPhone().isEmpty());

        assertNotNull(faker.company().name());
        assertFalse(faker.company().name().isEmpty());
    }

    @Before
    public void tearDown() {
        System.setOut(originalOut);
    }
}