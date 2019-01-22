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
@Table(name = "yts_params")
public class Params extends AbstractEntity {

    @Column(name = "fb_url")
    private String fbUrl;

    @Column(name = "gmail_url")
    private String gmailUrl;

    @Column(name = "youtube_url")
    private String youtubeUrl;

    @Column(name = "contact_addr")
    private String contactAddr;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "contact_phone2")
    private String contactPhone2;

    @Column(name = "contactEmail")
    private String contactEmail;

    @Column(name = "contactSkype")
    private String contactSkype;

}
