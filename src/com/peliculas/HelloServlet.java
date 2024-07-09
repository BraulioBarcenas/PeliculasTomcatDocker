package com.peliculas;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String JDBC_URL = "jdbc:mysql://mysql:3306/peliculas";
    private static String JDBC_USER = "user";
    private static String JDBC_PASS = "password";


    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html lang='es'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Renta peliculas</title>");
        out.println("<link rel='stylesheet' href='https://cdn.simplecss.org/simple.min.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div>");
        out.println("<h1>Inventario de peliculas</h1>");
        out.println("<img src='https://static.vecteezy.com/system/resources/previews/002/236/321/non_2x/movie-trendy-banner-vector.jpg'>");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        out.println("<p><a href='/TomcatServer/NewMovieForm.html'>Agregar nueva pelicula</a></p>");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            stmt = conn.createStatement();
            String sql = "SELECT id_pelicula, nombre FROM peliculas";
            rs = stmt.executeQuery(sql);
                out.println("<table>");
                    out.println("<thead>");
                    out.println("<tr>");
                        out.println("<th>ID</th>");
                        out.println("<th>Nombre</th>");
                        out.println("<th colspan='2'>Acciones</th>");
                    out.println("<tr>");
                    out.println("</thead>");
                    out.println("<tbody>");
                    while (rs.next()) {
                        int id = rs.getInt("id_pelicula");
                        String name = rs.getString("nombre");
                        out.println("<tr>");
                            out.println("<td>"+id+"</td>");
                            out.println("<td>"+name+"</td>");
                            out.println("<td><a href='/TomcatServer/update_movie?id="+id+"'>Editar</a></td>");
                            out.println("<td><a href='/TomcatServer/delete_movie?id="+id+"'>Eliminar</a></td>");
                        out.println("<tr>");
                    }
                    out.println("</tbody>");
                out.println("</table>");
                out.println("</div>");

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
}