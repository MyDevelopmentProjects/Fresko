package app.qrme.core.entities;

import app.qrme.lib.data.entity.AbstractEntity;
import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Immutable
@Table(name = "main_posts")
public class MainPosts extends AbstractEntity {

    @Column(name = "category_id", nullable = false)
    private Long category_id;

    @Column(name = "created_by", nullable = false)
    private Long created_by;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "descr", nullable = false, length = 10485760)
    private String descr;

    @Column(name = "img_urls", length = 10485760)
    private String imgUrls;

    @Column(name = "video_urls", length = 10485760)
    private String videoUrls;

    @Column(name = "number_of_views", nullable = false, columnDefinition = " INT DEFAULT 0 ")
    private Integer numberOfViews;

    @Column(name = "order_num", nullable = false, columnDefinition = " INT DEFAULT 0 ")
    private Integer orderNum;

    @Column(name = "is_active", columnDefinition = " BOOLEAN DEFAULT TRUE ")
    private Boolean active;

    @Column(name = "has_comments", columnDefinition = " BOOLEAN DEFAULT TRUE ")
    private Boolean hasComments;

    @Column(name = "shows_date", columnDefinition = " BOOLEAN DEFAULT TRUE ")
    private Boolean showsDate;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "category_title", nullable = false)
    private String categoryTitle;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "color")
    private String color;

}