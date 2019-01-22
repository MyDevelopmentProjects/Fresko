package app.qrme.core.data.repository;

import app.qrme.core.entities.SliderCategory;
import app.qrme.lib.data.repo.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SliderCategoryRepository extends GenericRepository<SliderCategory, Long> {
}
