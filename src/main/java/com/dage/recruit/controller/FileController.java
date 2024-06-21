package com.dage.recruit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/{photo}")
    public ResponseEntity<Resource> getPhoto(@PathVariable String photo) {
        try {
            String basePath = System.getProperty("user.dir") + "/recu/photo/";
            Path filePath = Paths.get(basePath, photo + ".jpg");
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .body(resource);
            } else {
                Resource defaultResource = resourceLoader.getResource("classpath:/static/images/photo/default.jpg");
                return ResponseEntity.ok()
                        .body(defaultResource);
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
