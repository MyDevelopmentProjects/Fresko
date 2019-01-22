package app.qrme.lib.data.config;

import app.qrme.lib.data.dto.UserAuthDTO;
import app.qrme.lib.utils.BaseConstants;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginCfg {

    @NotNull
    @Valid
    private BaseConstants.LoginRegister type;

    @NotNull
    @Valid
    private UserAuthDTO credentials;

}