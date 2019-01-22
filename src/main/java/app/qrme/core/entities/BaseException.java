package app.qrme.core.entities;

import app.qrme.lib.data.entity.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "yts_exceptions")
public class BaseException extends AbstractEntity {

    @Column(name = "body", length=10485760)
    private String body;

    @Column(name = "stack_trace", length=10485760)
    private String stackTrace;

    @Column(name = "canonical_name", length=10485760)
    private String getCanonicalName;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "data", length=10485760)
    private String data;

}
