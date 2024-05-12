package com.example.demo.history;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Basic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostGameBody {
    
    @Basic
    private List<String> players;
    private Date startTime;
    private Date endTime;
    
}
