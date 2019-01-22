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
@Table(name = "yts_section")
public class Section extends AbstractEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "translated")
    private String translated;

}
