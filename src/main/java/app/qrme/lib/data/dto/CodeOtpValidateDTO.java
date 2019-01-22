package app.qrme.lib.data.dto;

import app.qrme.lib.utils.BaseConstants;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CodeOtpValidateDTO {
    @NotNull
    private String phoneNumber;
    @NotNull
    private Integer code;
    @NotNull
    private BaseConstants.OtpType type;
}
