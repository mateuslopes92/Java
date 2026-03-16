package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleCalculatorTest {

    private SimpleCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new SimpleCalculator();
    }

    @Test
    @DisplayName("2 + 2 should equal 4")
    void twoPlusTwoShouldEqualFour(){
        int result = calculator.add(2,2);

        assertEquals(4, result);
    }

    @Test
    @DisplayName("3 + 7 should equal 10")
    void threePlusSevenShouldEqualTen(){
        int result = calculator.add(3,7);

        assertEquals(10, result);
    }

    @Test
    @DisplayName("Addition results should always be positive for positive numbers")
    void resultShouldBePositive(){
        int result = calculator.add(5,5);

        assertTrue(result > 0);
    }

    @Test
    @DisplayName("Multiple assertions example")
    void multipleAssertions(){
        int result = calculator.add(4,6);

        assertAll(
                () -> assertEquals(10, result),
                () -> assertTrue(result > 0)
        );
    }
}