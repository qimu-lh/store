<%--
  Created by IntelliJ IDEA.
  User: ����У
  Date: 2020/5/5
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html"%>
<%@page pageEncoding="GB2312"%>
<%@page import="java.sql.*" %>  <%--����java.sql��--%>
<html>
<head>
    <!-- ָ���ַ��� -->
    <meta charset="utf-8">
    <!-- ʹ��Edge���µ����������Ⱦ��ʽ -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport�ӿڣ���ҳ���Ը������õĿ���Զ��������䣬����������ڲ�����һ�������������Ŀ�����豸�Ŀ����ͬ��
    width: Ĭ�Ͽ�����豸�Ŀ����ͬ
    initial-scale: ��ʼ�����űȣ�Ϊ1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- ����3��meta��ǩ*����*������ǰ�棬�κ��������ݶ�*����*������� -->
    <title>��ѯ����</title>

    <!-- 1. ����CSS��ȫ����ʽ -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery���룬����ʹ��1.9���ϵİ汾 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. ����bootstrap��js�ļ� -->
    <script src="js/bootstrap.min.js"></script>
    <style>
        .p1{
            margin: 0 auto;
            width: fit-content;
            height: 20px;
            font-size: 18px;
            color: darkblue;
        }
    </style>

<%--    <script>--%>
<%--        $(function () {--%>
<%--            $.get("send",function (data) {--%>
<%--                alert(data);--%>
<%--            });--%>
<%--        });--%>
<%--    </script>--%>


</head>
<body>

<input type="button" id="btn" value="��ȡ����">
<table>
    <tbody id="data-list">
        <tr>
            <td>aa</td>
            <td>bb</td>
            <td>vv</td>
        </tr>
    </tbody>

</table>
<script src="${pageContext.request.contextPath}/send"></script>

<script>
    $(function () {
        $("#btn").click(function () {

            $.post("/send",{
                    name:"�̸�",
                    url:"http://www.cheng1996.cn"
                },
                function(data){
                    alert("����: \n" + data);
                    var jsonArray = JSON.parse(data);
                    console.log(jsonArray);
                    for(var i=0;i<jsonArray.length;i++)
                    {
                        console.log(jsonArray[0].name);
                        console.log(jsonArray[0].url);
                    }
        })
    })
</script>
<%--<div class="p1" >--%>
<%--    <h2 style="color: #761c19">��ǰ�豸��״̬����Ϣ</h2>--%>
<%--    <div class="p2">--%>
<%--        <%--%>
<%--            try {--%>
<%--                Class.forName("com.mysql.cj.jdbc.Driver");  ////����������--%>
<%--                String url = "jdbc:mysql://localhost:3306/day17?serverTimezone=GMT%2B8&useSSL=false"; //���ݿ���--%>
<%--                String username = "root";  //���ݿ��û���--%>
<%--                String password = "123456";  //���ݿ��û�����--%>
<%--                Connection conn = DriverManager.getConnection(url, username, password);  //����״̬--%>
<%--                if(conn != null){--%>
<%--                    out.print("<br />");--%>
<%--        %>--%>
<%--        <%--%>
<%--            Statement stmt = null;--%>
<%--            ResultSet rs = null;--%>
<%--            String userName=request.getParameter("userName");--%>
<%--            String sql = "SELECT s_value FROM information where s_name='"+userName+"' and j_name='�յ��¶�';";--%>
<%--            stmt = conn.createStatement();--%>
<%--            rs = stmt.executeQuery(sql);--%>
<%--            out.print("�յ���ǰ�¶ȣ�");--%>
<%--            /*out.print("<br/>");*/--%>
<%--            while (rs.next()) {%>--%>
<%--        <tr>--%>
<%--            <td width="30" ><%=rs.getString("s_value") %></td>--%>
<%--        </tr>--%>
<%--        <%--%>
<%--                    }--%>
<%--                }else{--%>
<%--                    out.print("����ʧ�ܣ�");--%>
<%--                }--%>
<%--            }catch (Exception e) {--%>
<%--                //e.printStackTrace();--%>
<%--                out.print("���ݿ������쳣��");--%>
<%--            }--%>
<%--        %>--%>
<%--    </div articlec>--%>
<%--    <div class="p2">--%>
<%--        <%--%>
<%--            try {--%>
<%--                Class.forName("com.mysql.cj.jdbc.Driver");  ////����������--%>
<%--                String url = "jdbc:mysql://localhost:3306/day17?serverTimezone=GMT%2B8&useSSL=false"; //���ݿ���--%>
<%--                String username = "root";  //���ݿ��û���--%>
<%--                String password = "123456";  //���ݿ��û�����--%>
<%--                Connection conn = DriverManager.getConnection(url, username, password);  //����״̬--%>

<%--                if(conn != null){--%>
<%--                    out.print(" ");--%>
<%--        %>--%>
<%--        <%--%>
<%--            Statement stmt = null;--%>
<%--            ResultSet rs = null;--%>
<%--            String userName=request.getParameter("userName");--%>
<%--            String sql = "SELECT s_state FROM information where s_name='"+userName+"' and j_name='ģʽ';";--%>
<%--            stmt = conn.createStatement();--%>
<%--            rs = stmt.executeQuery(sql);--%>
<%--            out.print("�յ�ģʽΪ��");--%>
<%--            /*out.print("<br/>");*/--%>
<%--            while (rs.next()) {%>--%>
<%--        <tr>--%>
<%--            <td width="30" ><%=rs.getString("s_state") %></td>--%>
<%--        </tr>--%>
<%--        <%--%>
<%--                    }--%>
<%--                }else{--%>
<%--                    out.print("����ʧ�ܣ�");--%>
<%--                }--%>
<%--            }catch (Exception e) {--%>
<%--                //e.printStackTrace();--%>
<%--                out.print("���ݿ������쳣��");--%>
<%--            }--%>
<%--        %>--%>
<%--    </div>--%>
<%--    <div class="p2">--%>
<%--        <%--%>
<%--            try {--%>
<%--                Class.forName("com.mysql.cj.jdbc.Driver");  ////����������--%>
<%--                String url = "jdbc:mysql://localhost:3306/day17?serverTimezone=GMT%2B8&useSSL=false"; //���ݿ���--%>
<%--                String username = "root";  //���ݿ��û���--%>
<%--                String password = "123456";  //���ݿ��û�����--%>
<%--                Connection conn = DriverManager.getConnection(url, username, password);  //����״̬--%>
<%--                if(conn != null){--%>
<%--                    out.print(" ");--%>
<%--        %>--%>
<%--        <%--%>
<%--            Statement stmt = null;--%>
<%--            ResultSet rs = null;--%>
<%--            String userName=request.getParameter("userName");--%>
<%--            String sql = "SELECT s_state FROM information where s_name='"+userName+"' and j_name='����';";--%>
<%--            stmt = conn.createStatement();--%>
<%--            rs = stmt.executeQuery(sql);--%>
<%--            out.print("�յ�����Ϊ��");--%>
<%--            /*out.print("<br/>");*/--%>
<%--            while (rs.next()) {%>--%>
<%--        <tr>--%>
<%--            <td width="30" ><%=rs.getString("s_state") %></td>--%>
<%--        </tr>--%>
<%--        <%--%>
<%--                    }--%>
<%--                }else{--%>
<%--                    out.print("����ʧ�ܣ�");--%>
<%--                }--%>
<%--            }catch (Exception e) {--%>
<%--                //e.printStackTrace();--%>
<%--                out.print("���ݿ������쳣��");--%>
<%--            }--%>
<%--        %>--%>
<%--    </div>--%>
<%--    <hr style=" height: 1px;border:none;border-top:2px solid darkgreen;width:300px" />--%>
<%--    <div class="p2">--%>
<%--        <%--%>
<%--            try {--%>
<%--                Class.forName("com.mysql.cj.jdbc.Driver");  ////����������--%>
<%--                String url = "jdbc:mysql://localhost:3306/day17?serverTimezone=GMT%2B8&useSSL=false"; //���ݿ���--%>
<%--                String username = "root";  //���ݿ��û���--%>
<%--                String password = "123456";  //���ݿ��û�����--%>
<%--                Connection conn = DriverManager.getConnection(url, username, password);  //����״̬--%>

<%--                if(conn != null){--%>
<%--                    out.print(" ");--%>
<%--        %>--%>
<%--        <%--%>
<%--            Statement stmt = null;--%>
<%--            ResultSet rs = null;--%>
<%--            String userName=request.getParameter("userName");--%>
<%--            String sql = "SELECT s_value FROM information where s_name='"+userName+"' and j_name='�¶�';";--%>
<%--            stmt = conn.createStatement();--%>
<%--            rs = stmt.executeQuery(sql);--%>
<%--            out.print("�¶�ָ��Ϊ��");--%>
<%--            /*out.print("<br/>");*/--%>
<%--            while (rs.next()) {%>--%>
<%--        <tr>--%>
<%--            <td width="30" ><%=rs.getString("s_value") %></td>--%>
<%--        </tr>--%>
<%--        <%--%>
<%--                    }--%>
<%--                }else{--%>
<%--                    out.print("����ʧ�ܣ�");--%>
<%--                }--%>
<%--            }catch (Exception e) {--%>
<%--                //e.printStackTrace();--%>
<%--                out.print("���ݿ������쳣��");--%>
<%--            }--%>
<%--        %>--%>
<%--    </div>--%>
<%--    <div class="p2">--%>
<%--        <%--%>
<%--            try {--%>
<%--                Class.forName("com.mysql.cj.jdbc.Driver");  ////����������--%>
<%--                String url = "jdbc:mysql://localhost:3306/day17?serverTimezone=GMT%2B8&useSSL=false"; //���ݿ���--%>
<%--                String username = "root";  //���ݿ��û���--%>
<%--                String password = "123456";  //���ݿ��û�����--%>
<%--                Connection conn = DriverManager.getConnection(url, username, password);  //����״̬--%>

<%--                if(conn != null){--%>
<%--                    out.print(" ");--%>
<%--        %>--%>
<%--        <%--%>
<%--            Statement stmt = null;--%>
<%--            ResultSet rs = null;--%>
<%--            String userName=request.getParameter("userName");--%>
<%--            String sql = "SELECT s_value FROM information where s_name='"+userName+"' and j_name='ʪ��';";--%>
<%--            stmt = conn.createStatement();--%>
<%--            rs = stmt.executeQuery(sql);--%>
<%--            out.print("ʪ��ָ��Ϊ��");--%>
<%--            /*out.print("<br/>");*/--%>
<%--            while (rs.next()) {%>--%>
<%--        <tr>--%>
<%--            <td width="30" ><%=rs.getString("s_value") %></td>--%>
<%--        </tr>--%>
<%--        <%--%>
<%--                    }--%>
<%--                }else{--%>
<%--                    out.print("����ʧ�ܣ�");--%>
<%--                }--%>
<%--            }catch (Exception e) {--%>
<%--                //e.printStackTrace();--%>
<%--                out.print("���ݿ������쳣��");--%>
<%--            }--%>
<%--        %>--%>
<%--    </div>--%>
<%--    <hr style=" height:1px;border:none;border-top:2px solid darkgreen;width:300px" />--%>
<%--    <div class="p3">--%>
<%--        <%--%>
<%--            try {--%>
<%--                Class.forName("com.mysql.cj.jdbc.Driver");  ////����������--%>
<%--                String url = "jdbc:mysql://localhost:3306/day17?serverTimezone=GMT%2B8&useSSL=false"; //���ݿ���--%>
<%--                String username = "root";  //���ݿ��û���--%>
<%--                String password = "123456";  //���ݿ��û�����--%>
<%--                Connection conn = DriverManager.getConnection(url, username, password);  //����״̬--%>

<%--                if(conn != null){--%>
<%--                    out.print(" ");--%>
<%--        %>--%>
<%--        <%--%>
<%--            Statement stmt = null;--%>
<%--            ResultSet rs = null;--%>
<%--            String userName=request.getParameter("userName");--%>
<%--            String sql = "SELECT s_state FROM information where s_name='"+userName+"' and j_name='�Ŵ���';";--%>
<%--            stmt = conn.createStatement();--%>
<%--            rs = stmt.executeQuery(sql);--%>
<%--            out.print("�Ŵ���״̬Ϊ��");--%>
<%--            /*out.print("<br/>");*/--%>
<%--            while (rs.next()) {%>--%>
<%--        <tr>--%>
<%--            <td width="30" ><%=rs.getString("s_state") %></td>--%>
<%--        </tr>--%>
<%--        <%--%>
<%--                    }--%>
<%--                }else{--%>
<%--                    out.print("����ʧ�ܣ�");--%>
<%--                }--%>
<%--            }catch (Exception e) {--%>
<%--                //e.printStackTrace();--%>
<%--                out.print("���ݿ������쳣��");--%>
<%--            }--%>
<%--        %>--%>
<%--    </div>--%>
<%--    <hr style=" height:1px;border:none;border-top:2px solid darkgreen;width:300px" />--%>
<%--    <div class="p3">--%>
<%--        <%--%>
<%--            try {--%>
<%--                Class.forName("com.mysql.cj.jdbc.Driver");  ////����������--%>
<%--                String url = "jdbc:mysql://localhost:3306/day17?serverTimezone=GMT%2B8&useSSL=false"; //���ݿ���--%>
<%--                String username = "root";  //���ݿ��û���--%>
<%--                String password = "123456";  //���ݿ��û�����--%>
<%--                Connection conn = DriverManager.getConnection(url, username, password);  //����״̬--%>

<%--                if(conn != null){--%>
<%--                    out.print(" ");--%>
<%--        %>--%>
<%--        <%--%>
<%--            Statement stmt = null;--%>
<%--            ResultSet rs = null;--%>
<%--            String userName=request.getParameter("userName");--%>
<%--            String sql = "SELECT s_state FROM information where s_name='"+userName+"' and j_name='�ƹ�1';";--%>
<%--            stmt = conn.createStatement();--%>
<%--            rs = stmt.executeQuery(sql);--%>
<%--            out.print("�ƹ�1��״̬Ϊ��");--%>
<%--            /*out.print("<br/>");*/--%>
<%--            while (rs.next()) {%>--%>
<%--        <tr>--%>
<%--            <td width="30" ><%=rs.getString("s_state") %></td>--%>
<%--        </tr>--%>
<%--        <%--%>
<%--                    }--%>
<%--                }else{--%>
<%--                    out.print("����ʧ�ܣ�");--%>
<%--                }--%>
<%--            }catch (Exception e) {--%>
<%--                //e.printStackTrace();--%>
<%--                out.print("���ݿ������쳣��");--%>
<%--            }--%>
<%--        %>--%>
<%--    </div>--%>
<%--    <div class="p3">--%>
<%--        <%--%>
<%--            try {--%>
<%--                Class.forName("com.mysql.cj.jdbc.Driver");  ////����������--%>
<%--                String url = "jdbc:mysql://localhost:3306/day17?serverTimezone=GMT%2B8&useSSL=false"; //���ݿ���--%>
<%--                String username = "root";  //���ݿ��û���--%>
<%--                String password = "123456";  //���ݿ��û�����--%>
<%--                Connection conn = DriverManager.getConnection(url, username, password);  //����״̬--%>

<%--                if(conn != null){--%>
<%--                    out.print(" ");--%>
<%--        %>--%>
<%--        <%--%>
<%--            Statement stmt = null;--%>
<%--            ResultSet rs = null;--%>
<%--            String userName=request.getParameter("userName");--%>
<%--            String sql = "SELECT s_state FROM information where s_name='"+userName+"' and j_name='�ƹ�2';";--%>
<%--            stmt = conn.createStatement();--%>
<%--            rs = stmt.executeQuery(sql);--%>
<%--            out.print("�ƹ�2��״̬Ϊ��");--%>
<%--            /*out.print("<br/>");*/--%>
<%--            while (rs.next()) {%>--%>
<%--        <tr>--%>
<%--            <td width="30" ><%=rs.getString("s_state") %></td>--%>
<%--        </tr>--%>
<%--        <%--%>
<%--                    }--%>
<%--                }else{--%>
<%--                    out.print("����ʧ�ܣ�");--%>
<%--                }--%>
<%--            }catch (Exception e) {--%>
<%--                //e.printStackTrace();--%>
<%--                out.print("���ݿ������쳣��");--%>
<%--            }--%>
<%--        %>--%>
<%--    </div>--%>
<%--    <div class="p3">--%>
<%--        <%--%>
<%--            try {--%>
<%--                Class.forName("com.mysql.cj.jdbc.Driver");  ////����������--%>
<%--                String url = "jdbc:mysql://localhost:3306/day17?serverTimezone=GMT%2B8&useSSL=false"; //���ݿ���--%>
<%--                String username = "root";  //���ݿ��û���--%>
<%--                String password = "123456";  //���ݿ��û�����--%>
<%--                Connection conn = DriverManager.getConnection(url, username, password);  //����״̬--%>

<%--                if(conn != null){--%>
<%--                    out.print(" ");--%>
<%--        %>--%>
<%--        <%--%>
<%--            Statement stmt = null;--%>
<%--            ResultSet rs = null;--%>
<%--            String userName=request.getParameter("userName");--%>
<%--            String sql = "SELECT s_value FROM information where s_name='"+userName+"' and j_name='�ƹ�3';";--%>
<%--            stmt = conn.createStatement();--%>
<%--            rs = stmt.executeQuery(sql);--%>
<%--            out.print("�ƹ�3��");--%>
<%--            /*out.print("<br/>");*/--%>
<%--            while (rs.next()) {%>--%>
<%--        <tr>--%>
<%--            <td width="30" ><%=rs.getString("s_value") %></td>--%>
<%--        </tr>--%>
<%--        <%--%>
<%--                    }--%>
<%--                }else{--%>
<%--                    out.print("����ʧ�ܣ�");--%>
<%--                }--%>
<%--            }catch (Exception e) {--%>
<%--                //e.printStackTrace();--%>
<%--                out.print("���ݿ������쳣��");--%>
<%--            }--%>
<%--        %>--%>
<%--    </div>--%>
<%--    <div class="p3">--%>
<%--        <%--%>
<%--            try {--%>
<%--                Class.forName("com.mysql.cj.jdbc.Driver");  ////����������--%>
<%--                String url = "jdbc:mysql://localhost:3306/day17?serverTimezone=GMT%2B8&useSSL=false"; //���ݿ���--%>
<%--                String username = "root";  //���ݿ��û���--%>
<%--                String password = "123456";  //���ݿ��û�����--%>
<%--                Connection conn = DriverManager.getConnection(url, username, password);  //����״̬--%>

<%--                if(conn != null){--%>
<%--                    out.print(" ");--%>
<%--        %>--%>
<%--        <%--%>
<%--            Statement stmt = null;--%>
<%--            ResultSet rs = null;--%>
<%--            String userName=request.getParameter("userName");--%>
<%--            String sql = "SELECT s_value FROM information where s_name='"+userName+"' and j_name='�ƹ�4';";--%>
<%--            stmt = conn.createStatement();--%>
<%--            rs = stmt.executeQuery(sql);--%>
<%--            out.print("�ƹ�4��");--%>
<%--            /*out.print("<br/>");*/--%>
<%--            while (rs.next()) {%>--%>
<%--        <tr>--%>
<%--            <td width="30" ><%=rs.getString("s_value") %></td>--%>
<%--        </tr>--%>
<%--        <%--%>
<%--                    }--%>
<%--                }else{--%>
<%--                    out.print("����ʧ�ܣ�");--%>
<%--                }--%>
<%--            }catch (Exception e) {--%>
<%--                //e.printStackTrace();--%>
<%--                out.print("���ݿ������쳣��");--%>
<%--            }--%>
<%--        %>--%>
<%--    </div><br/>--%>
<%--    <button onclick="window.location.href='interface.jsp';">--%>
<%--        &lt;%&ndash;<jsp:forward page="interface.jsp"></jsp:forward>&ndash;%&gt;--%>
<%--        <a style="color: #761c19">--%>
<%--            ��ѯʵʱ״̬��Ϣ--%>
<%--        </a>--%>
<%--    </button>--%>
<%--</div>--%>
</body>
</html>