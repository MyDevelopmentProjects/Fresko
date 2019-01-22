package app.qrme.lib.data.mapper;

import app.qrme.lib.data.dto.OtpDTO;
import app.qrme.lib.data.entity.OTP;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface OTPMapper {
    OtpDTO map(OTP otp);
    List<OtpDTO> map(List<OTP> otpList);
}