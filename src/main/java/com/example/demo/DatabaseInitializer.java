package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.example.demo.challenge.Challenge;
import com.example.demo.challenge.ChallengeRespository;
import com.example.demo.challenge.ChallengeService;
import com.example.demo.user.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;

@Component
public class DatabaseInitializer implements CommandLineRunner {

  @Autowired
  ChallengeService challengeService;
  @Autowired
  ChallengeRespository challengeRespository;

  public void initChallenges() {

    if (challengeRespository.count() != 0) {
      return;
    }

    // read json and write to db
    ObjectMapper mapper = new ObjectMapper();
    TypeReference<List<Challenge>> typeReference = new TypeReference<List<Challenge>>() {
    };
    try {
      //InputStream inputStream = TypeReference.class.getResourceAsStream("src/main/resources/static/init/challenges.json");
      File file = ResourceUtils.getFile("classpath:static/init/challenges.json");
      InputStream inputStream = new FileInputStream(file);
      List<Challenge> challenges = mapper.readValue(inputStream, typeReference);
      challengeService.initChallenges(challenges);
      System.out.println("Se inicializaron las pruebas");
    } catch (Exception e) {
      System.out.println("No se pudieron inicializar las pruebas: " + e.getMessage());
    }
  }

  @Override
  public void run(String... args) {

    initChallenges();

  }
}