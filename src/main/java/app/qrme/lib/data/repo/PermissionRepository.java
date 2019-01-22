package app.qrme.lib.data.repo;

import app.qrme.lib.data.entity.Permission;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends GenericRepository<Permission, Long> {
}