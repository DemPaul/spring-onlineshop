package onlineshop.spring.handler;

import onlineshop.spring.entity.User;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = Logger.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        User user = (User) authentication.getPrincipal();
        logger.info("User with email: " + user.getEmail() +
                " successfully authorized");
        response.sendRedirect("/spring.mvc.onlineshop/");
    }
}
