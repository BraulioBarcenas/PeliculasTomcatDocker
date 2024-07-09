package com.peliculas;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DeleteMovie extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String JDBC_URL = "jdbc:mysql://mysql:3306/peliculas";
    private static String JDBC_USER = "user";
    private static String JDBC_PASS = "password";


   
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        response.setContentType("text/html");
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            String id = request.getParameter("id");

            stmt = conn.createStatement();
            String sql = "DELETE FROM peliculas WHERE id_pelicula="+id;
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