package app.qrme.core.controller;

import app.qrme.lib.controller.AbstractAuthController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/security")
public class AdminController extends AbstractAuthController {
    @GetMapping({"/", ""})
    public ModelAndView index() {
        return new ModelAndView("admin/index");
    }
}
