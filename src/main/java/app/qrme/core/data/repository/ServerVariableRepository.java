package app.qrme.core.data.repository;

import app.qrme.core.entities.ServerVariable;
import app.qrme.lib.data.repo.GenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by user on 4/22/18.
 */
@Repository
public interface ServerVariableRepository extends GenericRepository<ServerVariable, Long> {
    ServerVariable findByServerKey(String param);
}
