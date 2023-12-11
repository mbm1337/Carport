package app.util;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import java.io.StringWriter;

public class SvgGenerator {
    public static String generateSvg() throws SVGGraphics2DIOException {
        // Opret en DOM-implementering og et Document-objekt
        DOMImplementation domImpl = org.apache.batik.dom.GenericDOMImplementation.getDOMImplementation();
        Document document = domImpl.createDocument("http://www.w3.org/2000/svg", "svg", null);

        // Opret en SVGGraphics2D fra Document
        SVGGraphics2D svgGraphics2D = new SVGGraphics2D(document);

        // Tegn stolpe 1
        svgGraphics2D.drawRect(50, 50, 10, 10);

        // Tegn stolpe 2
        svgGraphics2D.drawRect(310, 50, 10, 10);

        // Tegn stolpe 3
        svgGraphics2D.drawRect(530, 50, 10, 10);

        // Tegn stolpe 4
        svgGraphics2D.drawRect(50, 250, 10, 10);

        // Tegn stolpe 5
        svgGraphics2D.drawRect(290, 250, 10, 10);

        // Tegn stolpe 6
        svgGraphics2D.drawRect(530, 250, 10, 10);


        // Konverter SVGGraphics2D til SVG-streng
        StringWriter writer = new StringWriter();
        svgGraphics2D.stream(writer, true);
        String svgString = writer.toString();

        return svgString;
    }
}
