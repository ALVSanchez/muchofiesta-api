package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.user.User;

import lombok.RequiredArgsConstructor;

import java.awt.Color;
import java.awt.image.*;

@Service
@RequiredArgsConstructor
public class ImageService {


    private static final String IMAGE_DIR = "src/main/resources/static/images";
    @Autowired
    private ImageRepository imageRepository;

    public Optional<Image> uploadImage(MultipartFile imageFile, User user) {
        try {
            InputStream imageInputStream = imageFile.getInputStream();
            BufferedImage bufferedImage = ImageIO.read(imageInputStream);
            BufferedImage convertedImage = new BufferedImage(
                    bufferedImage.getWidth(),
                    bufferedImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            convertedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
            File file = new File(IMAGE_DIR, uniqueFileName() + ".jpg"); // TODO
            FileOutputStream fileOut = new FileOutputStream(file);
            ImageIO.write(convertedImage, "jpg", fileOut);

            Image newImage = new Image(null, file.getName(), user);
            imageRepository.save(newImage);
            return Optional.of(newImage);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public UserMatchResult userMatches(Integer imageId, User user){
        Optional<Image> image = imageRepository.findById(imageId);
        if(image.isPresent()){
            if(image.get().getUser().getId() == user.getId()) {
                return UserMatchResult.Authorized;
            } else {
                return UserMatchResult.Unauthorized;
            }
        }
        return UserMatchResult.DoesNotExist;
    }

    public Optional<InputStream> getImageStream(Integer id){
        Optional<Image> image = imageRepository.findById(id);
        if(image.isPresent()){
            File file = new File(IMAGE_DIR, image.get().getFileName());
            try{
                return Optional.of(new FileInputStream(file));
            } catch(Exception e) {
                //TODO: Handle exception
            }
        }
        return Optional.empty();
    }

    private String uniqueFileName() {
        return UUID.randomUUID().toString();
        //return Integer.toString(new Random(Integer.MAX_VALUE).nextInt());
    }
}
