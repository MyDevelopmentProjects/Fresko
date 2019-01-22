package app.qrme.lib.data.dto;

import app.qrme.lib.utils.BaseConstants;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GenOtpDTO {
    @NotNull
    private String phoneNumber;
    @NotNull
    private BaseConstants.OtpType type;
}
