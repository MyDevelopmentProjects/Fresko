package app.qrme.lib.handler;

import app.qrme.lib.data.entity.BaseException;
import app.qrme.lib.data.repo.BaseExceptionRepository;
import app.qrme.lib.utils.BaseConstants;
import app.qrme.lib.utils.CoreLibUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private BaseExceptionRepository baseExceptionRepository;

    /**
     * არასწორი მეთოდის გამოყენების შემთხვევაში დალოგე.
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        // ვიჭერ იპ მისამართს და არასწორი მეთოდის შემთხვევაში ვალოგირებ
        String IP_ADDRESS = request.getHeader(BaseConstants.Common.SC_IP_ADDR);
        baseExceptionRepository.save(BaseException.builder()
                //.user(AuthUtil.currentUser())
                .ipAddress(IP_ADDRESS)
                .message(ex.getMessage())
                .canonicalName(ex.getClass().getCanonicalName())
                .type(BaseConstants.ExceptionType.HTTP_REQUEST)
                .stackTrace(CoreLibUtils.toJson(ex))
                .build());
        return super.handleHttpRequestMethodNotSupported(ex, headers, status, request);
    }
}
