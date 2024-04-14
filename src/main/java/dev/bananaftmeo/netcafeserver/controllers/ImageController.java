package dev.bananaftmeo.netcafeserver.controllers;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Blob.BlobSourceOption;

import dev.bananaftmeo.netcafeserver.models.responses.ErrorResponse;
import dev.bananaftmeo.netcafeserver.models.responses.ImageDeleteResponse;
import dev.bananaftmeo.netcafeserver.models.responses.ImageNamesResponse;
import dev.bananaftmeo.netcafeserver.models.responses.ImageResponse;
import dev.bananaftmeo.netcafeserver.models.responses.ImageUploadResponse;
import dev.bananaftmeo.netcafeserver.services.fileservices.IFileService;

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
            Blob blob = fileService.download(fileName);
            return ResponseEntity.ok().body(new ImageResponse(blob.getContent(BlobSourceOption.generationMatch())));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Failed to download image: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllImageLinks(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            List<String> pagedImageNames = fileService.getAllImageNames(page, pageSize);
            int size = fileService.getAllImageNames(0, 1000).size();
            ImageNamesResponse response = new ImageNamesResponse();
            response.setImageNames(pagedImageNames);
            response.setSize(size);

            return ResponseEntity.ok().body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve image links: " + e.getMessage());
        }
    }

    @DeleteMapping("/pic/{fileName}")
    public ResponseEntity<?> deleteImage(@PathVariable String fileName) {
        logger.info("HIT - /delete | File Name : {}", fileName);
        try {
            boolean deleted = fileService.delete(fileName);
            if (deleted) {
                return ResponseEntity.ok().body(new ImageDeleteResponse("Image deleted successfully"));
            } else {
                return ResponseEntity.badRequest()
                        .body(new ImageDeleteResponse("Failed to delete image. Image not found."));
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ImageDeleteResponse("Failed to delete image: " + e.getMessage()));
        }
    }
}
