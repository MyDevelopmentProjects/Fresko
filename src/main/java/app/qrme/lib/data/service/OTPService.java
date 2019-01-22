package app.qrme.lib.data.service;

import app.qrme.core.utils.net.maradit.api.Maradit;
import app.qrme.core.utils.net.maradit.api.SubmitResponse;
import app.qrme.lib.data.dto.ResponseDTO;
import app.qrme.lib.data.entity.OTP;
import app.qrme.lib.data.mapper.OTPMapper;
import app.qrme.lib.data.repo.OTPRepository;
import app.qrme.lib.data.specification.OTPSpecification;
import app.qrme.lib.utils.BaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static app.qrme.lib.utils.BaseConstants.ErrorObj.SOMETHING_WENT_WRONG;
import static app.qrme.lib.utils.BaseConstants.ErrorObj.TOO_MANY_SMS_REQUESTS;
import static app.qrme.lib.utils.CoreLibUtils.generateNumbers;

@Service
public class OTPService {

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private OTPMapper otpMapper;

    /**
     * Called when user wants to change something or delete something or do login, password recovery etc...
     * NOTE: call this, only if user has enabled havingOtp flag to true
     * MIDDLEWARE must also send message at the same time, after calling this method
     * MIDDLEWARE must fetch current user details, get mobile and country info and send message
     *
     * @param phoneNumber
     * @param type
     * @return success in case user is authorized and data is saved successfully, otherwise returns bad request
     */
    @Transactional
    public ResponseEntity<ResponseDTO> generateOTP(String phoneNumber, BaseConstants.OtpType type) {
        List<OTP> otpList = otpRepository.findAll(OTPSpecification.unusedOTP(phoneNumber, type));
        if (otpList.isEmpty()) {
            // Mark All As Used
            this.markAllAsUsed(phoneNumber, type);

            // Generated Codes
            Integer generatedNumbers = Integer.valueOf(generateNumbers(4));

            // Create new Generated Code
            OTP otpGenerated = OTP.builder()
                    .code(generatedNumbers)
                    .phoneNumber(phoneNumber)
                    .used(false)
                    .type(type)
                    .build();

            SubmitResponse submitResponse = new Maradit(
                    BaseConstants.SMSMessage.VALIDATION_CODE.getMsg().replace("{}", otpGenerated.getCode().toString()),
                    Collections.singletonList(phoneNumber)
            ).submit();
            if (submitResponse.messageId > 0) {
                otpRepository.save(otpGenerated);
                return ResponseEntity.ok().body(ResponseDTO.builder().content(otpMapper.map(otpGenerated)).build());
            } else {
                return ResponseEntity.badRequest().body(ResponseDTO.builder().errorObj(SOMETHING_WENT_WRONG).build());
            }
        } else {
            return ResponseEntity.badRequest().body(ResponseDTO.builder().errorObj(TOO_MANY_SMS_REQUESTS).build());
        }
    }

    /**
     * ყველა წინამდებარე ოტპ,ს ვნიშნავთ როგორც გამოყენებულს
     *
     * @param phoneNumber
     * @param type
     */
    @Transactional
    public void markAllAsUsed(String phoneNumber, BaseConstants.OtpType type) {
        otpRepository.markAllAsUsed(phoneNumber, type);
    }

    /**
     * ვამოწმებ ვალიდურია არის თუ არა ოტპ,
     *
     * @param phoneNumber
     * @param type
     * @param code
     * @return
     */
    @Transactional
    public OTP validateOTP(String phoneNumber, BaseConstants.OtpType type, Integer code) {
        OTP otp = otpRepository.findTopByPhoneNumberAndTypeOrderByIdDesc(phoneNumber, type);
        if (otp != null && !otp.isUsed() && otp.getCode().equals(code) && otp.getDateExpiration().getTime() > new Date().getTime()) {
            return otp;
        }
        return null;
    }

    /**
     * ვამოწმებ ვალიდურია არის თუ არა ოტპ, და შესაბამის ResponseEntity ვაბრუნებ
     *
     * @param phoneNumber
     * @param type
     * @param code
     * @return
     */
    public ResponseEntity validateOTPCode(HttpServletRequest request, String phoneNumber, BaseConstants.OtpType type, Integer code) {
        if (this.validateOTP(phoneNumber, type, code) != null) {
//            Customer customer = customerRepository.findByPhoneNumber(phoneNumber);
//            if(customer != null){
//                return authService.loginCustomer(request, customer);
//            } else {
//                return ResponseEntity.badRequest().body(ResponseDTO.builder()
//                        .content(BaseConstants.ErrorObj.CUSTOMER_NOT_FOUND)
//                        .build());
//            }
        }
        return ResponseEntity.badRequest().body(ResponseDTO.builder()
                .content(BaseConstants.ErrorObj.INVALID_OTP_CODE)
                .build());
    }

}
