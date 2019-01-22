package app.qrme.core.entities;

import app.qrme.core.utils.GeneralUtil;
import app.qrme.lib.data.entity.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static app.qrme.core.utils.GeneralUtil.html2text;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "yts_gallery")
public class Gallery extends AbstractEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "title_ru")
    private String titleRu;

    @Column(name = "descr", length = 10485760)
    private String descr;

    @Column(name = "descr_ru", length = 10485760)
    private String descrRu;

    @Column(name = "descr_en", length = 10485760)
    private String descrEn;

    @Column(name = "images", nullable = false, length = 10485760)
    private String images;

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
