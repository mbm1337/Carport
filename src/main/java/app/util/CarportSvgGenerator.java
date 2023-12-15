package app.util;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import java.io.StringWriter;

public class CarportSvgGenerator {

    public static String generateSvg(double length, double width) throws SVGGraphics2DIOException {
        DOMImplementation domImpl = org.apache.batik.dom.GenericDOMImplementation.getDOMImplementation();
        Document document = domImpl.createDocument("http://www.w3.org/2000/svg", "svg", null);

        SVGGraphics2D svgGraphics2D = new SVGGraphics2D(document);


        drawStolpe(svgGraphics2D, length, width);
        drawSpaer(svgGraphics2D, length, width);
        drawRem(svgGraphics2D, length, width);



        StringWriter writer = new StringWriter();
        svgGraphics2D.stream(writer, true);
        return writer.toString();
    }

    private static void drawStolpe(SVGGraphics2D svgGraphics2D, double length, double width) {
        int stolpebrede = 15; // Bredde af stolper
        int afstanmellomstolper = 240;
        int antalstolper = Math.max(2, (int) Math.ceil(length / afstanmellomstolper));

        // Beregn startposition for stolper
        int start = (int) (100); // Start stolper 1 meter efter spær
        int end = (int) (length-10); // Slut stolper ved carportens slutning

        // Beregn afstand mellem stolper
        int distanceBetweenPosts = (end - start) / (antalstolper - 1);

        // Tegn stolper langs længden med afstand mellem dem
        for (int i = 0; i < antalstolper; i++) {
            int x = start + i * distanceBetweenPosts;
            svgGraphics2D.drawRect(x, 50, stolpebrede, stolpebrede);
            svgGraphics2D.drawRect(x, (int) (50 + width - stolpebrede), stolpebrede, stolpebrede);
        }
    }

    private static void drawSpaer(SVGGraphics2D svgGraphics2D, double length, double width) {
        int afstanmellemspaer = 55;
        int antalspaer = Math.max(2, (int) Math.ceil(length / afstanmellemspaer));

        // Beregn startposition for spær
        int start =0;

        // Beregn afstand mellem spær
        int distanceBetweenSpaer = (int) ((length - start) / (antalspaer - 1));

        // Tegn spærene langs længden med afstand mellem dem
        for (int i = 0; i < antalspaer; i++) {
            int x = start + i * distanceBetweenSpaer;
            svgGraphics2D.drawLine(x, 50, x, (int) (50 + width));
        }
    }

    private static void drawRem(SVGGraphics2D svgGraphics2D, double length, double width) {

        int remY = 50; // Afstand fra toppen af stolperne til remmen

        // Tegn remmen vandret langs den samlede længde
        svgGraphics2D.drawRect(0, remY, (int) length+3, 10);
        svgGraphics2D.drawRect(0, (int) (remY + width-10), (int) length+3,10);
    }


}
