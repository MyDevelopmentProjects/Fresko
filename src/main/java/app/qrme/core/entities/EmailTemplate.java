package app.qrme.core.entities;

import app.qrme.core.enums.EOrderState;
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
@Table(name = "yts_email_template")
public class EmailTemplate extends AbstractEntity {

    @Column(name = "template_name", unique = true)
    private String templateName;

    @Column(name = "template_value", length = 10485760)
    private String templateValue;

    @Column(name = "order_state")
    private EOrderState orderState;
}
