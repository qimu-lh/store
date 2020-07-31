package cn.d.web.servlet;

import cn.d.dao.impl.InformationImpl;
import cn.d.domain.Information;
import cn.d.util.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/send")
public class Send extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        String userName1 = request.getParameter("userName");

        System.out.println(userName1+"||");
        List<Information> list = new InformationImpl().chuancan(userName1);

//        request.getRequestDispatcher("/query.jsp").forward(request,response);
//        for (Information information : list) {
//            //System.out.println(information.getS_state());
//
//
//        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}