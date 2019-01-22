package app.qrme.core.controller;

import app.qrme.core.data.repository.SliderCategoryRepository;
import app.qrme.core.entities.SliderCategory;
import app.qrme.lib.controller.AbstractCRUDController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sliderCategory")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
public class SliderCategoryController extends AbstractCRUDController<SliderCategory, Long> {
    public SliderCategoryController(SliderCategoryRepository repository) {
        super(repository);
    }
}
