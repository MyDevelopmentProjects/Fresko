package app.qrme.core.entities;

import app.qrme.lib.data.entity.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "yts_server_variable")
public class ServerVariable extends AbstractEntity {

    @Column(name = "server_key", unique = true)
    private String serverKey;

    @Column(name = "server_val")
    private String serverVal;
}