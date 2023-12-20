package app.util;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import java.awt.*;
import java.io.StringWriter;

public class

SvgGenerator {

    public static String generateSvg(double length, double width,double skurDybde) throws SVGGraphics2DIOException {
        DOMImplementation domImpl = org.apache.batik.dom.GenericDOMImplementation.getDOMImplementation();
        Document document = domImpl.createDocument("http://www.w3.org/2000/svg", "svg", null);

        SVGGraphics2D svgGraphics2D = new SVGGraphics2D(document);


        drawStolpe(svgGraphics2D, length, width);
        drawRem(svgGraphics2D, length, width);
        drawShed(svgGraphics2D,length, width, skurDybde);


        StringWriter writer = new StringWriter();
        svgGraphics2D.stream(writer, true);
        return writer.toString();
    }

    private static void drawStolpe(SVGGraphics2D svgGraphics2D, double length, double width) {
        int stolpebrede = 10; // Bredde af stolper
        int afstanmellomstolper = 240;
        int hojde= 270; // Højde af stolper
        int antalstolper = Math.max(2, (int) Math.ceil(length / afstanmellomstolper));

        // Beregn startposition for stolper
        int start = (int) (100); // Start stolper 1 meter efter spær
        int end = (int) (length-10); // Slut stolper ved carportens slutning

        // Beregn afstand mellem stolper
        int distanceBetweenPosts = (end - start) / (antalstolper - 1);

        // Tegn stolper langs længden med afstand mellem dem
        for (int i = 0; i < antalstolper; i++) {
            int x = start + i * distanceBetweenPosts;
            svgGraphics2D.drawRect(x, 50, stolpebrede, hojde);

            // Konverter længden til en streng
            String lengthText = String.valueOf(distanceBetweenPosts);

            // Få FontMetrics for det aktuelle skrifttype
            FontMetrics fontMetrics = svgGraphics2D.getFontMetrics(new Font("Arial", Font.PLAIN, 12)); // Tilpas skrifttypen og størrelsen efter behov

            // Beregn bredden af teksten
            int textWidth = fontMetrics.stringWidth(lengthText);

            // Tegn tekst over stolpen, så den er centralt placeret
            svgGraphics2D.drawString(lengthText, x + (stolpebrede - textWidth) / 2, 45);
        }
    }



    private static void drawRem(SVGGraphics2D svgGraphics2D, double length, double width) {
        int remY = 40; // Afstand fra toppen af stolperne til remmen

        // Tegn remmen vandret langs den samlede længde
        svgGraphics2D.drawRect(0, remY, (int) length, 10);

        // Konverter længden til en streng
        String lengthText = String.valueOf(length);

        // Få FontMetrics for det aktuelle skrifttype
        FontMetrics fontMetrics = svgGraphics2D.getFontMetrics(new Font("Arial", Font.PLAIN, 12)); // Tilpas skrifttypen og størrelsen efter behov

        // Beregn bredden af teksten
        int textWidth = fontMetrics.stringWidth(lengthText);

        // Tegn tekst over remmen, så den er centralt placeret
        svgGraphics2D.drawString(lengthText, (int) (length - textWidth) / 2, remY - 5);
    }

    private static void drawShed(SVGGraphics2D svgGraphics2D, double length, double width, double skurDybde) {
        int afstanmellemplanker = 17;
        int antalspaer = Math.max(2, (int) Math.ceil(skurDybde / afstanmellemplanker));

        // Beregn startposition for spær fra modsatte side
        int start = (int) (length - skurDybde - 10); // Start ved tolpens sidste position fra modsatte side

        // Beregn afstand mellem spær
        int distanceBetweenSpaer = (int) ((skurDybde) / (antalspaer - 1));

        // Beregn bredden af spærene
        int plankeBrede = 15; // Ændr bredden efter behov

        // Tegn spærene langs længden med afstand mellem dem
        for (int i = 0; i < antalspaer; i++) {
            int x = start + i * distanceBetweenSpaer;
            int spaerHeight = 270;



            if ( skurDybde >= 1) {
                svgGraphics2D.drawRect(x, 50, plankeBrede, spaerHeight);
            }
        }
    }





}
