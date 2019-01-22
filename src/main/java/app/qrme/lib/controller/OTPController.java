package app.qrme.lib.controller;

import app.qrme.lib.data.dto.CodeOtpValidateDTO;
import app.qrme.lib.data.dto.GenOtpDTO;
import app.qrme.lib.data.entity.OTP;
import app.qrme.lib.data.repo.OTPRepository;
import app.qrme.lib.data.service.OTPService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/otp")
@Api(value = "One Time Password Controller", description = "This Controller is responsible for Generating One Time Password.")
public class OTPController extends AbstractCRUDController<OTP, Long> {

    @Autowired
    private OTPService otpService;

    public OTPController(OTPRepository otpRepository) {
        super(otpRepository);
    }

    /**
     * This will automatically generate new otp and save
     * @param params
     * @return
     */
    @PostMapping("/generate")
    public ResponseEntity generateOTP(@Valid @RequestBody GenOtpDTO params) {
        return otpService.generateOTP(params.getPhoneNumber(), params.getType());
    }

    /**
     * მოცემული მეთოდი გამოიყენება ოტპ ვალიდაციის მიზნით
     * @param params
     * @return
     */
    @PostMapping("/validate")
    public ResponseEntity validateOTP(HttpServletRequest request, @Valid @RequestBody CodeOtpValidateDTO params){
        return otpService.validateOTPCode(request, params.getPhoneNumber(), params.getType(), params.getCode());
    }
}