package app.qrme.lib.controller;

import app.qrme.lib.data.config.QueryCfg;
import app.qrme.lib.data.entity.Role;
import app.qrme.lib.data.repo.RoleRepository;
import app.qrme.lib.utils.BaseConstants;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@Api(value = "Role Controller", description = "This Controller is responsible for Role.")
public class RoleController extends AbstractCRUDController<Role, Long> {

    @Autowired
    public RoleController(RoleRepository repository) {
        super(repository);
        this.getFilterFields().add(QueryCfg.builder()
                .field("name")
                .operation(BaseConstants.QueryOperation.LIKE)
                .build());
    }
}