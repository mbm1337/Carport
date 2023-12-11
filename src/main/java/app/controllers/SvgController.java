package app.controllers;

import app.util.SvgGenerator;
import io.javalin.http.Context;
import org.apache.batik.svggen.SVGGraphics2DIOException;

import java.util.HashMap;
import java.util.Map;

public class SvgController {
    public static void getSvg(Context ctx) throws SVGGraphics2DIOException {
        String svgContent = SvgGenerator.generateSvg();

        // Opret et map med variabler til Thymeleaf-template
        Map<String, Object> model = new HashMap<>();
        model.put("svgContent", svgContent);

        // Brug det oprettede map til at sende variabler til Thymeleaf-template
        ctx.render("svg.html", model);
    }
}
