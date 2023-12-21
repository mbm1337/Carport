package app.util;

import app.entities.Admin;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class CarportSvgGenerator {
    static List<int[]> stolpeKoordinater;

    public static String generateSvg(double length, double width,double skurDybde ,double skurBrede,boolean skurside) throws SVGGraphics2DIOException {
        DOMImplementation domImpl = org.apache.batik.dom.GenericDOMImplementation.getDOMImplementation();
        Document document = domImpl.createDocument("http://www.w3.org/2000/svg", "svg", null);

        SVGGraphics2D svgGraphics2D = new SVGGraphics2D(document);


        drawStolpe(svgGraphics2D, length, width);
        drawSpaer(svgGraphics2D, length, width);
        drawRem(svgGraphics2D, length, width);
        drawShed(svgGraphics2D,length, width, skurDybde, skurBrede, skurside);



        StringWriter writer = new StringWriter();
        svgGraphics2D.stream(writer, true);
        return writer.toString();
    }

    private static List<int[]> drawStolpe(SVGGraphics2D svgGraphics2D, double length, double width) {
        stolpeKoordinater = new ArrayList<>();
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
            svgGraphics2D.drawRect(x, 65, stolpebrede, stolpebrede);
            svgGraphics2D.drawRect(x, (int) (35 + width - stolpebrede), stolpebrede, stolpebrede);

            stolpeKoordinater.add(new int[]{x, 0});
            stolpeKoordinater.add(new int[]{x, (int) (0 + width - stolpebrede)});

        }
        return stolpeKoordinater;
    }

    private static void drawSpaer(SVGGraphics2D svgGraphics2D, double length, double width) {
        int afstanmellemspaer = 55;
        int antalspaer = Math.max(2, (int) Math.ceil(length / afstanmellemspaer));

        // Beregn startposition for spær
        int start = 0;

        // Beregn afstand mellem spær
        int distanceBetweenSpaer = (int) ((length - start) / (antalspaer - 1));

        // Tegn spærene langs længden med afstand mellem dem
        for (int i = 0; i < antalspaer; i++) {
            int x = start + i * distanceBetweenSpaer;
            int spaerWidth = 5; // Ændr bredden efter behov
            int spaerHeight = (int) width;

            svgGraphics2D.drawRect(x, 50, spaerWidth, spaerHeight);
        }
    }

    private static void drawRem(SVGGraphics2D svgGraphics2D, double length, double width) {

        int remY = 25; // Afstand fra toppen af stolperne til remmen

        // Tegn remmen vandret langs den samlede længde
        svgGraphics2D.drawRect(0, 65, (int) length+3, 10);
        svgGraphics2D.drawRect(0, (int) (remY + width), (int) length+3,10);
    }


    private static void drawShed(SVGGraphics2D svgGraphics2D, double length, double width, double skurDybde, double skurBrede, boolean startFromRight) {

        int stolpebrede = 15; // Bredde af stolper


        boolean topLeftMatch = false;
        boolean bottomLeftMatch = false;
        boolean topRightMatch = false;
        boolean bottomRightMatch = false;
        int shedX; // X-positionen for skuret
        int shedY; // Y-positionen for skuret
        double tolerance = 0.1;// Tolerance for at matche stolperne 0.1 = 10% indenfor forventet værdi



        if (startFromRight) {
            shedX = (int) (length - skurDybde);
        } else {
            shedX = (int) (length - skurDybde);
        }

        if (startFromRight) {
            shedY = 65; // Toppen af carporten
        } else {
            shedY = (int) (35+width - skurBrede); // Bund af carporten
        }

        int TopLeftX = shedX;
        int TopLeftY = shedY;
        int BottomLeftX = shedX;
        int BottomLeftY = (int) (shedY + skurBrede - stolpebrede);
        int TopRightX = (int) (shedX + skurDybde - stolpebrede);
        int TopRightY = shedY;
        int BottomRightX = (int) (shedX + skurDybde - stolpebrede);
        int BottomRightY = (int) (shedY + skurBrede - stolpebrede);



        // Tegn skuret
        svgGraphics2D.drawRect(shedX, shedY, (int) skurDybde, (int) skurBrede);


        // Tjek om stolperne matcher skurets hjørner

        for (int[] stolpeKoordinat : stolpeKoordinater) {
            int stolpeCarportX = stolpeKoordinat[0];
            int stolpeCarportY = stolpeKoordinat[1];

            if (!topLeftMatch &&
                    (stolpeCarportX - TopLeftX) <= tolerance * TopLeftX &&
                    (TopLeftX - stolpeCarportX) <= tolerance * TopLeftX &&
                    (stolpeCarportY - TopLeftY) <= tolerance * TopLeftY &&
                    (TopLeftY - stolpeCarportY) <= tolerance * TopLeftY) {
                topLeftMatch = true;
            }

            if (!bottomLeftMatch &&
                    (stolpeCarportX - BottomLeftX) <= tolerance * BottomLeftX &&
                    (BottomLeftX - stolpeCarportX) <= tolerance * BottomLeftX &&
                    (stolpeCarportY - BottomLeftY) <= tolerance * BottomLeftY &&
                    (BottomLeftY - stolpeCarportY) <= tolerance * BottomLeftY) {
                bottomLeftMatch = true;
            }

            if (!topRightMatch &&
                    (TopRightX - stolpeCarportX) <= tolerance * TopRightX &&
                    (stolpeCarportX - TopRightX) <= tolerance * TopRightX &&
                    (stolpeCarportY - TopRightY) <= tolerance * TopRightY &&
                    (TopRightY - stolpeCarportY) <= tolerance * TopRightY) {
                topRightMatch = true;
            }

            if (!bottomRightMatch &&
                    (stolpeCarportX - BottomRightX) <= tolerance * BottomRightX &&
                    (BottomRightX - stolpeCarportX) <= tolerance * BottomRightX &&
                    (stolpeCarportY - BottomRightY) <= tolerance * BottomRightY &&
                    (BottomRightY - stolpeCarportY) <= tolerance * BottomRightY) {
                bottomRightMatch = true;
            }

        }

        if (!topLeftMatch) {
            svgGraphics2D.drawRect(shedX, shedY, stolpebrede, stolpebrede);
        }
        if (!bottomLeftMatch) {
            svgGraphics2D.drawRect(shedX, BottomLeftY, stolpebrede, stolpebrede);
        }
        if (!topRightMatch) {
            svgGraphics2D.drawRect(TopRightX, shedY, stolpebrede, stolpebrede);
        }
        if (!bottomRightMatch) {
            svgGraphics2D.drawRect(BottomRightX, BottomRightY, stolpebrede, stolpebrede);
        }
        if ( skurBrede >= 1) {
            svgGraphics2D.drawRect(shedX, BottomLeftY -55, stolpebrede, stolpebrede);
        }
       // svgGraphics2D.drawRect(shedX, BottomLeftY -55, stolpebrede, stolpebrede);

    }



}






