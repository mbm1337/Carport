package app.controllers;

import app.util.CarportSvgGenerator;
import io.javalin.http.Context;
import org.apache.batik.svggen.SVGGraphics2DIOException;

import java.util.HashMap;
import java.util.Map;

public class SvgController {
    public static void getSvg(Context ctx) throws SVGGraphics2DIOException {
        double length = 400;
        double width = 200;

        String svgContent = CarportSvgGenerator.generateSvg(length, width);


        Map<String, Object> model = new HashMap<>();
        model.put("svgContent", svgContent);


        ctx.render("/svg.html", model);
    }
}
