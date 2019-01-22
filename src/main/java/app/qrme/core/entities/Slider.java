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
@Table(name = "yts_slider")
public class Slider extends AbstractEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "title_ru")
    private String titleRu;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "img_url", nullable = false, length = 1000)
    private String imgUrl;

    @Column(name = "href", nullable = false, length = 1000)
    private String href;

    @Column(name = "small_desc")
    private String smallDesc;

    @Column(name = "small_desc_ru")
    private String smallDescRu;

    @Column(name = "small_desc_en")
    private String smallDescEn;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private SliderCategory category;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by", nullable = false)
    private BaseUser createdBy;

    @Column(name = "order_num", nullable = false, columnDefinition = " INT DEFAULT 0 ")
    private Integer orderNum;

    @Column(name = "is_active", columnDefinition = " BOOLEAN DEFAULT TRUE ")
    private Boolean active;

    @Column(name = "shows_date", columnDefinition = " BOOLEAN DEFAULT TRUE ")
    private Boolean showsDate;

    public String localizedTitle(String locale) {
        if (locale.equalsIgnoreCase("KA")) {
            return this.getTitle();
        } else if (locale.equalsIgnoreCase("RU")) {
            return this.getTitleRu();
        }
        return this.getTitleEn();
    }

    public String localizedDescr(String locale) {
        if (locale.equalsIgnoreCase("KA")) {
            return this.smallDesc;
        } else if (locale.equalsIgnoreCase("RU")) {
            return this.smallDescRu;
        }
        return this.smallDescEn;
    }

}
