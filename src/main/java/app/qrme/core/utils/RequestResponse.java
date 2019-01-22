package app.qrme.core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestResponse {
    private Boolean success = false;
    private String message;
    private List<String> errors = new ArrayList<>();
    private Map<String,String> meta = new HashMap<>();
    private Integer code;

    public RequestResponse() {
    }

    public RequestResponse(Boolean success) {
        this.success = success;
    }

    public RequestResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public RequestResponse(Boolean success, String message, Integer code) {
        this.success = success;
        this.message = message;
        this.code = code;
    }

    public RequestResponse(Boolean success, String message, Map<String, String> meta, Integer code) {
        this.success = success;
        this.message = message;
        this.meta = meta;
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Map<String, String> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, String> meta) {
        this.meta = meta;
    }


    public static class RequestResponseBuilder {
        private Boolean success = false;
        private String message;
        private List<String> errors = new ArrayList<>();
        private Map<String,String> meta = new HashMap<>();
        private Integer code;

        public RequestResponseBuilder setSuccess(Boolean success) {
            this.success = success;
            return this;
        }

        public RequestResponseBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public RequestResponseBuilder setErrors(List<String> errors) {
            this.errors = errors;
            return this;
        }

        public RequestResponseBuilder setMeta(Map<String, String> meta) {
            this.meta = meta;
            return this;
        }

        public RequestResponseBuilder setCode(Integer code) {
            this.code = code;
            return this;
        }

        public RequestResponse build(){
            RequestResponse response = new RequestResponse();
            response.setCode(this.code);
            response.setMessage(this.message);
            response.setErrors(this.errors);
            response.setMeta(this.meta);
            response.setSuccess(this.success);
            return response;
        }
    }


}