<%@ page contentType="text/html"%>
<%@page pageEncoding="GB2312"%>
<%@page import="java.sql.*" %>  <%--����java.sql��--%>
<html>
<head>
    <title >��MySQL���ݿ��ж���student��</title>
</head>
<body>
<%
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");  ////����������
        String url = "jdbc:mysql://localhost:3306/day17?serverTimezone=GMT%2B8&useSSL=false"; //���ݿ���
        String username = "root";  //���ݿ��û���
        String password = "123456";  //���ݿ��û�����
        Connection conn = DriverManager.getConnection(url, username, password);  //����״̬

        if(conn != null){
            out.print("���ݿ����ӳɹ���");
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
        String sql = "SELECT * FROM information;";  //��ѯ���
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        out.print("��ѯ�����");
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
                out.print("����ʧ�ܣ�");
            }
        }catch (Exception e) {
            //e.printStackTrace();
            out.print("���ݿ������쳣��");
        }
    %>
</table>
</body>
</html>