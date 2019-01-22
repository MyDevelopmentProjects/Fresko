package app.qrme.core.data.repository;

import app.qrme.core.entities.Section;
import app.qrme.lib.data.repo.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends GenericRepository<Section, Long> {
}
