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
@Table(name = "yts_quick_links")
public class QuickLinks extends AbstractEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "href", nullable = false)
    private String href;

    @Column(name = "is_active", columnDefinition = " BOOLEAN DEFAULT TRUE ")
    private Boolean active;
}
