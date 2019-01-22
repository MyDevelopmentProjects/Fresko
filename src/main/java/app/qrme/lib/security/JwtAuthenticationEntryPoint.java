package app.qrme.lib.security;

import app.qrme.lib.data.dto.ResponseDTO;
import app.qrme.lib.utils.BaseConstants;
import app.qrme.lib.utils.CoreLibUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {

        CoreLibUtils.responseWithBody(httpServletResponse, HttpStatus.UNAUTHORIZED,  ResponseDTO.builder()
                .errorObj(BaseConstants.ErrorObj.UNAUTHORIZED)
                .build());
    }
}
