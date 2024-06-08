package com.example.demo.history;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
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

    private final int MAX_SUGGESTED_LOGS = 20;
    private final int MAX_SUGGESTED_PLAYERS = 10;

    public List<String> getSuggestedPlayers(User user) {
        //HashSet<String> suggestedPlayers = new HashSet<>();

        Pageable pageable = PageRequest.of(0, MAX_SUGGESTED_LOGS, Sort.Direction.DESC, "startTime");

        List<String> suggestedPlayers = gameLogRepository.findAllByUserId(user.getId(), pageable)
        .stream() // Get stream of games
        .flatMap(log -> log.getPlayers().stream()) // Map into a stream of players
        .distinct() // Remove duplicates
        .filter(player -> !player.equals(user.getName())) // Filter out the user's name
        .limit(MAX_SUGGESTED_PLAYERS)
        .toList();

        
        return suggestedPlayers;

    }

    public List<GameLogResponse> getHistory(User user, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "startTime");
        Page<GameLog> page = gameLogRepository.findAllByUserId(user.getId(), pageable);
        return page.map(GameLogResponse::fromGameLog).getContent();
        
    }

    public GameLog startGame(User user, List<String> players, Date startTime, Optional<MultipartFile> startPhotoFile) {
        
        Optional<Image> startImageOpt;
        if(startPhotoFile.isPresent()){
            startImageOpt = imageService.uploadImageOpt(startPhotoFile.get(), user);
        } else {
            startImageOpt = Optional.empty();
        }

        GameLog gameLog = GameLog.builder()
        .user(user)
        .players(players)
        .startTime(startTime)
        .startPhoto(startImageOpt.orElse(null))
        .build();
        
        gameLogRepository.save(gameLog);
        return gameLog;
    }
    
    public GameLog endGame(User user, Date endTime, Optional<MultipartFile> endPhotoFile) {
        
        Optional<Image> endImageOpt;
        if(endPhotoFile.isPresent()){
            endImageOpt = imageService.uploadImageOpt(endPhotoFile.get(), user);
        } else {
            endImageOpt = Optional.empty();
        }

        Optional<GameLog> latestGameOpt = gameLogRepository.findFirstByUserIdOrderByStartTimeDesc(user.getId());

        if(latestGameOpt.isEmpty()){
            // TODO: Manejar error (El usuario nunca ha comenzado una partida)
            throw new Error();
        } else if(latestGameOpt.get().getEndTime() != null){
            // TODO: Manejar error (No hay una partida en progreso)
            throw new Error();
        }

        GameLog latestGame = latestGameOpt.get();

        latestGame.setEndTime(endTime);
        latestGame.setEndPhoto(endImageOpt.orElse(null));
        gameLogRepository.save(latestGame);
        return latestGame;
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
