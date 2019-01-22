package app.qrme.lib.data.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "yts_role")
public class Role extends AbstractEntity {

    @Column(name = "name", length = 50)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "yts_role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissions;
}