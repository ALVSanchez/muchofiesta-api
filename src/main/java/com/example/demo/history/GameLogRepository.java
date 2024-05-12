package com.example.demo.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameLogRepository extends JpaRepository<GameLog, Integer> {
    //List<GameLog> findAllOrderByDateDesc(Pageable pageable);
    @Query("SELECT * from gamelog gl, user u WHERE u.id = :userId AND gamelog.user = u.id")
    Page<GameLog> findAllByUserId(Integer userId, Pageable pageable);
}