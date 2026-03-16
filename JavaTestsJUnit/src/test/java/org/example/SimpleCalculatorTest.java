package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleCalculatorTest {
    @Test
    void twoPlusTwoShouldEqualFour(){
        SimpleCalculator calculator = new SimpleCalculator();
        int result = calculator.add(2, 2);

        assertEquals(4, result);
    }

    @Test
    void threePlusSevenShouldEqualTen(){
        SimpleCalculator calculator = new SimpleCalculator();
        int result = calculator.add(3, 7);

        assertEquals(10, result);
    }
}