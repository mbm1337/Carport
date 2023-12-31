package app.entities;

import app.entities.Calculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    @Test
    void testNumberOfPostsForSmallCarport() {
        // Arrange
        Calculator calculator = new Calculator();

        // Act
        int result = calculator.numberOfPosts(500);

        // Assert
        assertEquals(4, result);
    }

}