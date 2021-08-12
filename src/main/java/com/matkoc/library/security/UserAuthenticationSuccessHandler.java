package com.matkoc.library.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{
        boolean isInactive = false;
        boolean isLibrarian = false;
        boolean isReader = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            String authority = grantedAuthority.getAuthority();
            if (authority.equals("ROLE_INACTIVE") || authority.equals("ROLE_INACTIVE_LIB")) {
                isInactive = true;
            } else if (authority.equals("ROLE_LIBRARIAN")) {
                isLibrarian = true;
            } else if (authority.equals("ROLE_READER")){
                isReader = true;
            }
            break;
        }

        if (isInactive) {
            redirectStrategy.sendRedirect(request, response, "/login/activate-account");
        }else if (isLibrarian){
            redirectStrategy.sendRedirect(request, response, "/librarian");
        } else if (isReader){
            redirectStrategy.sendRedirect(request, response, "/reader");
        } else {
            redirectStrategy.sendRedirect(request, response, "/manager");
        }
    }
}
