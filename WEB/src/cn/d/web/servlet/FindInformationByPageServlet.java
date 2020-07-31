package cn.d.web.servlet;

import cn.d.domain.Information;
import cn.d.domain.PageBean;
import cn.d.service.UserService;
import cn.d.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@WebServlet("/findInformationByPageServlet")
public class FindInformationByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String currentPage = request.getParameter("currentPage");
        String rows = request.getParameter("rows");
        //第一页不能再往前
        if (currentPage == null || "".equals(currentPage)){
            currentPage = "1";
        }
        //最后一页不能再往后
        if (rows == null || "".equals(rows)){
            rows = "5";
        }
        //查询的条件（搜索功能）
        Map<String, String[]> condition = request.getParameterMap();

        Set<String> set = condition.keySet();
        for (String key : set) {
            System.out.println("Find"+key+set);
        }
        UserService service = new UserServiceImpl();
        PageBean<Information> pd  = service.findInformationByPage(currentPage,rows,condition);
        request.setAttribute("pd",pd);
        request.setAttribute("condition",condition);
        //转发不需要虚拟目录
        request.getRequestDispatcher("/a.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
