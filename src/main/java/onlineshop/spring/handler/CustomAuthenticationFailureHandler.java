package onlineshop.spring.handler;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger logger = Logger.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException {

        String errorMessage;
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (isNullOrEmpty(email) || isNullOrEmpty(password)) {
            errorMessage = "The form is not fully completed!";
            request.getSession().setAttribute("lastEnteredEmail", email);
            request.getSession().setAttribute("lastEnteredPassword", password);
        } else if (exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
            errorMessage = "Wrong email or password! Check them and try again.";
            request.getSession().setAttribute("lastEnteredEmail", email);
            request.getSession().setAttribute("lastEnteredPassword", password);
        } else {
            errorMessage = "Unknown error - " + exception.getMessage();
            request.getSession().setAttribute("lastEnteredEmail", email);
            request.getSession().setAttribute("lastEnteredPassword", password);
        }

        if (!isNullOrEmpty(email)) {
            logger.error("Problem with authorization of user by email: " + email, exception);
        }
        request.getSession().setAttribute("message", errorMessage);
        response.sendRedirect("/spring.mvc.onlineshop/login");
    }

    private boolean isNullOrEmpty(String requestParameter) {
        return Objects.isNull(requestParameter) || requestParameter.trim().isEmpty();
    }
}
