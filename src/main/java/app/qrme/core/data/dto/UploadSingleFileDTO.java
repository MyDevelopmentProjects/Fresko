package app.qrme.core.data.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadSingleFileDTO {
    private String filelink;
    private String filename;
}
