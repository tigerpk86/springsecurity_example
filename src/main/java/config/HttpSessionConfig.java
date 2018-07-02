package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.web.bind.support.DefaultSessionAttributeStore;
import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import session.CustomSessionAttributeStore;
import session.HashMapSessionRepository;

import javax.servlet.ServletContext;

@Configuration
public class HttpSessionConfig extends WebMvcConfigurationSupport {

    private Integer maxInactiveIntervalInSeconds = 1800;

    @Bean
    public HashMapSessionRepository mapSessionRepository() {
        HashMapSessionRepository sessionRepository = new HashMapSessionRepository();
        sessionRepository.setDefaultMaxInactiveInterval(maxInactiveIntervalInSeconds);
        return sessionRepository;
    }


    @Bean
    public <S extends ExpiringSession> SessionRepositoryFilter<? extends ExpiringSession>
    springSessionRepositoryFilter(SessionRepository sessionRepository, ServletContext servletContext) {
        SessionRepositoryFilter<S> sessionRepositoryFilter = new SessionRepositoryFilter<S>(sessionRepository);
        sessionRepositoryFilter.setServletContext(servletContext);
        return sessionRepositoryFilter;
    }

    @Bean
    public SessionAttributeStore sessionAttributeStore(){
        return  new DefaultSessionAttributeStore();
    }



    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter handlerAdapter = super.requestMappingHandlerAdapter();
        handlerAdapter.setSessionAttributeStore(sessionAttributeStore());
        return handlerAdapter;
    }



}
