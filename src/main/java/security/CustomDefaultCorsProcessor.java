package security;

import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.DefaultCorsProcessor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomDefaultCorsProcessor extends DefaultCorsProcessor {



    @Override
    public boolean processRequest(CorsConfiguration config, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!CorsUtils.isCorsRequest(request)) {
            return true;
        }

        ServletServerHttpResponse serverResponse = new ServletServerHttpResponse(response);
        if (responseHasCors(serverResponse)) {
//            logger.debug("Skip CORS processing: response already contains \"Access-Control-Allow-Origin\" header");
            return true;
        }

        ServletServerHttpRequest serverRequest = new ServletServerHttpRequest(request);
        if (WebUtils.isSameOrigin(serverRequest)) {
//            logger.debug("Skip CORS processing: request is from same origin");
            return true;
        }

        boolean preFlightRequest = CorsUtils.isPreFlightRequest(request);
        if (config == null) {
            if (preFlightRequest) {
                rejectRequest(serverResponse);
                return false;
            }
            else {
                return true;
            }
        }

        return handleInternal(serverRequest, serverResponse, config, preFlightRequest);
    }


    private boolean responseHasCors(ServerHttpResponse response) {
        try {
            return (response.getHeaders().getAccessControlAllowOrigin() != null);
        }
        catch (NullPointerException npe) {
            // SPR-11919 and https://issues.jboss.org/browse/WFLY-3474
            return false;
        }
    }

}
