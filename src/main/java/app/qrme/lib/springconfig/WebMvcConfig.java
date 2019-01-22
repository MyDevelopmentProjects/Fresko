package app.qrme.lib.springconfig;

import app.qrme.lib.annotations.SCHashRestriction;
import app.qrme.lib.annotations.SCIpRestriction;
import app.qrme.lib.data.dto.ResponseDTO;
import app.qrme.lib.utils.BaseConstants;
import app.qrme.lib.utils.CoreLibUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;

import static app.qrme.lib.utils.BaseConstants.Common.HEADER_OWHF;
import static app.qrme.lib.utils.BaseConstants.Common.HEADER_OWHT;
import static app.qrme.lib.utils.BaseConstants.ErrorObj.BAD_REQUEST;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    public class CustomIpInterceptor implements HandlerInterceptor{
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            if (handler instanceof HandlerMethod) {
                HandlerMethod method = (HandlerMethod)handler;
                SCIpRestriction ipRestriction = method.getMethodAnnotation(SCIpRestriction.class);
                if(ipRestriction != null) {
                    for(String addr: ipRestriction.addresses()){
                        if(InetAddress.getLocalHost().getHostAddress().equals(request.getLocalAddr()) ||
                                request.getRemoteAddr().equals(BaseConstants.Common.LOCAL_IPV6_ADDRESS) ||
                                request.getRemoteAddr().equals(BaseConstants.Common.LOCAL_IPV4_ADDRESS)){
                            continue;
                        }
                        if(!request.getRemoteAddr().equals(addr)){
                            CoreLibUtils.responseWithBody(response, HttpStatus.BAD_REQUEST, ResponseDTO.builder().errorObj(BAD_REQUEST).build());
                            return false;
                        }
                    }
                }
                SCHashRestriction hashRestriction = method.getMethodAnnotation(SCHashRestriction.class);
                if(hashRestriction != null){
                    String[] hashValues = CoreLibUtils.generateSecureHash(method.getMethod().getName());
                    if(hashRestriction.isPost()){
                        response.addHeader(HEADER_OWHF, hashValues[0]);
                        response.addHeader(HEADER_OWHT, hashValues[1]);
                    } else {
                        if(request.getHeader(HEADER_OWHF) != null && request.getHeader(HEADER_OWHF).equals(hashValues[0]) ||
                            request.getHeader(HEADER_OWHT) != null && request.getHeader(HEADER_OWHT).equals(hashValues[1])){
                            return true;
                        } else {
                            CoreLibUtils.responseWithBody(response, HttpStatus.BAD_REQUEST, ResponseDTO.builder().errorObj(BAD_REQUEST).build());
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomIpInterceptor()).addPathPatterns("/**");
    }
}
