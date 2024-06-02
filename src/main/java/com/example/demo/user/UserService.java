package com.example.demo.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Image;
import com.example.demo.ImageRepository;
import com.example.demo.ImageService;
import com.example.demo.auth.AuthData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ImageService imageService;
    @Autowired
    private final ImageRepository imageRepository;

    public User getAuthUser(AuthData authResult) {
        Optional<User> user = userRepository.findByEmail(authResult.getEmail());
        if(user.isEmpty()){
            // No debería poder ocurrir
            //TODO: Manejar error
            throw new Error();
        }
        return user.get();
    }

    public Optional<User> getAuthUserOptional(AuthData authResult) {
        return userRepository.findByEmail(authResult.getEmail());
    }

    public Integer postProfilePic(User user, MultipartFile profilePic){

        Image image = null;
        if(profilePic != null) {
            image = imageService.uploadImageOpt(profilePic, user).orElse(null);
        }

        if(image != null ) {
            if(user.getProfilePic() != null){
                imageRepository.delete(user.getProfilePic());
            }
            user.setProfilePic(image);
            userRepository.save(user);
            return image.getId();
        }

        return null;
    }

}
