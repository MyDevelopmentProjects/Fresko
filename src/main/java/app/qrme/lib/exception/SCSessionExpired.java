package app.qrme.lib.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class SCSessionExpired extends SCException {

    private String ipAddress;
    private String token;

    public SCSessionExpired(String ipAddress, String token) {
        super("Session Expired");
        this.ipAddress = ipAddress;
        this.token = token;
    }
}