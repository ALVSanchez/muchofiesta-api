package com.example.demo.challenge;

import java.util.Map;

import jakarta.persistence.Basic;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Challenge {

    public enum Category {
        Simple
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ElementCollection
    private Map<String, String> data;

}