package botdealer.website.controller;

import botdealer.website.model.ModelUpDown;
import botdealer.website.storage.StorageFileNotFoundException;
import botdealer.website.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UpDown {
    private final StorageService storageService;
    private String filename;

    @Autowired
    public UpDown(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload-file")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {

        storageService.store(file);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/download-file")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@RequestBody ModelUpDown modelUpDown) {

        Resource file = storageService.loadAsResource(modelUpDown.getFilename() + ".xlsx");
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
