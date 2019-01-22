package app.qrme.lib.utils;

import lombok.Getter;

public final class BaseConstants {

    public static final class Common {
        public static final String empty_string = "";
        public static final String space = " ";
        public static final String score = "-";
        public static final String under_score = "_";
        public static final String dot = ".";
        public static final String comma = ",";
        public static final String dblDot = ":";
        public static final String equal = "=";
        public static final String VALUE = "value";
        public static final String TYPE = "type";
        public static final String ip_address = "CLIENT_REMOTE_IP_ADDRESS";
        public static final String authorization = "Authorization";
        public static final String bearer = "Bearer ";
        public static final String basic_user_role = "BASIC_USER";
        public static final String super_user_role = "SUPER_ADMIN";
        public static final String LOCAL_IPV4_ADDRESS = "127.0.0.1";
        public static final String LOCAL_IPV6_ADDRESS = "0:0:0:0:0:0:0:1";
        public static final String QRME = "qrme";
        public static final String SC_IP_ADDR = "SC_IP_ADDR";
        public static final String HEADER_OWHF = "OWHF";
        public static final String HEADER_OWHT = "OWHT";
    }

    public enum QueryOperation{
        IN,
        NOT_IN,
        NOT_EQUAL,
        OR,
        EQUAL,
        SEARCH_EXPRESSION,
        LIKE,
        LESS_THAN,
        LESS_THAN_OR_EQUAL,
        GREATER_THAN,
        GREATER_THAN_OR_EQUAL,
        BETWEEN
    }

    public enum JwtTokenResult{
        NONE,
        VALID_TOKEN,
        INVALID_TOKEN,
        EXC_SIGNATURE,
        EXC_MAILFORMED,
        EXC_EXPIRED,
        EXC_UNSUPPORTED,
        EXC_ILLEGAL_ARGUMENT
    }

    public enum SingleEntryType {
        ACCESS_TOKEN,
        CODE,
        AUTHENTICATION,
        TWO_FACTOR_AUTH
    }

    public enum LoginRegister {
        SIMPLE,
        FACEBOOK,
        TWITTER,
        GOOGLE
    }

    public enum TwoFactorAuthType {
        OTP,
        GOTP
    }

    public enum OtpType {
        NONE,
        GENERAL,
        TWO_FACTOR_AUTH,
        PSW_RESET,
        PSW_UPD
    }

    public enum ExceptionType{
        GLOBAL_RUNTIME,
        HTTP_REQUEST
    }

    public enum ErrorObj {
        NONE(0),
        BAD_REQUEST(400),
        UNAUTHORIZED(401),
        NOT_FOUND(404),
        INTERNAL_SERVER_ERROR(500),
        INVALID_USERNAME_OR_PASSWORD(1000),
        ALL_FIELDS_ARE_REQUIRED(1001),
        USERNAME_ALREADY_EXISTS(1002),
        VALIDATION_ERROR(1003),
        SOMETHING_WENT_WRONG(1004),
        INVALID_2FA_CODE(1005),
        GOOGLE_2FA_REQUIRED(1006),
        OTP_2FA_REQUIRED(1007),
        TOO_MANY_SMS_REQUESTS(1008),
        INVALID_USERNAME(1009),
        PASSWORD_NOT_EQUAL(1010),
        INVALID_OTP_CODE(1011),
        SESSION_EXPIRED(1012),
        INVALID_TOKEN(1013),
        ALREADY_EXISTS(1014),
        INVALID_PARAMS(1015),
        NO_SYSTEM_FOUND(1016),
        NO_SUCH_TABLE(1017),
        TABLE_IS_DEACTIVATED(1018),
        CUSTOMER_NOT_FOUND(1019),
        ORDER_IS_BEING_PROCESSED(1020);

        //TODO: describe all needed error codes

        private int statusCode;
        private String msg;

        ErrorObj(int statusCode) {
            this.statusCode = statusCode;
        }

    }

    @Getter
    public enum SMSMessage{
        NONE(""),
        VALIDATION_CODE("Validation Code: {}");


        private String msg;

        SMSMessage(String msg) {
            this.msg = msg;
        }
    }

    public static final class SuccessCodes {

    }

    public static final class Encoding {
        public static final String UTF_8 = "UTF-8";
    }

    public static final class GA {
        public static final String googleQRCodeUrl = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
        public static final String googleOAuthUrl = "otpauth://totp/%s:%s?secret=%s&issuer=%s";
    }

}
