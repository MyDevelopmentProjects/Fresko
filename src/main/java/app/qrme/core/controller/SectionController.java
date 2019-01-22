package app.qrme.core.controller;

import app.qrme.core.data.repository.SectionRepository;
import app.qrme.core.entities.Section;
import app.qrme.lib.controller.AbstractCRUDController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/section")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
public class SectionController extends AbstractCRUDController<Section, Long> {
    public SectionController(SectionRepository repository) {
        super(repository);
    }
}
