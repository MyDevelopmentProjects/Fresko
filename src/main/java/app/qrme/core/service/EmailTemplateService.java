package app.qrme.core.service;

import app.qrme.core.data.repository.EmailTemplateRepository;
import app.qrme.core.entities.EmailTemplate;
import app.qrme.core.enums.EOrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailTemplateService {

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    private EmailTemplate findByOrderStateAndReplace(EOrderState state){
        Optional<EmailTemplate> emailTemplate = emailTemplateRepository.findByOrderState(state);
        if(emailTemplate.isPresent()){
            // TODO JANIKO
            // find a way to replace all variables
            // variables should be predifined
            return emailTemplate.get();
        }
        return null;
    }
}
