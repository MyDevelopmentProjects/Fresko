package app.qrme.lib.controller;

import app.qrme.lib.data.config.QueryCfg;
import app.qrme.lib.data.entity.Permission;
import app.qrme.lib.data.repo.PermissionRepository;
import app.qrme.lib.utils.BaseConstants;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
@Api(value = "Permission Controller", description = "This Controller is responsible for Permissioning.")
public class PermissionController extends AbstractCRUDController<Permission, Long> {

    @Autowired
    public PermissionController(PermissionRepository repository) {
        super(repository);
        this.getFilterFields().add(QueryCfg.builder()
                .field("name")
                .operation(BaseConstants.QueryOperation.LIKE)
                .nextOperation(BaseConstants.QueryOperation.OR)
                .build());

    }


}