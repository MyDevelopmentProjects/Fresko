package app.qrme.lib.springconfig;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:library.properties")
@Component
public class LibraryConfig {

}
