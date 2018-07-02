package config;

import interceptor.AuthCheckInterceptor;
import login.LoginLogoutController;
import member.controller.HelloController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import security.CustomCorsInterceptor;
import session.SessionController;
import user.bo.ChangPasswordBO;
import user.controller.ChangePasswordController;
import user.controller.User2Controller;
import user.controller.UserController;

@Configuration
public class ControllerConfig extends WebMvcConfigurerAdapter{
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/main").setViewName("main");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthCheckInterceptor())
                .addPathPatterns("/edit/**")
                .excludePathPatterns("/elus/**");
        registry.addInterceptor(new CustomCorsInterceptor());
    }

    @Bean
    public HelloController helloController() {
        return new HelloController();
    }

    @Bean
    public UserController userController() {
        return new UserController();
    }

    @Bean
    public SessionController sessionController() {
        return new SessionController();
    }

    @Bean
    public User2Controller user2Controller() {
        return new User2Controller();
    }

    @Bean
    public LoginLogoutController loginLogoutController(){
        return new LoginLogoutController();
    }

    @Bean
    public ChangPasswordBO changPasswordBO() {
        ChangPasswordBO changPasswordBO = new ChangPasswordBO();
        return changPasswordBO;
    }

    @Bean
    public ChangePasswordController changePasswordController() {
        return new ChangePasswordController();
    }



}
