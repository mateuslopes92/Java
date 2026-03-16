package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class GraderTest {

    private Grader grader;

    @BeforeEach
    void setUp() {
        grader = new Grader();
    }

    @ParameterizedTest
    @DisplayName("Grades below 60 should return F")
    @ValueSource(ints = {0, 10, 30, 59})
    void shouldReturnF(int score){
        assertEquals('F', grader.determineLetterGrade(score));
    }

    @ParameterizedTest
    @DisplayName("Verify correct letter grade for boundary values")
    @CsvSource({
            "69, D",
            "79, C",
            "89, B",
            "99, A"
    })
    void shouldReturnCorrectGrade(int score, char expectedGrade){
        assertEquals(expectedGrade, grader.determineLetterGrade(score));
    }

    @Test
    @DisplayName("Negative grades should throw exception")
    void negativeGradeShouldThrowException(){
        assertThrows(
                IllegalArgumentException.class,
                () -> grader.determineLetterGrade(-1)
        );
    }

    @Test
    @DisplayName("Grade 100 should still return A")
    void hundredShouldReturnA(){
        assertEquals('A', grader.determineLetterGrade(100));
    }
}