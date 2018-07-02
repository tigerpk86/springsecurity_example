package security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class IpTokenBasedRememberMeServices extends TokenBasedRememberMeServices {

    // HttpServletRequest를 ThreadLocal에 임시 저장
    // 일부 메서드가 매개변수로 HttpServletRequest를 사용하지 않음
    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    public IpTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }

    public HttpServletRequest getContext() {
        return requestHolder.get();
    }

    public void setContext(HttpServletRequest httpServletRequest) {
        requestHolder.set(httpServletRequest);
    }

    protected String getUserIpAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    @Override
    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {


        try {
            setContext(request);
            super.onLoginSuccess(request, response, successfulAuthentication);
        } finally {
            setContext(null);
        }
    }

    @Override
    protected String makeTokenSignature(long tokenExpiryTime, String username, String password) {

        String data = username + ":" + tokenExpiryTime + ":" + password + ":" + getKey() + ":" + getUserIpAddress(getContext());
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }

        return new String(Hex.encode(digest.digest(data.getBytes())));

//        return DigestUtils.md5DigestAsHex((username + ":" + tokenExpiryTime
//                                            + ":" + password +":" + getKey() + ":" + getUserIpAddress(getContext())).getBytes());
    }


    @Override
    protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response) {
        String[] tokensWithIpaddress = Arrays.copyOf(tokens, tokens.length+1);
        tokensWithIpaddress[tokensWithIpaddress.length-1] = getUserIpAddress(request);
        super.setCookie(tokensWithIpaddress, maxAge, request, response);
    }

    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) {
//        return super.processAutoLoginCookie(cookieTokens, request, response);

        try {
            setContext(request);
            String ipAddressToken = cookieTokens[cookieTokens.length-1];
            if (!getUserIpAddress(request).equals(ipAddressToken)) {
                throw new InvalidCookieException("Cookie Ip Address did not contain a matching");
            }

            return super.processAutoLoginCookie(Arrays.copyOf(cookieTokens, cookieTokens.length-1), request, response);
        } finally {
            setContext(null);
        }




    }
}
