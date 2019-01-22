package app.qrme.core.controller;

import app.qrme.core.data.dto.UploadFileDTO;
import app.qrme.core.data.dto.UploadSingleFileDTO;
import app.qrme.core.service.StorageService;
import app.qrme.core.utils.MGLIOUtils;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by MJaniko on 5/2/2017.
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private StorageService storageService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<String> listUploadedFiles() {
        return storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(UploadController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadMultipleFileHandler(@RequestParam("file") MultipartFile[] files
    ) {
        if (files.length == 0) {
            return ResponseEntity.badRequest().body("File size is null or empty.");
        }
        if (!MGLIOUtils.limitFileSize(files)) {
            return ResponseEntity.badRequest().body("Wrong file size.");
        }

        List<UploadFileDTO> fileList = new ArrayList<>();
        for (MultipartFile file : files) {
            fileList.add(
                    UploadFileDTO.builder().name(storageService.storeEncoded(file)).type("USER_IMG").build()
            );
        }

        return ResponseEntity.ok(fileList);
    }

    @RequestMapping(value = "/single", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadSingleFile(@RequestParam("file") MultipartFile file) {
        if (file == null) {
            return ResponseEntity.badRequest().body("File size is null or empty.");
        }
        return ResponseEntity.ok(UploadSingleFileDTO
                .builder()
                .filelink("/uploads/" + storageService.storeEncoded(file))
                .filename(file.getOriginalFilename())
                .build());
    }

}
