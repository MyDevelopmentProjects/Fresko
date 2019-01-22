package app.qrme.lib.data.entity;

import app.qrme.lib.utils.BaseConstants;
import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "yts_otp")
public class OTP extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "otp_type")
    private BaseConstants.OtpType type;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "code", length = 6)
    private Integer code;

    @Column(name = "is_used", columnDefinition = " BOOLEAN DEFAULT FALSE ")
    private boolean used;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_expiration")
    protected Date dateExpiration;

    @PrePersist
    void onCreate() {
        // One Minute Expiration time
        dateExpiration = new DateTime().plusMinutes(1).toDate();
    }
}