package app.qrme.lib.data.dto;

import app.qrme.lib.annotations.nonempty.SCPasswordNonEmpty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SCPasswordNonEmpty.List({
    @SCPasswordNonEmpty(
        fieldName = "password",
        twitter = "twitterUrl",
        google = "googleUrl",
        facebook = "facebookUrl"
    )
})
public class UserAuthDTO {

    @Email
    private String username;
    @Size(min = 8)
    private String password;
    private Integer auth2FACode;
    private String facebookUrl;
    private String googleUrl;
    private String twitterUrl;

}
