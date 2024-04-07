package com.example.demo.history;

import java.util.Date;

import com.example.demo.Image;
import com.example.demo.user.User;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: do something about the double "User" relation in Image and GameLog
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GameLog {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    
    @Basic
    Date date;

    @ManyToOne
    User user;

    @ManyToOne
    Image initialPhoto;

    @ManyToOne
    Image finalPhoto;
}
