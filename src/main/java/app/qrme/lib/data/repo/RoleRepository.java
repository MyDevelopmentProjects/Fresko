package app.qrme.lib.data.repo;

import app.qrme.lib.data.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends GenericRepository<Role, Long> {
    Role findByName(String name);
}