package app.qrme.core.data.repository;

import app.qrme.core.entities.PostCategory;
import app.qrme.lib.data.repo.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCategoryRepository extends GenericRepository<PostCategory, Long> {
}
