package onlineshop.spring.filters;


import onlineshop.spring.entity.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebFilter(urlPatterns = "/admin/*")
public class AdminFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        User userFromSession = (User) request.getSession().getAttribute("user");
        if (userFromSession != null && Objects.nonNull(userFromSession.getRole())
                && userFromSession.getRole().equals("admin")) {
            chain.doFilter(req, resp);
        } else {
            response.sendRedirect("/spring.mvc.onlineshop/user/product/all");
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }
}
