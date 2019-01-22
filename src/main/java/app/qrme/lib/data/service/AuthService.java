package app.qrme.lib.data.service;


import app.qrme.lib.data.config.LoginCfg;
import app.qrme.lib.data.dto.*;
import app.qrme.lib.data.entity.BaseUser;
import app.qrme.lib.data.entity.Role;
import app.qrme.lib.data.mapper.BaseUserMapper;
import app.qrme.lib.data.repo.BaseUserRepository;
import app.qrme.lib.data.repo.RoleRepository;
import app.qrme.lib.security.JwtTokenProvider;
import app.qrme.lib.security.SpringSecurityUser;
import app.qrme.lib.security.UserDetailsServiceImpl;
import app.qrme.lib.utils.AuthUtil;
import app.qrme.lib.utils.BaseConstants;
import app.qrme.lib.utils.CoreLibUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

import static app.qrme.lib.utils.BaseConstants.Common.QRME;
import static app.qrme.lib.utils.BaseConstants.ErrorObj.*;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BaseUserRepository baseUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BaseUserMapper baseUserMapper;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private Google2FAService google2FAService;

    /**
     * მომხმარებლის რეგისტრაცია
     *
     * @param regData
     * @return
     */
    @Validated
    public ResponseEntity<ResponseDTO> registerUser(@Valid UserRegisterDTO regData) {
        Optional<BaseUser> baseUser = baseUserRepository.findByUsername(regData.getUsername());
        // Check if current user exists
        if (baseUser.isPresent()) {
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .errorObj(BaseConstants.ErrorObj.USERNAME_ALREADY_EXISTS)
                    .build());
        }

        BaseUser user = new BaseUser();
        user.setFirstName(regData.getFirstName());
        user.setLastName(regData.getLastName());
        user.setUsername(regData.getUsername());

        if (regData.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(regData.getPassword()));
        } else if (regData.getFacebookUrl() != null
                || regData.getGoogleUrl() != null
                || regData.getTwitterUrl() != null) {
            user.setPassword(passwordEncoder.encode(CoreLibUtils.generateString(8)));
        }
        user.setActive(true);

        Role userRole = roleRepository.findByName(BaseConstants.Common.basic_user_role);
        if (userRole != null) {
            user.getRole().add(userRole);
        }

        // Google Ownabit
        String secretKey = google2FAService.createKeyFor(QRME);
        user.setGoogleSecretKey(secretKey);

        BaseUser currentUser = baseUserRepository.save(user);
        return ResponseEntity.ok(ResponseDTO.builder().content(baseUserMapper.map(currentUser)).build());
    }

    /**
     * SpringSecurityContext-დან ვიღებ ავტორიზირებულ მომხმარებელს
     *
     * @return
     */

    public ResponseEntity getCurrentUser() {
        Object user = AuthUtil.currentUser();
        if (user != null) {
            if(user instanceof SpringSecurityUser){
                return ResponseEntity.ok(ResponseDTO.builder().content(
                        CurrentUserDTO.builder().type("USER").userDetail(user).build()
                ).build());
            }
            return ResponseEntity.ok(ResponseDTO.builder().content(user).build());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseDTO.builder().errorObj(BaseConstants.ErrorObj.UNAUTHORIZED).build());
    }

    /**
     * სოციალური ქსელით დაბრუნებული მონაცემით ავტორიზაციის გავლა
     *
     * @param request
     * @param loginCfg
     * @return
     */
    public ResponseEntity loginWithSocial(HttpServletRequest request, LoginCfg loginCfg) {
        UserDetails userDetails = userDetailsService.loadUserBySocialNetworkUrl(loginCfg);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SpringSecurityUser springSecurityUser = (SpringSecurityUser) userDetails;
        if (springSecurityUser.isHavingGAOtp() && springSecurityUser.getGoogleSecretKey() != null) {
            if (loginCfg.getCredentials().getAuth2FACode() == null) {
                return ResponseEntity.ok().body(ResponseDTO.singleEntry(BaseConstants.SingleEntryType.TWO_FACTOR_AUTH, GOOGLE_2FA_REQUIRED));
            }
            boolean validCode = loginCfg.getCredentials().getAuth2FACode().toString().length() == 6;
            if (!google2FAService.isCodeValid(springSecurityUser.getGoogleSecretKey(), loginCfg.getCredentials().getAuth2FACode()) && validCode) {
                return ResponseEntity.badRequest().body(ResponseDTO.builder().errorObj(INVALID_2FA_CODE).build());
            }
        }
        if (springSecurityUser.isHavingOtp() && !springSecurityUser.isHavingGAOtp()) {
            if (loginCfg.getCredentials().getAuth2FACode() == null) {
                return ResponseEntity.ok().body(ResponseDTO.singleEntry(BaseConstants.SingleEntryType.TWO_FACTOR_AUTH, OTP_2FA_REQUIRED));
            }
        }
        UserDTO securityDTO = baseUserMapper.map(springSecurityUser);
        return this.login(authentication, securityDTO);
    }

    /**
     * Username/Password რეგისტრაცია
     *
     * @param userAuthDTO
     * @return
     */
    public ResponseEntity doBasicLogin(UserAuthDTO userAuthDTO) {
        Optional<BaseUser> user = baseUserRepository.findByUsername(userAuthDTO.getUsername());
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body(ResponseDTO.builder().errorObj(INVALID_USERNAME_OR_PASSWORD).build());
        }

        // Checking Current Password
        if (!BCrypt.checkpw(userAuthDTO.getPassword(), user.get().getPassword())) {
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .errorObj(INVALID_USERNAME_OR_PASSWORD)
                    .build());
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userAuthDTO.getUsername(),
                        userAuthDTO.getPassword()
                )
        );
        SpringSecurityUser springSecurityUser = (SpringSecurityUser) authentication.getPrincipal();
        if (springSecurityUser.isHavingGAOtp() && springSecurityUser.getGoogleSecretKey() != null) {
            if (userAuthDTO.getAuth2FACode() == null) {
                return ResponseEntity.ok().body(ResponseDTO.singleEntry(BaseConstants.SingleEntryType.TWO_FACTOR_AUTH, GOOGLE_2FA_REQUIRED));
            }
            boolean validCode = userAuthDTO.getAuth2FACode().toString().length() == 6;
            if (!google2FAService.isCodeValid(springSecurityUser.getGoogleSecretKey(), userAuthDTO.getAuth2FACode()) && validCode) {
                return ResponseEntity.badRequest().body(ResponseDTO.builder().errorObj(INVALID_2FA_CODE).build());
            }
        }
        if (springSecurityUser.isHavingOtp() && !springSecurityUser.isHavingGAOtp()) {
            if (userAuthDTO.getAuth2FACode() == null) {
                return ResponseEntity.ok().body(ResponseDTO.singleEntry(BaseConstants.SingleEntryType.TWO_FACTOR_AUTH, OTP_2FA_REQUIRED));
            }
        }
        UserDTO securityDTO = baseUserMapper.map(springSecurityUser);
        return login(authentication, securityDTO);
    }


    /**
     * მთავარი ლოგიკა SpringSecurityContext-ში მომხარებლის ჩასატვირთად
     *
     * @param authentication
     * @return
     */
    private ResponseEntity login(Authentication authentication, UserDTO springSecurityDTO) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String AUTH_TOKEN = tokenProvider.generateExpirableToken(authentication);
        return ResponseEntity.ok(ResponseDTO.builder()
                .content(UserSuccessDTO.builder()
                        .token(AUTH_TOKEN)
                        .user(springSecurityDTO)
                        .build()
                )
                .build());
    }

    /**
     * SpringSecurityContext-ში სესიის დახურვა
     *
     * @return
     */
    public ResponseEntity logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
            SecurityContextHolder.clearContext();
        }
        return ResponseEntity.ok(ResponseDTO.builder().success(true).build());
    }

    /**
     * ავტორიზაციის გავლა
     *
     * @param request
     * @param loginCfg
     * @return
     */
    public ResponseEntity doAuthorization(HttpServletRequest request, LoginCfg loginCfg) {
        switch (loginCfg.getType()) {
            case TWITTER:
            case FACEBOOK:
            case GOOGLE: {
                return this.loginWithSocial(request, loginCfg);
            }
            case SIMPLE:
            default: {
                return this.doBasicLogin(loginCfg.getCredentials());
            }
        }
    }

}
