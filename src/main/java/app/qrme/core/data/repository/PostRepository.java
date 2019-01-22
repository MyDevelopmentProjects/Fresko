package app.qrme.core.data.repository;

import app.qrme.core.entities.Post;
import app.qrme.lib.data.repo.GenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PostRepository extends GenericRepository<Post, Long> {
    Page<Post> findAllByCategoryId(Pageable pageable, Long categoryId);

    Page<Post> findByCategoryIdAndTitleContainingOrDescrContaining(Pageable pageable, Long categoryId, String title, String descr);

    Page<Post> findByTitleContainingOrDescrContaining(Pageable pageable, String title, String descr);

}
