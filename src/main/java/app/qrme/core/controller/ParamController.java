package app.qrme.core.controller;

import app.qrme.core.data.repository.ParamsRepository;
import app.qrme.core.entities.Params;
import app.qrme.lib.controller.AbstractCRUDController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/param")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
public class ParamController extends AbstractCRUDController<Params, Long> {
    public ParamController(ParamsRepository repository) {
        super(repository);
    }
}
