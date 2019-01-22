package app.qrme.core.data.repository;

import app.qrme.core.entities.Slider;
import app.qrme.lib.data.repo.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SliderRepository extends GenericRepository<Slider, Long> {
}
