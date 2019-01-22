package app.qrme.core.controller;

import app.qrme.core.data.repository.BreakingNewsRepository;
import app.qrme.core.entities.BreakingNews;
import app.qrme.lib.controller.AbstractCRUDController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/breakingNews")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
public class BreakingNewsController extends AbstractCRUDController<BreakingNews, Long> {
    public BreakingNewsController(BreakingNewsRepository repository) {
        super(repository);
    }
}
