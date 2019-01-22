package app.qrme.lib.data.repo;

import app.qrme.lib.data.entity.BaseUser;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface BaseUserRepository extends GenericRepository<BaseUser, Long> {
    Optional<BaseUser> findByUsername(String username);
    Optional<BaseUser> findByGoogleUrl(String url);
    Optional<BaseUser> findByTwitterUrl(String url);
    Optional<BaseUser> findByFacebookUrl(String url);
}