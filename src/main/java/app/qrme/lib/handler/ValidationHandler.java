package app.qrme.lib.handler;

import app.qrme.lib.data.dto.ResponseDTO;
import app.qrme.lib.utils.BaseConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.util.List;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationHandler extends DefaultHandlerExceptionResolver {

    /**
     * არავალიდური ცვლადების პროცესორი, ერორებში ყრის ფილდებს
     * @param fieldErrors
     * @return
     */
    private ResponseEntity<ResponseDTO> processFieldErrors(List<FieldError> fieldErrors) {
        ResponseDTO errorResponse = ResponseDTO.builder()
                .errorObj(BaseConstants.ErrorObj.VALIDATION_ERROR)
                .build();
        for (FieldError fieldError: fieldErrors) {
            if(fieldError.getCodes().length > 0){
                errorResponse.getErrors().add(fieldError.getField() +":"+fieldError.getCodes()[fieldError.getCodes().length-1]);
            }
        }
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * ამუშავებს არავალიდურ ცვლადებს
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity<ResponseDTO> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

}