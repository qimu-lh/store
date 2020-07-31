<%@ page contentType="text/html"%>
<%@page pageEncoding="GB2312"%>
<%@page import="java.sql.*" %>  <%--导入java.sql包--%>
<html>
<head>
    <title >从MySQL数据库中读出student表</title>
</head>
<body>
<%
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");  ////驱动程序名
        String url = "jdbc:mysql://localhost:3306/day17?serverTimezone=GMT%2B8&useSSL=false"; //数据库名
        String username = "root";  //数据库用户名
        String password = "123456";  //数据库用户密码
        Connection conn = DriverManager.getConnection(url, username, password);  //连接状态

        if(conn != null){
            out.print("数据库连接成功！");
            out.print("<br />");
%>
<table border="2">
    <tr>
        <td width="100" number="title">s_id</td>
        <td width="100" name="title">s_name</td>
        <td width="100" age="title">j_name</td>
        <td width="100" age="title">s_value</td>
        <td width="100" age="title">s_state</td>
    </tr>
    <%
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM information;";  //查询语句
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        out.print("查询结果：");
        out.print("<br/>");
        while (rs.next()) {%>
    <tr>
        <td width="100" ><%=rs.getString("s_id") %></td>
        <td width="100" ><%=rs.getString("s_name") %></td>
        <td width="100" ><%=rs.getString("j_name") %></td>
        <td width="100" ><%=rs.getString("s_value") %></td>
        <td width="100" ><%=rs.getString("s_state") %></td>
    </tr>
    <%
                }
            }else{
                out.print("连接失败！");
            }
        }catch (Exception e) {
            //e.printStackTrace();
            out.print("数据库连接异常！");
        }
    %>
</table>
</body>
</html>