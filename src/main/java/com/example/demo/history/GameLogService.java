package com.example.demo.history;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameLogService {

    @Autowired
    private final GameLogRepository gameLogRepository;

    public List<GameLog> getHistory(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "date");
        Page<GameLog> page = gameLogRepository.findAll(pageable);
        return page.getContent();
    }
    
}
