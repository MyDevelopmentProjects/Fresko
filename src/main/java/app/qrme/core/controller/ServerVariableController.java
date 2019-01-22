package app.qrme.core.controller;

import app.qrme.core.data.repository.ServerVariableRepository;
import app.qrme.core.entities.ServerVariable;
import app.qrme.lib.controller.AbstractCRUDController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server-variable")
public class ServerVariableController extends AbstractCRUDController<ServerVariable, Long> {

    public ServerVariableController(ServerVariableRepository repository) {
        super(repository);
    }
}
