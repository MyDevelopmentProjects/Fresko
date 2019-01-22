package app.qrme.lib.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

    private static Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Object currentUser() {
        if (isAuthenticated()) {
            return getAuth().getPrincipal();
        }
        return null;
    }

    public static boolean isAuthenticated() {
        return (getAuth() != null && getAuth().isAuthenticated() && !(getAuth() instanceof AnonymousAuthenticationToken));
    }

}
