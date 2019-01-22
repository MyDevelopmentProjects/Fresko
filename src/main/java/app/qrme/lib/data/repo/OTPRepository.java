package app.qrme.lib.data.repo;

import app.qrme.lib.data.entity.OTP;
import app.qrme.lib.utils.BaseConstants;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OTPRepository extends GenericRepository<OTP, Long> {

    /**
     * This method must be called, before creating any new OTP for current user
     * @param phoneNumber is used to filter otp list by current user id
     */
    @Modifying(clearAutomatically = true)
    @Query("UPDATE OTP SET used = true where phoneNumber =:phoneNumber and type = :otpType")
    void markAllAsUsed(@Param("phoneNumber") String phoneNumber, @Param("otpType") BaseConstants.OtpType otpType);

    OTP findTopByPhoneNumberAndTypeOrderByIdDesc(String phoneNumber, BaseConstants.OtpType otpType);

    List<OTP> findAllByPhoneNumber(String phoneNumber);
}