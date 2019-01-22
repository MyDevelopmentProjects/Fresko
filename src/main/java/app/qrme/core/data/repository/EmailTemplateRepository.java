package app.qrme.core.data.repository;

import app.qrme.core.entities.EmailTemplate;
import app.qrme.core.enums.EOrderState;
import app.qrme.lib.data.repo.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailTemplateRepository extends GenericRepository<EmailTemplate, Long> {
    Optional<EmailTemplate> findByOrderState(EOrderState state);
}
