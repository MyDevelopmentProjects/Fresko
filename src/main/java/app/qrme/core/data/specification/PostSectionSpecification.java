package app.qrme.core.data.specification;

import app.qrme.core.entities.Post;
import app.qrme.core.entities.PostSection;
import org.springframework.data.jpa.domain.Specification;

public class PostSectionSpecification {

    public static Specification<PostSection> findBySection(String section) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (section != null) {
                return criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("section").get("title"), section)
                );
            }
            return null;
        };
    }

    public static Specification<Post> findRelated(Long categoryId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (categoryId != null) {
                return criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("category").get("id"), categoryId)
                );
            }
            return null;
        };
    }

}
