package app.controllers;

import app.entities.Admin;
import app.entities.User;
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


            double length = admin.getLength();
            double width =  admin.getWidth();
            double skurDybde = admin.getShedLength();
            double skurBrede =  admin.getShedWidth();
            boolean skur = admin.isShedSide();


            String svgContent = CarportSvgGenerator.generateSvg(length, width, skurDybde, skurBrede, skur);
            String svgContent2 = SvgGenerator.generateSvg(length, width, skurDybde);

            
            ctx.attribute("svgContent", svgContent);


            ctx.attribute("svgContent2", svgContent2);


    }

}
