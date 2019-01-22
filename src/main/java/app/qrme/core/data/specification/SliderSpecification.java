package app.qrme.core.data.specification;

import app.qrme.core.entities.Slider;
import org.springframework.data.jpa.domain.Specification;

public class SliderSpecification {
    public static Specification<Slider> enabled() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("active"), true)
        );
    }

}
