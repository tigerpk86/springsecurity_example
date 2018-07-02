package security;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomCorsInterceptor extends HandlerInterceptorAdapter {

    public static final String CREDENTIALS_NAME = "Access-Control-Allow-Credentials";
    public static final String ORIGIN_NAME = "Access-Control-Allow-Origin";
    public static final String METHODS_NAME = "Access-Control-Allow-Methods";
    public static final String HEADERS_NAME = "Access-Control-Allow-Headers";
    public static final String MAX_AGE_NAME = "Access-Control-Max-Age";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        response.setHeader(CREDENTIALS_NAME, "true");
        response.setHeader(ORIGIN_NAME, request.getHeader("Origin"));
        response.setHeader(METHODS_NAME, "GET, OPTIONS, POST, PUT, DELETE");
//        response.setHeader(HEADERS_NAME, "Origin, X-Requested-With, Content-Type, Accept");
//        response.setHeader(MAX_AGE_NAME, "3600");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        response.setHeader(ORIGIN_NAME, "");
        response.setHeader(ORIGIN_NAME, null);
//        response.setHeader(CREDENTIALS_NAME, "");
//        response.setHeader(ORIGIN_NAME, "");
//        response.setHeader(METHODS_NAME, "");
//        response.setHeader(HEADERS_NAME, "");
//        response.setHeader(MAX_AGE_NAME, "");
    }
}
