package app.qrme.lib.data.entity;


import app.qrme.lib.utils.BaseConstants;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "yts_base_exception")
public class BaseException extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private BaseUser user;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "body", length = 10485760)
    private String body;

    @Column(name = "message", length = 10485760)
    private String message;

    @Column(name = "stack_trace", length = 10485760)
    private String stackTrace;

    @Column(name = "canonical_name", length = 10485760)
    private String canonicalName;

    @Enumerated(EnumType.STRING)
    @Column(name = "error_type")
    private BaseConstants.ExceptionType type;

}