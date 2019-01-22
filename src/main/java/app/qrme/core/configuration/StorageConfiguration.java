package app.qrme.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by user on 5/29/17.
 */
@ConfigurationProperties("storage")
public class StorageConfiguration {

    /**
     * Folder location for storing files
     */
    private String location = "uploadFiles";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}