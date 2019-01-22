package app.qrme;

import app.qrme.core.configuration.StorageConfiguration;
import app.qrme.core.service.StorageService;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletException;
import javax.sql.DataSource;

@EnableConfigurationProperties(StorageConfiguration.class)
@SpringBootApplication(exclude = {
        ErrorMvcAutoConfiguration.class
},
        scanBasePackages = "app.qrme.*"
)
public class Application {

    @Autowired
    DataSource dataSource;

    @Autowired
    private StorageService serv;

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            //storageService.deleteAll(); // Delete all files on startup
            //storageService.init();
        };
    }

    @Bean
    public ServletWebServerFactory factory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                Context root = null;
                try {
                    root = tomcat.addWebapp("/uploads", serv.getRootLocation().toAbsolutePath().toString());
                    root.setAllowCasualMultipartParsing(true);
                } catch (ServletException e) {
                    //e.printStackTrace();
                }
                return super.getTomcatWebServer(tomcat);
            }
        };
    }
}
