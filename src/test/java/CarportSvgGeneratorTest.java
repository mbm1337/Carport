

import app.util.CarportSvgGenerator;

import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CarportSvgGeneratorTest {

    @Test
    void testGenerateSvg() throws SVGGraphics2DIOException, SAXException {
        double length = 100.0;
        double width = 50.0;

        String svg = CarportSvgGenerator.generateSvg(length, width);

        // Validate the SVG content based on your expectations
        assertTrue(svg.contains("<rect x=\""), "Check for the presence of rectangles in the SVG");

    }
}
