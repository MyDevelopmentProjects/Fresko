package app.qrme.lib.security;

import app.qrme.lib.data.config.LoginCfg;
import app.qrme.lib.data.entity.BaseUser;
import app.qrme.lib.data.repo.BaseUserRepository;
import app.qrme.lib.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BaseUserRepository baseUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BaseUser baseUser = baseUserRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("The username %s doesn't exist", username))
        );
        return getUserDetailsWithRole(baseUser);
    }

    @Transactional
    public UserDetails loadUserById(Long id){
        BaseUser baseUser = baseUserRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", id)
        );
        return getUserDetailsWithRole(baseUser);
    }

    @Transactional
    public UserDetails loadUserBySocialNetworkUrl(LoginCfg loginCfg){
        BaseUser baseUser;
        switch (loginCfg.getType()) {
            case FACEBOOK:
                baseUser = baseUserRepository.findByFacebookUrl(loginCfg.getCredentials().getFacebookUrl()).orElseThrow(
                        () -> new ResourceNotFoundException("FacebookUser", "facebookUrl", loginCfg.getCredentials().getFacebookUrl())
                );
                break;
            case GOOGLE:
                baseUser = baseUserRepository.findByGoogleUrl(loginCfg.getCredentials().getGoogleUrl()).orElseThrow(
                        () -> new ResourceNotFoundException("GoogleUser", "googleUrl", loginCfg.getCredentials().getGoogleUrl())
                );
                break;
            default:
                baseUser = baseUserRepository.findByTwitterUrl(loginCfg.getCredentials().getTwitterUrl()).orElseThrow(
                        () -> new ResourceNotFoundException("TwitterUser", "twitterUrl", loginCfg.getCredentials().getTwitterUrl())
                );
                break;
        }
        return getUserDetailsWithRole(baseUser);
    }

    /**
     * როლების ჩატვირთვა SpringSecurityUser კონტექსტში
     * @param baseUser
     * @return
     */
    private UserDetails getUserDetailsWithRole(BaseUser baseUser) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (baseUser.getRole() != null) {
            baseUser.getRole().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        }
        return new SpringSecurityUser(baseUser, authorities);
    }

}