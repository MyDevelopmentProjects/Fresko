package app.qrme.lib.data.service;

import app.qrme.lib.data.repo.BaseUserRepository;
import app.qrme.lib.security.SpringSecurityUser;
import app.qrme.lib.utils.BaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
public class UserService {

    @Autowired
    private BaseUserRepository baseUserRepository;

    @Autowired
    private OTPService otpService;

    @Autowired
    private Google2FAService google2FAService;


    /**
     * turn on or off your 2FA authentication.
     * MIDDLEWARE must also send message at the same time, after calling this method
     * MIDDLEWARE must fetch current user details, get mobile and country info and send message
     * @param tfaType param, which identifies is the type OTP or Google OTP
     * @param isEnabled pass true or false
     * @param code the code you received on mobile or you see in GA
     * @return returns success in case you setup 2FA correctly otherwise bad request
     */
    public ResponseEntity toggle2FA(@Valid @NotNull BaseConstants.TwoFactorAuthType tfaType,
                                    @Valid @NotNull Boolean isEnabled,
                                    @NotNull Integer code) {
        SpringSecurityUser user = (SpringSecurityUser) SecurityContextHolder.getContext().getAuthentication();
        if (user != null) {
            switch (tfaType) {
                case GOTP:
                    if (validateGoogle(user, code)) {
                        user.toggleGOtp(isEnabled);
                        return ResponseEntity.ok().build();
                    }
                    break;
            }
        }

        return ResponseEntity.badRequest().build();
    }

    /**
     * Google ერთჯერადი პაროლის ვალიდაცია
     * @param u
     * @param code
     * @return
     */
    private boolean validateGoogle(SpringSecurityUser u, Integer code) {
        return !StringUtils.isEmpty(u.getGoogleSecretKey()) && google2FAService.isCodeValid(u.getGoogleSecretKey(), code);
    }


    /**
     * Normal ერთჯერადი პაროლის ვალიდაცია
     * @param phoneNumber
     * @param code
     * @return
     */
    private boolean validateNormalOTP(HttpServletRequest request, String phoneNumber, Integer code){
        return  otpService.validateOTPCode(request, phoneNumber, BaseConstants.OtpType.GENERAL, code) != null;
    }

}
