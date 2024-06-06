package com.example.demo.challenge;

import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Challenge
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "challenge")
public class Challenge {

    public enum Category {
        Simple,
        Timer
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @ElementCollection
    @Column(name = "data")
    private Map<String, String> data;
}