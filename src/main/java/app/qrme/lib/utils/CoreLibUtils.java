package app.qrme.lib.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import org.apache.http.entity.ContentType;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static app.qrme.lib.utils.BaseConstants.Common.empty_string;
import static app.qrme.lib.utils.BaseConstants.Common.ip_address;
import static app.qrme.lib.utils.BaseConstants.Encoding.UTF_8;
import static app.qrme.lib.utils.BaseConstants.GA.googleOAuthUrl;
import static app.qrme.lib.utils.BaseConstants.GA.googleQRCodeUrl;

@Component
public class CoreLibUtils {

    private static String LEFT_SALT;
    private static String RIGHT_SALT;

    @Value("${app.leftsalt}")
    public void setLeftSalt(String leftSalt) {
        LEFT_SALT = leftSalt;
    }

    @Value("${app.rightsalt}")
    public void setRightSalt(String rightSalt) {
        RIGHT_SALT = rightSalt;
    }

    public static String generateQRForGoogle(String email, String secretKey) {
        try {
            return googleQRCodeUrl + URLEncoder.encode(String.format(googleOAuthUrl, empty_string, email, secretKey, empty_string), UTF_8);
        } catch (UnsupportedEncodingException e) {
        }
        return empty_string;
    }

    //TODO: we need to tell Apolon to send the ip address of the client
    public static String getClientIp(HttpServletRequest request) {
        String remoteAddr = empty_string;
        if (request != null) {
            remoteAddr = request.getHeader(ip_address);
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

    public static <T> String toJson(T object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (IOException e) {
            //TODO: log exception
        }
        return empty_string;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.equals(empty_string);
    }


    public static String generateString(int length) {
        if (length < 5) length = 5;
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rng = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = chars.charAt(rng.nextInt(chars.length()));
        }
        return String.valueOf(text);
    }

    public static String generateNumbers(int length) {
        String chars = "123456789";
        Random rng = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = chars.charAt(rng.nextInt(chars.length()));
        }
        return String.valueOf(text);
    }

    public static HttpServletResponse responseWithBody(HttpServletResponse response, HttpStatus status, Object responseObject) throws IOException {
        response.setStatus(status.value());
        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        response.getWriter().write(CoreLibUtils.toJson(responseObject));
        return response;
    }

    public static String[] generateSecureHash(String methodName) {
        int SECOND_MINS_30 = LocalDateTime.now().minusSeconds(30).getMinuteOfHour();
        int SECOND_PLUS_30 = LocalDateTime.now().plusSeconds(30).getMinuteOfHour();
        return new String[]{
                Hashing.sha256()
                        .hashString(LEFT_SALT + methodName + SECOND_MINS_30 + RIGHT_SALT, StandardCharsets.UTF_8)
                        .toString(),
                Hashing.sha256()
                        .hashString(LEFT_SALT + methodName + SECOND_PLUS_30 + RIGHT_SALT, StandardCharsets.UTF_8)
                        .toString()
        };
    }
}
