package com.peliculas;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UpdateMovie extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // private static String JDBC_URL = "jdbc:mysql://mysql:3306/peliculas";
    // private static String JDBC_USER = "user";
    // private static String JDBC_PASS = "password";
    private static String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/peliculas";
    private static String JDBC_USER = "root";
    private static String JDBC_PASS = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Renta peliculas</title>");
        out.println("<link rel='stylesheet' href='https://cdn.simplecss.org/simple.min.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Inventario de peliculas</h1>");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            String id = request.getParameter("id");

            stmt = conn.createStatement();
            String sql = "SELECT id_pelicula, nombre FROM peliculas WHERE id_pelicula="+id;
            rs = stmt.executeQuery(sql);
            rs.next();
            String idPelicula = rs.getString("id_pelicula");
            String nombrePelicula = rs.getString("nombre");

            out.println("<form action='update_movie' method='post'>");
            out.println("<input type='text' name='id_pelicula' id='id_pelicula' value='"+idPelicula+"' hidden>");
            out.println("<label for='nombre'>Editar pelicula</label>");
            out.println("<input type='text' name='nombre' id='nombre' value='"+nombrePelicula+"'>");
            out.println("<button type='submit'>Enviar</button>");
            out.println("</form>");

        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            out.println("<p>Products not found!</p>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        out.println("</html></body>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        response.setContentType("text/html");


        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            String id = request.getParameter("id_pelicula");
            String nombre = request.getParameter("nombre");

            stmt = conn.createStatement();
            String sql = "UPDATE peliculas SET nombre='"+nombre+"' WHERE id_pelicula="+id;
            stmt.execute(sql);
            response.sendRedirect("/TomcatServer/tomcat_server");
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}