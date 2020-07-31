package cn.d.web.filter;
/*
    登录验证的过滤器，登陆拦截
*/
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        System.out.println("fiter");
        String uri = request.getRequestURI();
        if (uri.contains("/login.jsp") || uri.contains("/loginServlet")
            || uri.contains("/css/") || uri.contains("/js/") || uri.contains("/checkCode")
        ){
            chain.doFilter(req,resp);
        }else{
            Object user = request.getSession().getAttribute("user");
            if (user!=null){
                chain.doFilter(req,resp);
            }else{
                request.setAttribute("login_msg","您尚未登录，请先登录");
                request.getRequestDispatcher("/login.jsp").forward(req,resp);
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
