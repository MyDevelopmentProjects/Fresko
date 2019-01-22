package app.qrme.lib.springconfig;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:thread.properties")
public class ThreadConfig {
}
