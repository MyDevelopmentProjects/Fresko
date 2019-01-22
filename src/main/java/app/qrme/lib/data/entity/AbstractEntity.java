package app.qrme.lib.data.entity;

import app.qrme.core.utils.TimeAgo;
import app.qrme.lib.data.entity.embeddable.DateCreatedUpdate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Embedded
    private DateCreatedUpdate timestamp;

    @PrePersist
    void onCreate() {
        this.setTimestamp(DateCreatedUpdate.builder().created(new Date()).build());
    }

    @PreUpdate
    void onPersist() {
        if(timestamp != null) {
            this.getTimestamp().setUpdated(new Date());
        }
    }

    public String ago(String locale) {
        return TimeAgo.timeAgo(this.getTimestamp().getCreated().getTime() / 1000, locale);
    }

}
