package app.qrme.core.data.repository;

import app.qrme.core.entities.Params;
import app.qrme.lib.data.repo.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParamsRepository extends GenericRepository<Params, Long> {
}
