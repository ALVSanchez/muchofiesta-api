package com.example.demo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //@Query(value = "SELECT * FROM user WHERE username=?", nativeQuery = true)
    //public User findByUsername(String username);

    public List<User> findByName(String name);
    //public Optional<User> findByUsernameAndPassword(String username, String password);
    public Optional<User> findByEmail(String email);
    
}
