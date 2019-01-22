package app.qrme.core.configuration;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by MJaniko on 5/21/2017.
 */
@Configuration
public class SessionConfiguration {

    private class SessionListener implements  HttpSessionListener{

        @Override
        public void sessionCreated(HttpSessionEvent event) {
            event.getSession().setMaxInactiveInterval(0); // 5 Minutes / 0 disabled
        }

        @Override
        public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

        }

    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener() {
        return new ServletListenerRegistrationBean<>(new SessionListener());
    }
}
