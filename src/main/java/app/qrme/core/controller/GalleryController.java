package app.qrme.core.controller;

import app.qrme.core.data.repository.GalleryRepository;
import app.qrme.core.data.repository.ParamsRepository;
import app.qrme.core.entities.Gallery;
import app.qrme.core.entities.Params;
import app.qrme.lib.controller.AbstractCRUDController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gallery")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
public class GalleryController extends AbstractCRUDController<Gallery, Long> {
    public GalleryController(GalleryRepository repository) {
        super(repository);
    }
}
