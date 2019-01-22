package app.qrme.lib.controller;

import app.qrme.lib.data.config.LoginCfg;
import app.qrme.lib.data.dto.UserRegisterDTO;
import app.qrme.lib.data.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.MappedSuperclass;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@MappedSuperclass
public abstract class AbstractAuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/loginWithCfg")
    public ResponseEntity loginWithCfg(HttpServletRequest request, @Valid @RequestBody LoginCfg loginCfg) {
        return authService.doAuthorization(request, loginCfg);
    }

    @GetMapping(value = "/self")
    public ResponseEntity getAuthUser() {
        return authService.getCurrentUser();
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<?> logoutPage() {
        return authService.logout();
    }

    @PostMapping(value = "/register")
    public ResponseEntity registerUser(@Valid @RequestBody UserRegisterDTO regData) {
        return authService.registerUser(regData);
    }

}