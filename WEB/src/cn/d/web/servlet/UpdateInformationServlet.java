//package cn.d.web.servlet;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Controller
//@RequestMapping("/updateInformationServlet")
//public class UpdateInformationServlet{
//
//    @RequestMapping("/a1")
//
//    public void ajax(String name, HttpServletResponse response) throws IOException {
//        if("admin".equals(name)){
//            response.getWriter().print("true");
//        }else{
//            response.getWriter().print("false");
//        }
//    }
//}


package cn.d.web.servlet;

import cn.d.dao.impl.InformationImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateInformationServlet")
public class UpdateInformationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String userName = request.getParameter("userName");
        String temp = request.getParameter("temp");
        String vent = request.getParameter("vent");
        String deng1 = request.getParameter("deng1");
        String deng2 = request.getParameter("deng2");
        String deng3 = request.getParameter("deng3");
        String deng4 = request.getParameter("deng4");
        String moshi = request.getParameter("moshi");
        String fengsu = request.getParameter("fengsu");
        String wendu = request.getParameter("wendu");
        String shidu = request.getParameter("shidu");
//        System.out.println(userName);
//        System.out.println(vent);
//        System.out.println(temp);

        new InformationImpl().update(userName,temp);
        new InformationImpl().vent(vent,userName);
        new InformationImpl().deng1(deng1,userName);
        new InformationImpl().deng2(deng2,userName);
        new InformationImpl().deng3(deng3,userName);
        new InformationImpl().deng4(deng4,userName);
        new InformationImpl().moshi(moshi,userName);
        new InformationImpl().fengsu(fengsu,userName);
        new InformationImpl().wendu(wendu,userName);
        new InformationImpl().shidu(shidu,userName);

        int i = Integer.parseInt(deng3);
        int j = Integer.parseInt(deng4);



        if (0<=i&&i<=100&&0<=j&&j<=100)
        {
            response.sendRedirect(request.getContextPath()+"/query.jsp?userName="+userName);
        }
        else{
            response.sendRedirect(request.getContextPath()+"/mm.jsp");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
