package app.qrme.core.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadFileDTO {
    private String name;
    private String type;
}
