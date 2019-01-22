package app.qrme.core.entities;

import app.qrme.core.utils.GeneralUtil;
import app.qrme.lib.data.entity.AbstractEntity;
import app.qrme.lib.data.entity.BaseUser;
import lombok.*;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.persistence.*;
import java.util.Locale;

import static app.qrme.core.utils.GeneralUtil.html2text;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "yts_post")
public class Post extends AbstractEntity {

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private PostCategory category;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by", nullable = false)
    private BaseUser createdBy;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "title_ru")
    private String titleRu;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "descr", nullable = false, length = 10485760)
    private String descr;

    @Column(name = "descr_ru", length = 10485760)
    private String descrRu;

    @Column(name = "descr_en", length = 10485760)
    private String descrEn;

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

    public String localizedTitle(String locale) {
        if (locale.equalsIgnoreCase("KA")) {
            return html2text(this.getTitle());
        } else if (locale.equalsIgnoreCase("RU")) {
            return html2text(this.getTitleRu());
        }
        return html2text(this.getTitleEn());
    }

    public String localizedDescr(String locale) {
        if (locale.equalsIgnoreCase("KA")) {
            return this.getDescr();
        } else if (locale.equalsIgnoreCase("RU")) {
            return this.getDescrRu();
        }
        return this.getDescrEn();
    }

    public String shortTitle(int count) {
        return GeneralUtil.firstWords(this.title, count);
    }

    public String shotDesc(int count, String locale) {
        return GeneralUtil.firstWords(this.localizedDescr(locale), count);
    }

    public String seoTitle() {
        return html2text(this.getTitle()
                .replaceAll(" ", "_")
                .replaceAll("\"", "")
                .replaceAll("„", "")
                .replaceAll("“", "")
                .replaceAll("`", "")
                .replaceAll("%", "")
                .replaceAll("_-_", "")
                .replaceAll("&", "")
                .replaceAll("#", "")
                .replaceAll("@", "")
                .replaceAll("!", "")
                .replaceAll("/", "")
                .replaceAll("/", "")
                .replaceAll("\\[", "")
                .replaceAll("\\]", "")
                .replaceAll("\\{", "")
                .replaceAll("\\}", "")
                .replaceAll("'", ""));
    }

    public String seoShortTitle() {
        return html2text(this.shortTitle(14).replaceAll(" ", "_"));
    }

    public String fbImg() {
        if (imgUrls == null || imgUrls.length() < 3) {
            return "";
        }
        return "https://yts.ge/uploads/" + imgUrls
                .replaceAll("\"", "")
                .replaceAll("\\[", "")
                .replaceAll("]", "");
    }

    public String shortHref() {
        String title = seoShortTitle();
        if (title.endsWith("?")) {
            title = title.substring(0, title.length() - 1);
        }
        return "/detail/" + title + "-" + this.getId();
    }

    public String href() {
        String title = seoTitle();
        if (title.endsWith("?")) {
            title = title.substring(0, title.length() - 1);
        }
        return "/detail/" + title + "-" + this.getId();
    }

}
