package app.controllers;

import app.entities.Admin;
import app.persistence.AdminMapper;
import app.persistence.ConnectionPool;
import app.util.CarportSvgGenerator;
import app.util.SvgGenerator;
import io.javalin.http.Context;
import org.apache.batik.svggen.SVGGraphics2DIOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvgController {
    public static void getSvg(Context ctx, ConnectionPool connectionPool) throws SVGGraphics2DIOException {
        int orderNumber = Integer.parseInt(ctx.pathParam("ordernumber"));

        // Fetch the order details from the database based on orderNumber
        Admin admin = AdminMapper.getOrderDetails(orderNumber, connectionPool);

        if (admin != null) {
            double length = admin.getLength();
            double width =  admin.getWidth();

            String svgContent = CarportSvgGenerator.generateSvg(length, width);
            String svgContent2 = SvgGenerator.generateSvg(length, width);

            Map<String, Object> model = new HashMap<>();
            model.put("svgContent", svgContent);

            Map<String, Object> model2 = new HashMap<>();
            model2.put("svgContent2", svgContent2);

            ctx.render("/tilbud.html", model);
            ctx.render("/tilbud.html", model2);
        } else {
            // Handle the case where OrderDetail is not found for the provided orderNumber
            ctx.result("OrderDetail not found for the provided orderNumber");
        }
    }

}
