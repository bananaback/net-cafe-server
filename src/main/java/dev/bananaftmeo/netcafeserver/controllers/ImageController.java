package dev.bananaftmeo.netcafeserver.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.bananaftmeo.netcafeserver.models.responses.ErrorResponse;
import dev.bananaftmeo.netcafeserver.models.responses.ImageUploadResponse;
import dev.bananaftmeo.netcafeserver.services.IFileService;

@RestController
@RequestMapping("api/image")
public class ImageController {
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private IFileService fileService;

    @PostMapping("/pic")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile multipartFile) {
        logger.info("HIT -/upload | File Name : {}", multipartFile.getOriginalFilename());
        try {
            String downloadLink = fileService.upload(multipartFile);
            return ResponseEntity.ok().body(new ImageUploadResponse("Image uploaded successfully", downloadLink));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Failed to upload image: " + e.getMessage()));
        }
    }

    @PostMapping("/pic/{fileName}")
    public ResponseEntity<?> download(@PathVariable String fileName) {
        logger.info("HIT -/download | File Name : {}", fileName);
        try {
            fileService.download(fileName);
            return ResponseEntity.ok().body("Download image successfully.");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Failed to download image: " + e.getMessage()));
        }
    }
}
