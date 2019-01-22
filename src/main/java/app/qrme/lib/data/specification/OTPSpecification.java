package app.qrme.lib.data.specification;


import app.qrme.lib.data.entity.OTP;
import app.qrme.lib.utils.BaseConstants;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class OTPSpecification {

    /**
     * ვფილტრავთ გამოუყენებელ ერთჯერად პაროლებს მომხმარებლით
     * @param phoneNumber
     * @return
     */
    public static Specification<OTP> unusedOTP(String phoneNumber) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (phoneNumber != null) {
                return criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber),
                        criteriaBuilder.equal(root.get("used"), false)
                );
            }
            return null;
        };
    }

    /**
     * ვფილტრავთ გამოუყენებელ ერთჯერად პაროლებს მომხმარებლით და ტიპით
     * @param phoneNumber
     * @param type
     * @return
     */
    public static Specification<OTP> unusedOTP(String phoneNumber, BaseConstants.OtpType type){
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (phoneNumber != null && type != null) {
                return criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber),
                        criteriaBuilder.equal(root.get("type"), type),
                        criteriaBuilder.equal(root.get("used"), false),
                        criteriaBuilder.greaterThan(root.get("dateExpiration"), new Date())
                );
            }
            return null;
        };
    }
}
