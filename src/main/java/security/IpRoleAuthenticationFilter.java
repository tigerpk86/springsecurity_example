package security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

public class IpRoleAuthenticationFilter extends OncePerRequestFilter{

    private String targetRole;
    private List<String> allowedIpAddress;

    public String getTargetRole() {
        return targetRole;
    }

    public void setTargetRole(String targetRole) {
        this.targetRole = targetRole;
    }

    public List<String> getAllowedIpAddress() {
        return allowedIpAddress;
    }

    public void setAllowedIpAddress(List<String> allowedIpAddress) {
        this.allowedIpAddress = allowedIpAddress;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && targetRole != null) {
            boolean shouldCheck = false;

            for(GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals(targetRole)) {
                    shouldCheck = true;
                    break;
                }
            }

            if(shouldCheck && allowedIpAddress.size() > 0) {
                 boolean shouldAllow = false;
                for(String ipAddres : allowedIpAddress) {
                    if(request.getRemoteAddr().equals(ipAddres)) {
                        shouldAllow = true;
                        break;
                    }
                }
                if(!shouldAllow) {
                    throw new AccessDeniedException("Access Denied Ip : " + request.getRemoteUser());
                }
            }

        } else {
            logger.warn("The IPRoleAuthenticationFilter should be placed after the user has been authenticated in the filter chain.");
        }

        filterChain.doFilter(request, response);
    }
}
