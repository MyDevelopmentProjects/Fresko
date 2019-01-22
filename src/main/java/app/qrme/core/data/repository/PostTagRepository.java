package app.qrme.core.data.repository;

import app.qrme.core.entities.PostTag;
import app.qrme.lib.data.repo.GenericRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PostTagRepository extends GenericRepository<PostTag, Long> {
    List<PostTag> findAllByPostId(Long postId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM PostTag where post.id =:postId")
    @Transactional
    void removeAllByPostId(@Param("postId") Long postId);
}
