package de.turnertech.thw.cop;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;
    
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(HttpServletResponse.SC_OK);
    
            PrintWriter writer = response.getWriter();
            writer.println("TokenServlet");
        }
}
