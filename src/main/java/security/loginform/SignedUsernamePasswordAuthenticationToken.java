package security.loginform;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SignedUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String requestSignature;

    public SignedUsernamePasswordAuthenticationToken(String principal, String credentials, String signature) {
        super(principal, credentials);
        this.requestSignature = signature;
    }

    public String getRequestSignature() {
        return requestSignature;
    }

    public void setRequestSignature(String requestSignature) {
        this.requestSignature = requestSignature;
    }
}

