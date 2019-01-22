package app.qrme.core.data.repository;

import app.qrme.core.entities.MainPosts;
import app.qrme.lib.data.repo.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainPostsRepository extends GenericRepository<MainPosts, Long> {

    @Query(value = "SELECT * FROM main_posts() ORDER BY type ASC", nativeQuery = true)
    List<MainPosts> getAll();

}