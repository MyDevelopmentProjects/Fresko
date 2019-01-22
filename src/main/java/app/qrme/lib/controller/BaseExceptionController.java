package app.qrme.lib.controller;

import app.qrme.lib.data.entity.BaseException;
import app.qrme.lib.data.repo.BaseExceptionRepository;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base-exception")
@Api(value = "Base Exception Controller", description = "This Controller is responsible for BaseException handling.")
public class BaseExceptionController extends AbstractCRUDController<BaseException, Long>{

    public BaseExceptionController(BaseExceptionRepository repository) {
        super(repository);
    }

}