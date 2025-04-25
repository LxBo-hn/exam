package com.t2308e.examtest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/players")
public class PlayerServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/player_evaluation";
    private static final String USER = "username";
    private static final String PASS = "password";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Thông Tin Cầu Thủ</h1>");
        out.println("<form action='players' method='post'>");
        out.println("Tên cầu thủ: <input type='text' name='playerName' required><br>");
        out.println("Tuổi cầu thủ: <input type='text' name='playerAge' required><br>");
        out.println("Chỉ số: <select name='indexName' required>");
        out.println("<option value=''>Chọn chỉ số</option>");
        out.println("<option value='1'>Tốc độ</option>");
        out.println("<option value='2'>Sức mạnh</option>");
        out.println("<option value='3'>Độ chính xác</option>");
        out.println("</select><br>");
        out.println("Giá trị: <input type='number' name='value' required><br>");
        out.println("<input type='submit' value='Thêm'>");
        out.println("</form>");

        // Đọc và hiển thị tất cả cầu thủ
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM player")) {

            out.println("<table border='1'><tr><th>Id</th><th>Tên cầu thủ</th><th>Tuổi</th><th>Hành động</th></tr>");
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("player_id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("age") + "</td>");
                out.println("<td><a href='edit?id=" + rs.getInt("player_id") + "'>Chỉnh sửa</a> | <a href='delete?id=" + rs.getInt("player_id") + "'>Xóa</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("playerName");
        String age = request.getParameter("playerAge");
        int index_id = Integer.parseInt(request.getParameter("indexName"));
        float value = Float.parseFloat(request.getParameter("value"));

        // Chèn cầu thủ vào cơ sở dữ liệu
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO player (name, full_name, age, index_id) VALUES (?, ?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, name); // full_name giống tên
            pstmt.setString(3, age);
            pstmt.setInt(4, index_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("players");
    }
}