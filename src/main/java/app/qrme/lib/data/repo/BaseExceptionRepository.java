package app.qrme.lib.data.repo;

import app.qrme.lib.data.entity.BaseException;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseExceptionRepository extends GenericRepository<BaseException, Long> {
}