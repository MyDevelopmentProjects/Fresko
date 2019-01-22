package app.qrme.core.data.repository;

import app.qrme.core.entities.BreakingNews;
import app.qrme.lib.data.repo.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreakingNewsRepository extends GenericRepository<BreakingNews, Long> {
}
