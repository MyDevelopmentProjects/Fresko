package app.qrme.core.data.repository;

import app.qrme.core.entities.Tag;
import app.qrme.lib.data.repo.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends GenericRepository<Tag, Long> {
}
