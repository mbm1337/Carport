package app.controllers;

import app.util.CarportSvgGenerator;
import app.util.SvgGenerator;
import io.javalin.http.Context;
import org.apache.batik.svggen.SVGGraphics2DIOException;

import java.util.HashMap;
import java.util.Map;

public class SvgController {
    public static void getSvg(Context ctx) throws SVGGraphics2DIOException {
        double length = 780;
        double width = 300;

        String svgContent = CarportSvgGenerator.generateSvg(length, width);
        String svgContent2 = SvgGenerator.generateSvg(length, width);


        Map<String, Object> model = new HashMap<>();
        model.put("svgContent", svgContent);

        Map<String, Object> model2 = new HashMap<>();
        model.put("svgContent2", svgContent2);


        ctx.render("/svg.html", model);
        ctx.render("/svg.html", model2);
    }
}
