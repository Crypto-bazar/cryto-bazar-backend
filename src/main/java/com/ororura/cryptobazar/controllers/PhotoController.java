package com.ororura.cryptobazar.controllers;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("photo")
public class PhotoController {
    private static final String UPLOAD_DIR = "src/main/resources/uploads/";

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getPhoto(@PathVariable String filename) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR).resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new IOException("Файл не найден: " + filename);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/png") // или image/png, если у вас PNG
                .body(resource);
    }
}
