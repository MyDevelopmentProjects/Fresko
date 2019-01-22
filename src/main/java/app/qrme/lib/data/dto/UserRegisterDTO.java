package app.qrme.lib.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {

    @NotEmpty
    @Email
    private String username;
    @Size(min = 8, max = 24)
    private String password;
    @Size(min = 2)
    private String firstName;
    @Size(min = 2)
    private String lastName;
    private String facebookUrl;
    private String twitterUrl;
    private String googleUrl;

}