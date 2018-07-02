package security.loginform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import security.loginform.SignedUsernamePasswordAuthenticationToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestHeaderProcessingFilter extends AbstractAuthenticationProcessingFilter{

    private String USERNAME_HEADER = "username";
    private String PASSWORD_HEADER = "password";
    private String SIGNATURE_HEADER = "signature";

    public RequestHeaderProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
            String username = request.getHeader(USERNAME_HEADER);
        String password = request.getHeader(PASSWORD_HEADER);
        String signature = request.getHeader(SIGNATURE_HEADER);

        SignedUsernamePasswordAuthenticationToken authRequest = new SignedUsernamePasswordAuthenticationToken(username, password, signature);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
