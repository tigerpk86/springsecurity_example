package config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

    // 디폴트 서블릿 핸들러를 설정할 수 있는 설정
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // 스프링 4에서는 지원 안함
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.jsp().prefix("/WEB-INF/view/").suffix(".jsp");
//    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver result = new InternalResourceViewResolver();
        result.setPrefix("/WEB-INF/view/");
        result.setSuffix(".jsp");
        return result;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource msgSrc =
                new ResourceBundleMessageSource();
        msgSrc.setBasenames("message.label");
        msgSrc.setDefaultEncoding("UTF-8");
        return msgSrc;
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("md");
    }



}
