import app.util.Calculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    public void testCalculateArea() {
        Calculator calculator = new Calculator();
        int area = calculator.calculateArea(5, 10);
        assertEquals(50, area);
    }

    @Test
    public void testNumberOfPosts() {
        Calculator calculator = new Calculator();
        int posts = calculator.numberOfPosts(700);
        assertEquals(6, posts);
    }

    @Test
    public void testSpaerAmount() {
        int spaer = Calculator.spaerAmount(650);
        assertEquals(12, spaer);
    }

    @Test
    public void testBeamAmount() {
        int beams = Calculator.beamAmount(700);
        assertEquals(3, beams);
    }


    @Test
    public void testScrewPost() {
        Calculator calculator = new Calculator();
        int screws = calculator.screwPost(700);
        assertEquals(60, screws);
    }


    @Test
    public void testBeslagPost() {
        Calculator calculator = new Calculator();
        int beslag = calculator.beslagPost(500);
        assertEquals(4, beslag);
    }



    @Test
    public void testShedArea() {
        Calculator calculator = new Calculator();
        int area = calculator.shedArea(210, 210);
        assertEquals(44100, area);
    }

    @Test
    public void testBeklaedning() {
        Calculator calculator = new Calculator();
        int beklaedning = calculator.beklaedning(210, 210);
        assertEquals(108, beklaedning);
    }

    @Test
    public void testNumberOfStolperPerSkur() {
        Calculator calculator = new Calculator();
        int stolper = calculator.numberOfStolperPerSkur(210, 210);
        assertEquals(8, stolper);
    }
}
