package app.qrme.core.controller;

import app.qrme.core.data.repository.PostCategoryRepository;
import app.qrme.core.entities.PostCategory;
import app.qrme.lib.controller.AbstractCRUDController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/postCategory")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
public class PostCategoryController extends AbstractCRUDController<PostCategory, Long> {
    public PostCategoryController(PostCategoryRepository repository) {
        super(repository);
    }
}
