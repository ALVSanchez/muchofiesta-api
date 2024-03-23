package com.example.demo;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.auth.AuthResult;
import com.example.demo.user.User;
import com.example.demo.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/user")
@RestController
@RequiredArgsConstructor
public class ImageController {

    @Autowired
    private final AuthResult authResult;

    @Autowired
    private final UserService userService;

    @Autowired
    private final ImageService imageService;

    @Autowired
    private AuthResult userInfo;

    @Operation(summary = "Upload an image", description = "Returns the id of the image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully uploaded"),
            @ApiResponse(responseCode = "400", description = "The image was too large or not supported")
    })

    @PostMapping("/uploadImage")
    public ResponseEntity<Optional<Integer>> uploadImage(@RequestParam("image") MultipartFile image) {

        // TODO: handle error
        User user = userService.getAuthUser(authResult).get();

        System.out.println(user);

        Optional<Image> newImage = imageService.uploadImage(image, user);
        if (newImage.isPresent()) {
            return ResponseEntity.ok().body(Optional.of(newImage.get().getId()));
        } else {
            return ResponseEntity.badRequest().body(Optional.empty());
        }
    }

    @Operation(summary = "Get an image", description = "Returns image with the given id with JPEG mime-type if successful")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "There is no image with that id"),
            @ApiResponse(responseCode = "403", description = "User is not authorized to fetch this image")
    })

    @GetMapping(value = "/getImage/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String id) {
        System.out.println(id);
        // TODO: handle error
        User user = userService.getAuthUser(authResult).get();

        UserMatchResult match = imageService.userMatches(Integer.parseInt(id), user);
        if (match == UserMatchResult.DoesNotExist) {
            //TODO: Response type + swagger
            return ResponseEntity.status(404).body(null);
        } else if (match == UserMatchResult.Unauthorized) {
            //TODO: Response type + swagger
            return ResponseEntity.status(403).body(null);
        }

        Optional<InputStream> image = imageService.getImageStream(Integer.parseInt(id));
        if (image.isPresent()) {
            try {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new InputStreamResource(image.get()));
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        //TODO: document or debug internal server error
        //TODO: Response type + swagger
        return ResponseEntity.status(404).body(null);
    }

}