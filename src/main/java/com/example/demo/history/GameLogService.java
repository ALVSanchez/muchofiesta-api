package com.example.demo.history;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Image;
import com.example.demo.ImageService;
import com.example.demo.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameLogService {

    @Autowired
    private final GameLogRepository gameLogRepository;

    @Autowired
    private final ImageService imageService;

    public List<GameLog> getHistory(User user, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "date");
        Page<GameLog> page = gameLogRepository.findAllByUserId(user.getId(), pageable);
        return page.getContent();
    }
    

    public GameLog postGame(User user, PostGameBody body, Optional<MultipartFile> initialPhotoFile, Optional<MultipartFile> finalPhotoFile) {
        Optional<Image> initialImageOpt;
        if(initialPhotoFile.isPresent()){
            initialImageOpt = imageService.uploadImageOpt(initialPhotoFile.get(), user);
        } else {
            initialImageOpt = Optional.empty();
        }

        Optional<Image> finalImageOpt;
        if(finalPhotoFile.isPresent()){
            finalImageOpt = imageService.uploadImageOpt(finalPhotoFile.get(), user);
        } else {
            finalImageOpt = Optional.empty();
        }

        GameLog gameLog = GameLog.builder()
        .user(user)
        .players(body.getPlayers())
        .startDate(body.getFechaInicio())
        .endDate(body.getFechaFin())
        .initialPhoto(initialImageOpt)
        .finalPhoto(finalImageOpt)
        .build();
        
        gameLogRepository.save(gameLog);
        return gameLog;
    }
}
