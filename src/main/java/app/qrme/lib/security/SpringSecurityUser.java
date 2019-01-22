package app.qrme.lib.security;

import app.qrme.lib.data.entity.BaseUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SpringSecurityUser extends BaseUser implements UserDetails {
    private static final long serialVersionUID = 5639683223516504866L;
    private Collection<? extends GrantedAuthority> authorities;

    public SpringSecurityUser(BaseUser user, Collection<? extends GrantedAuthority> authorities) {
        super(user);
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
