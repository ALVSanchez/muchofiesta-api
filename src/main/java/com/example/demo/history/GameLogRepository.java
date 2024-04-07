package com.example.demo.history;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameLogRepository extends JpaRepository<GameLog, Integer> {
    //List<GameLog> findAllOrderByDateDesc(Pageable pageable);
}