package com.example.demo.history;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameLogRepository extends JpaRepository<GameLog, Integer> {
    //List<GameLog> findAllOrderByDateDesc(Pageable pageable);
    @Query("SELECT gl from GameLog gl, User u WHERE u.id = :userId AND gl.user.id = u.id")
    Page<GameLog> findAllByUserId(Integer userId, Pageable pageable);

    Optional<GameLog> findFirstByUserIdOrderByStartTimeDesc(Integer userId);
}