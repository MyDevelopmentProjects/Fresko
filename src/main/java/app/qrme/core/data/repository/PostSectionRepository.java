package app.qrme.core.data.repository;

import app.qrme.core.entities.PostSection;
import app.qrme.lib.data.repo.GenericRepository;
import app.qrme.lib.utils.BaseConstants;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PostSectionRepository extends GenericRepository<PostSection, Long> {

    List<PostSection> findAllByPostId(Long postId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM PostSection where post.id =:postId")
    @Transactional
    void removeAllByPostId(@Param("postId") Long postId);

}
