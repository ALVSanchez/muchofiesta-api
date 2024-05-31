package com.example.demo.history;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameLogResponse {
  private Integer gameId;
  private List<String> players;
  private Date startTime;
  private Date endTime;
  private Optional<Integer> startPhotoId;
  private Optional<Integer> endPhotoId;

  public static GameLogResponse fromGameLog(GameLog gameLog) {
    // Obtener id de las fotos
    Optional<Integer> startPhotoId = Optional.ofNullable(gameLog.getStartPhoto()).map(img -> img.getId());
    Optional<Integer> endPhotoId = Optional.ofNullable(gameLog.getEndPhoto()).map(img -> img.getId());

    GameLogResponse response = GameLogResponse.builder()
        .gameId(gameLog.getId())
        .players(gameLog.getPlayers())
        .startTime(gameLog.getStartTime())
        .endTime(gameLog.getEndTime())
        .startPhotoId(startPhotoId)
        .endPhotoId(endPhotoId)
        .build();

    return response;
  }
}
