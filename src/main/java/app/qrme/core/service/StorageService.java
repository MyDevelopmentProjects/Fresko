package app.qrme.core.service;

import app.qrme.core.configuration.StorageConfiguration;
import app.qrme.core.utils.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by user on 5/29/17.
 */
@Service
public class StorageService {

    private final Path rootLocation;

    @Autowired
    public StorageService(StorageConfiguration properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    public Path getRootLocation() {
        return rootLocation;
    }

    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    public String storeEncoded(MultipartFile file) {
        try {

            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + file.getOriginalFilename());
            }

            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

            String dirName = rootLocation + File.separator + sdf.format(new Date());

            File directory = new File(dirName);
            if (!directory.exists()) {
                directory.mkdir();
            }

            int beginIndex = file.getOriginalFilename().lastIndexOf(".");
            String fileExtension = file.getOriginalFilename().substring(beginIndex + 1);
            String fileName = GeneralUtil.encodeMD5(System.currentTimeMillis() + GeneralUtil.generateString(11)) + "." +  fileExtension;
            String concatenated = dirName + File.separator + fileName;
            Files.copy(file.getInputStream(), Paths.get(concatenated));
            return concatenated.replaceAll(rootLocation.toString() + File.separator, "");
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read stored files", e);
        }
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init() {
        try {
            if (!Files.exists(rootLocation))
                Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }
}
