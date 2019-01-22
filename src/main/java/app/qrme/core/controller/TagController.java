package app.qrme.core.controller;

import app.qrme.core.data.repository.TagRepository;
import app.qrme.core.entities.Tag;
import app.qrme.lib.controller.AbstractCRUDController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
public class TagController extends AbstractCRUDController<Tag, Long> {
    public TagController(TagRepository repository) {
        super(repository);
    }
}
