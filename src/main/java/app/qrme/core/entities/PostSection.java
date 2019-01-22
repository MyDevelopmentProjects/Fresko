package app.qrme.core.entities;

import app.qrme.lib.data.entity.AbstractEntity;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "yts_post_section")
public class PostSection extends AbstractEntity {

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;


}
