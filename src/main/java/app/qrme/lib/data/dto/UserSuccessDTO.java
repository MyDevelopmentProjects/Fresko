package app.qrme.lib.data.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSuccessDTO {
    private String token;
    private UserDTO user;
}
