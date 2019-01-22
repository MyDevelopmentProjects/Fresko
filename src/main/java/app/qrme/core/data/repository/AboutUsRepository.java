package app.qrme.core.data.repository;

import app.qrme.core.entities.AboutUs;
import app.qrme.lib.data.repo.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutUsRepository extends GenericRepository<AboutUs, Long> {

}
