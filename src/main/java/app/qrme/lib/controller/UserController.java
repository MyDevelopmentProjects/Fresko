package app.qrme.lib.controller;

import app.qrme.lib.data.entity.BaseUser;
import app.qrme.lib.data.repo.BaseUserRepository;
import app.qrme.lib.data.service.UserService;
import app.qrme.lib.utils.BaseConstants;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/base-user")
@Api(value = "Base User Controller", description = "This Controller is responsible for BaseUser.")
public class UserController extends AbstractCRUDController<BaseUser, Long> {

    @Autowired
    private UserService userService;

    public UserController(BaseUserRepository repository) {
        super(repository);
    }


    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    @Override
    public ResponseEntity save(@RequestBody BaseUser obj) {
        return super.save(obj);
    }

    /**
     * turn on or off your 2FA authentication.
     * MIDDLEWARE must also send message at the same time, after calling this method
     * MIDDLEWARE must fetch current user details, get mobile and country info and send message
     *
     * @param tfaType   param, which identifies is the type OTP or Google OTP
     * @param isEnabled pass true or false
     * @param code      the code you received on mobile or you see in GA
     * @return returns success in case you setup 2FA correctly otherwise bad request
     */
    @PostMapping("/toggle2FA")
    public ResponseEntity toggle2FA(
            @Valid @NotNull BaseConstants.TwoFactorAuthType tfaType,
            @Valid @NotNull Boolean isEnabled,
            @NotNull Integer code) {
        return userService.toggle2FA(tfaType, isEnabled, code);
    }

}