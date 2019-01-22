package app.qrme.lib.data.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrentUserDTO {
    private String type;
    private Object userDetail;
}
