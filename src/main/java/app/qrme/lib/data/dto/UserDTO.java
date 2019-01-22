package app.qrme.lib.data.dto;

import app.qrme.lib.utils.CoreLibUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import static app.qrme.lib.utils.BaseConstants.Common.QRME;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String facebookUrl;
    private String twitterUrl;
    private String googleUrl;
    private String googleSecretKey;
    private boolean havingOtp = false;
    private boolean havingGAOtp = false;
    private String qrCodeUrl;

    public String getQrCodeUrl() {
        if (!StringUtils.isEmpty(this.googleSecretKey) && !this.havingOtp) {
            return CoreLibUtils.generateQRForGoogle(QRME, this.getGoogleSecretKey());
        }
        return null;
    }

}
