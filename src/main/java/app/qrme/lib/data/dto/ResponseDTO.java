package app.qrme.lib.data.dto;

import app.qrme.lib.utils.BaseConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T> {
    private boolean success;
    private T content;
    private BaseConstants.ErrorObj errorObj;
    @Builder.Default
    private List<String> errors = new ArrayList<>();

    public static ResponseDTO singleEntry(BaseConstants.SingleEntryType singleEntryType, Object v) {
        ResponseDTO<Map> successResponseDTO = new ResponseDTO<>();
        Map<String, Object> customObj = new HashMap<>(1);
        customObj.put(BaseConstants.Common.TYPE, singleEntryType.name());
        customObj.put(BaseConstants.Common.VALUE, v);
        successResponseDTO.setContent(customObj);
        return successResponseDTO;
    }

    public void setContent(T content) {
        this.success = true;
        this.content = content;
    }

    /**
     * Override Default Lombok Builder
     *
     * @param <T>
     */
    public static class ResponseDTOBuilder<T> {

        public ResponseDTOBuilder content(T content) {
            this.success = true;
            this.content = content;
            return this;
        }

        public ResponseDTOBuilder errorObj(BaseConstants.ErrorObj errorObj) {
            this.success = false;
            this.errorObj = errorObj;
            return this;
        }

        public ResponseDTOBuilder errors(List<String> errors) {
            this.success = false;
            this.errors = errors;
            return this;
        }

    }

}