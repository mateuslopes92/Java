package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraderTest {

    @Test
    void fifthNineShouldReturnF() {
        var grader = new Grader();
        var result = grader.determineLetterGrade(59);

        assertEquals('F', result);
    }

    @Test
    void sixtyNineShouldReturnD() {
        var grader = new Grader();
        var result = grader.determineLetterGrade(69);

        assertEquals('D', result);
    }

    @Test
    void senventyNineShouldReturnC() {
        var grader = new Grader();
        var result = grader.determineLetterGrade(79);

        assertEquals('C', result);
    }

    @Test
    void eightyNineShouldReturnB() {
        var grader = new Grader();
        var result = grader.determineLetterGrade(89);

        assertEquals('B', result);
    }

    @Test
    void ninetyNineShouldReturnA() {
        var grader = new Grader();
        var result = grader.determineLetterGrade(99);

        assertEquals('A', result);
    }

    @Test
    void negativeOneShouldReturnIllegalArgumentException() {
        var grader = new Grader();

        assertThrows(IllegalArgumentException.class, () -> { grader.determineLetterGrade(-1); });
    }

}