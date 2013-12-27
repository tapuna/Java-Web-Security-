/*
 * Copyright (C) 2013 Dominik Schadow, dominikschadow@gmail.com
 *
 * This file is part of Java-Web-Security
.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.dominikschadow.webappsecurity.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.dominikschadow.webappsecurity.token.CSRFTokenHandler;
import org.apache.log4j.Logger;

/**
 * Basic protected servlet for GET and POST requests. Checks the CSRF-Token value to identify
 * CSRF attacks. Prints out all information to standard out and returns the received parameter
 * as response.
 *
 * @author Dominik Schadow
 */
@WebServlet(name = "ProtectedServlet", urlPatterns = {"/ProtectedServlet"})
public class ProtectedServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(ProtectedServlet.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        LOGGER.info("Processing protected GET request");

        response.setContentType("text/html");
        
        try {
            if (!CSRFTokenHandler.isValid(request)) {
                LOGGER.warn("CSRF token is invalid");
                response.setStatus(401);

                try (PrintWriter out = response.getWriter()) {
                    out.println("CSRF token is invalid");
                } catch (IOException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }
                
                return;
            }
        } catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

        LOGGER.info("CSRF token is valid");

        String name = request.getParameter("name");
        LOGGER.info("Received " + name + " as GET parameter");

        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Ch08_CSRF</title>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\" />");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Ch08_CSRF</h1>");
            out.println("<p>Received <b>" + name + "</b> as GET parameter.</p>");
            out.println("<p><a href=\"requests-protected.jsp\">Back</a></p>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        LOGGER.info("Processing protected POST request");

        response.setContentType("text/html");
        
        try {
            if (!CSRFTokenHandler.isValid(request)) {
                LOGGER.warn("CSRF token is invalid");
                response.setStatus(401);

                try (PrintWriter out = response.getWriter()) {
                    out.println("CSRF token is invalid");
                } catch (IOException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }
                
                return;
            }
        } catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

        LOGGER.info("CSRF token is valid");

        String name = request.getParameter("name");
        LOGGER.info("Received " + name + " as POST parameter");

        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Ch08_CSRF</title>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\" />");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Ch08_CSRF</h1>");
            out.println("<p>Received <b>" + name + "</b> as POST parameter.</p>");
            out.println("<p><a href=\"requests-protected.jsp\">Back</a></p>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}