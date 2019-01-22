package app.qrme.core.entities;

import app.qrme.core.utils.GeneralUtil;
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
@Table(name = "yts_about_us")
public class AboutUs extends AbstractEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "title_ru")
    private String titleRu;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "img_urls", length = 10485760)
    private String imgUrls;

    @Column(name = "descr", nullable = false, length = 10485760)
    private String descr;

    @Column(name = "descr_ru", length = 10485760)
    private String descrRu;

    @Column(name = "descr_en", length = 10485760)
    private String descrEu;

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
            return this.getDescr();
        } else if (locale.equalsIgnoreCase("RU")) {
            return this.getDescrRu();
        }
        return this.getDescrEu();
    }

    public String shortTitle(int count) {
        return GeneralUtil.firstWords(this.title, count);
    }

    public String shotDesc(int count, String locale) {
        return GeneralUtil.firstWords(this.localizedDescr(locale), count);
    }

    public String seoTitle() {
        return this.getTitle().replaceAll(" ", "_");
    }

    public String href() {
        return "/detail/" + seoTitle() + "-" + this.getId();
    }

}
