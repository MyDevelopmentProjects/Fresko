package app.qrme.lib.security;

import app.qrme.lib.data.dto.ResponseDTO;
import app.qrme.lib.utils.BaseConstants;
import app.qrme.lib.utils.CoreLibUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static app.qrme.lib.utils.BaseConstants.Common.authorization;
import static app.qrme.lib.utils.BaseConstants.Common.bearer;


@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String jwt = getJwtFromRequest(request);
        if (StringUtils.hasText(jwt)) {
            BaseConstants.JwtTokenResult result = tokenProvider.validateToken(jwt);
            switch (result) {
                case VALID_TOKEN: {
                    String tokenType = tokenProvider.getAuthType(jwt);
                    if(tokenType != null){
                        Long userId = tokenProvider.getIdFromToken(jwt);
                        switch (tokenType){
                            case "USER":{
                                UserDetails userDetails = userDetailsService.loadUserById(userId);
                                TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                SecurityContextHolder.getContext().setAuthentication(authentication);
                                filterChain.doFilter(request, response);
                                break;
                            }
                        }
                    }
                    break;
                }
                case EXC_EXPIRED:
                    //pass down the actual obj that exception handler normally send
                    CoreLibUtils.responseWithBody(response, HttpStatus.UNAUTHORIZED, ResponseDTO.builder()
                            .errorObj(BaseConstants.ErrorObj.SESSION_EXPIRED)
                            .build());
                    break;
                default: {
                    CoreLibUtils.responseWithBody(response, HttpStatus.UNAUTHORIZED, ResponseDTO.builder()
                            .errorObj(BaseConstants.ErrorObj.INVALID_TOKEN)
                            .build());
                    break;
                }
            }
        } else {
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(authorization);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(bearer)) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }


}
