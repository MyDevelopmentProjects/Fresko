package app.qrme.core.entities;

import app.qrme.core.utils.GeneralUtil;
import app.qrme.lib.data.entity.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.text.DecimalFormat;
import java.text.NumberFormat;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "yts_products")
public class Products extends AbstractEntity {

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

    @Column(name = "old_price")
    private String oldPrice;

    @Column(name = "new_price")
    private String newPrice;

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
        return this.getDescrEn();
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

    public String getOP() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(Double.valueOf(this.getOldPrice()));
    }

    public String getNP() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(Double.valueOf(this.getNewPrice()));
    }
}
