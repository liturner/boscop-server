package de.turnertech.thw.cop;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExampleServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/xml");
        response.setHeader("Cache-Control", "no-store");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter writer = response.getWriter();
        writer.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        writer.println("<gml:FeatureCollection xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" gml:id=\"foo\" xmlns:gml=\"http://www.opengis.net/gml/3.2\" xmlns:cop=\"urn:turnertech:thw:cop\">");
        writer.println("<gml:featureMember>");
        writer.println("<cop:Unit fid=\"TheFuRnRId\">");
        writer.println("<geometry>");
        writer.println("<gml:Point srsName=\"urn:ogc:def:crs:EPSG::4326\">");
        writer.println("<gml:coordinates>0.0,0.0</gml:coordinates>");
        writer.println("</gml:Point>");
        writer.println("</geometry>");
        writer.println("</cop:Unit>");
        writer.println("</gml:featureMember>");
        writer.println("<gml:featureMember>");
        writer.println("<cop:Danger fid=\"TheFuRnRId2\">");
        writer.println("<geometry>");
        writer.println("<gml:Point srsName=\"urn:ogc:def:crs:EPSG::4326\">");
        writer.println("<gml:coordinates>0.0,10.0</gml:coordinates>");
        writer.println("</gml:Point>");
        writer.println("</geometry>");
        writer.println("</cop:Danger>");
        writer.println("</gml:featureMember>");
        writer.println("</gml:FeatureCollection>");
    }
}
