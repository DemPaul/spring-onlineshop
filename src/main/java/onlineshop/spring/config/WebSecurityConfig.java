package onlineshop.spring.config;

import onlineshop.spring.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/login**", "/register**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/signin")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler((req, res, auth) -> {
                    res.sendRedirect("/spring.mvc.onlineshop/");
                })
                .failureHandler((req, res, exp) -> {
                    String errorMessage;
                    if (exp.getClass().isAssignableFrom(NullPointerException.class)) {
                        errorMessage = "The form is not fully completed!";
                    } else if (exp.getClass().isAssignableFrom(BadCredentialsException.class)) {
                        errorMessage = "Wrong email or password! Check them and try again.";
                    } else {
                        errorMessage = "Unknown error - " + exp.getMessage();
                    }
                    req.getSession().setAttribute("message", errorMessage);
                    res.sendRedirect("/spring.mvc.onlineshop/login");
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/signout")
                .logoutSuccessHandler((req, res, auth) -> {
                    req.getSession().setAttribute("message",
                            "You are successfully logged out!");
                    res.sendRedirect("/spring.mvc.onlineshop/login");
                })
                .permitAll()
                .and()
                .csrf().disable();
    }
}
