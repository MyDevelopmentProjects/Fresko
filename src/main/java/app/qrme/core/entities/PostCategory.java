package app.qrme.core.entities;

import app.qrme.lib.data.entity.AbstractEntity;
import app.qrme.lib.data.entity.BaseUser;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "yts_post_category")
public class  PostCategory extends AbstractEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "img_url", length = 1000)
    private String imgUrl;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by", nullable = false)
    private BaseUser createdBy;

    @Column(name = "order_num", nullable = false, columnDefinition = " INT DEFAULT 0 ")
    private Integer orderNum;

    @Column(name = "is_active", columnDefinition = " BOOLEAN DEFAULT TRUE ")
    private boolean active;

}
