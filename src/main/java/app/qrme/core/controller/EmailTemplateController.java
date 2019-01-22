package app.qrme.core.controller;

import app.qrme.core.data.repository.EmailTemplateRepository;
import app.qrme.core.entities.EmailTemplate;
import app.qrme.lib.controller.AbstractCRUDController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email-template")
public class EmailTemplateController extends AbstractCRUDController<EmailTemplate, Long> {
    public EmailTemplateController(EmailTemplateRepository repository) {
        super(repository);
    }
}
