<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thông Tin Cầu Thủ</title>
</head>
<body>
<h1>Thông Tin Cầu Thủ</h1>
<form action="players" method="post">
    Tên cầu thủ: <input type="text" name="playerName" required><br>
    Tuổi cầu thủ: <input type="text" name="playerAge" required><br>
    Chỉ số: <select name="indexName" required>
    <option value="">Chọn chỉ số</option>
    <option value="1">Tốc độ</option>
    <option value="2">Sức mạnh</option>
    <option value="3">Độ chính xác</option>
</select><br>
    Giá trị: <input type="number" name="value" required><br>
    <input type="submit" value="Thêm">
</form>

<%
    String DB_URL = "jdbc:mysql://localhost:3306/player_evaluation";
    String USER = ""; // Thay thế bằng tên người dùng của bạn
    String PASS = ""; // Thay thế bằng mật khẩu của bạn

    // Kết nối đến cơ sở dữ liệu
    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM player")) {

        // Hiển thị bảng
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
%>
</body>
</html>