package com.BrewMate.BrewMate.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ImageController {

    @GetMapping("/images/coffees/{imageName}")
    public ResponseEntity<Resource> getCoffeeImage(@PathVariable String imageName) {
        Resource image = new ClassPathResource("static/images/coffees/" + imageName);

        if (!image.exists()) {
            return ResponseEntity.notFound().build();
        }

//        Differing media type support.
//        So far webp, jpg/eg and png.
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        if (imageName.endsWith(".webp")) {
            mediaType = MediaType.parseMediaType("image/webp");
        } else if (imageName.endsWith(".jpg") || imageName.endsWith(".jpeg")) {
            mediaType = MediaType.IMAGE_JPEG;
        } else if (imageName.endsWith(".png")) {
            mediaType = MediaType.IMAGE_PNG;
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(image);
    }

}
