package com.example.demo.history;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.Image;
import com.example.demo.user.User;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: do something about the double "User" relation in Image and GameLog
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gamelog")
public class GameLog {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    
    @Basic
    @Column(name = "startDate")
    private Date startDate;
    @Column(name = "endDate")
    private Date endDate;

    @ElementCollection
    @Column(name = "players")
    private List<String> players;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "initialPhoto")
    private Optional<Image> initialPhoto;

    @ManyToOne
    @JoinColumn(name = "finalPhoto")
    private Optional<Image> finalPhoto;
}
