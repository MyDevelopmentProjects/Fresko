package app.qrme.core.data.repository;

import app.qrme.core.entities.QuickLinks;
import app.qrme.lib.data.repo.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuickLinksRepository extends GenericRepository<QuickLinks, Long> {
}
