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

    public List<GameLogResponse> getHistory(User user, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "startTime");
        Page<GameLog> page = gameLogRepository.findAllByUserId(user.getId(), pageable);
        return page.map(GameLogResponse::fromGameLog).getContent();
        
    }
    

    public GameLog postGame(User user, PostGameBody body, Optional<MultipartFile> startPhotoFile, Optional<MultipartFile> endPhotoFile) {
        Optional<Image> startImageOpt;
        if(startPhotoFile.isPresent()){
            startImageOpt = imageService.uploadImageOpt(startPhotoFile.get(), user);
        } else {
            startImageOpt = Optional.empty();
        }

        Optional<Image> endImageOpt;
        if(endPhotoFile.isPresent()){
            endImageOpt = imageService.uploadImageOpt(endPhotoFile.get(), user);
        } else {
            endImageOpt = Optional.empty();
        }

        GameLog gameLog = GameLog.builder()
        .user(user)
        .players(body.getPlayers())
        .startTime(body.getStartTime())
        .endTime(body.getEndTime())
        .startPhoto(startImageOpt.orElse(null))
        .endPhoto(endImageOpt.orElse(null))
        .build();
        
        gameLogRepository.save(gameLog);
        return gameLog;
    }
}
