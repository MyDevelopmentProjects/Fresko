package app.qrme.core.controller;

import app.qrme.core.data.repository.PostTagRepository;
import app.qrme.core.entities.PostTag;
import app.qrme.lib.controller.AbstractCRUDController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/postTag")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
public class PostTagController extends AbstractCRUDController<PostTag, Long> {
    public PostTagController(PostTagRepository repository) {
        super(repository);
    }
}
