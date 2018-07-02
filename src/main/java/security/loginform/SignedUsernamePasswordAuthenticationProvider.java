package security.loginform;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class SignedUsernamePasswordAuthenticationProvider extends DaoAuthenticationProvider{

    @Override
    public boolean supports(Class<?> authentication) {
        return SignedUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);
        SignedUsernamePasswordAuthenticationToken signedToken = (SignedUsernamePasswordAuthenticationToken)authentication;
        if (signedToken.getRequestSignature() == null) {
          throw new BadCredentialsException("SignedUsernamePasswordAuthenticationToken.missingSignature");
        }
    
        if(!signedToken.getRequestSignature().equals(calculateExepctedSignautre(signedToken))) {
            throw new BadCredentialsException("SignedUsernamePasswordAuthenticationToken.invalidSignature");
        }

    }

    private String calculateExepctedSignautre(SignedUsernamePasswordAuthenticationToken signedToken) {
        return signedToken.getPrincipal() + "|+|" +signedToken.getCredentials();

    }
}
