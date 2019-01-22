package app.qrme.lib.handler;

import app.qrme.lib.data.dto.ResponseDTO;
import app.qrme.lib.data.entity.BaseException;
import app.qrme.lib.data.repo.BaseExceptionRepository;
import app.qrme.lib.exception.SCException;
import app.qrme.lib.utils.BaseConstants;
import app.qrme.lib.utils.CoreLibUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private BaseExceptionRepository baseExceptionRepository;

    /**
     * გლობალური RuntimeException, ლოგერი
     *
     * @param exc
     */
    @ExceptionHandler({RuntimeException.class})
    public void ExceptionHandler(Exception exc) {
        baseExceptionRepository.save(BaseException.builder()
                //.user(AuthUtil.currentUser())
                .message(exc.getMessage())
                .canonicalName(exc.getClass().getCanonicalName())
                .type(BaseConstants.ExceptionType.GLOBAL_RUNTIME)
                .stackTrace(CoreLibUtils.toJson(exc.getStackTrace()))
                .build());
    }

    /**
     * ეს ხდება როდესაც რაღაც მონაცემი უკვე არსებობს, დუპლიკატი
     *
     * @param exc
     * @return
     */
    @ResponseBody
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ResponseDTO> SqlExceptionHandler(Exception exc) {
        baseExceptionRepository.save(BaseException.builder()
                //.user(AuthUtil.currentUser())
                .message(exc.getMessage())
                .canonicalName(exc.getClass().getCanonicalName())
                .type(BaseConstants.ExceptionType.GLOBAL_RUNTIME)
                .stackTrace(CoreLibUtils.toJson(exc.getStackTrace()))
                .build());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseDTO.builder()
                .errorObj(BaseConstants.ErrorObj.ALREADY_EXISTS)
                .build());
    }

    /**
     * @param exc
     */
    @ExceptionHandler({SCException.class})
    public void SCExceptionHandler(Exception exc) {
        baseExceptionRepository.save(BaseException.builder()
                //.user(AuthUtil.currentUser())
                .message(exc.getMessage())
                .canonicalName(exc.getClass().getCanonicalName())
                .type(BaseConstants.ExceptionType.GLOBAL_RUNTIME)
                .stackTrace(CoreLibUtils.toJson(exc.getStackTrace()))
                .build());
    }

}