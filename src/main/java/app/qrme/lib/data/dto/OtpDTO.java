package app.qrme.lib.data.dto;

import app.qrme.lib.utils.BaseConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpDTO {
    private String phoneNumber;
    private BaseConstants.OtpType type;
    private String code;
    private boolean used;
    protected Date dateExpiration;
}
