package app.qrme.core.controller;

import app.qrme.core.data.repository.SliderRepository;
import app.qrme.core.entities.Slider;
import app.qrme.lib.controller.AbstractCRUDController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/slider")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
public class SliderController extends AbstractCRUDController<Slider, Long> {
    public SliderController(SliderRepository repository) {
        super(repository);
    }
}
