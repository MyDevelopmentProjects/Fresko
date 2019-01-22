package app.qrme.lib.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "yts_user")
public class BaseUser extends AbstractEntity {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "yts_user_role",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> role;

    @Column(name = "user_name", unique = true, nullable = false)
    private String username;

    @Column(name = "password", length = 61, nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "facebook_url", unique = true)
    private String facebookUrl;

    @Column(name = "twitter_url", unique = true)
    private String twitterUrl;

    @Column(name = "google_url", unique = true)
    private String googleUrl;

    @Column(name = "google_secret_key")
    private String googleSecretKey;

    @Column(name = "is_having_otp", columnDefinition = " BOOLEAN DEFAULT FALSE ")
    private boolean havingOtp = false;

    @Column(name = "is_having_ga_otp", columnDefinition = " BOOLEAN DEFAULT FALSE ")
    private boolean havingGAOtp = false;

    @Column(name = "is_active", columnDefinition = " BOOLEAN DEFAULT TRUE ")
    private boolean active = true;

    public BaseUser() {
    }

    public BaseUser(BaseUser user) {
        this.id = user.id;
        this.role = user.role;
        this.username = user.username;
        this.password = user.password;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.facebookUrl = user.facebookUrl;
        this.twitterUrl = user.twitterUrl;
        this.googleUrl = user.googleUrl;
        this.googleSecretKey = user.googleSecretKey;
        this.havingOtp = user.havingOtp;
        this.havingGAOtp = user.havingGAOtp;
        this.active = user.active;
    }

    public void toggleOtp(Boolean enabled) {
        this.havingGAOtp = false;
        this.havingOtp = enabled;
    }
    public void toggleGOtp(Boolean enabled) {
        this.havingOtp = false;
        this.havingGAOtp = enabled;
    }
}