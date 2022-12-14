package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    // Получаем запись из БД по логину
    Optional<Person> findByLogin(String login);
    @Modifying
    @Query(value = "update person set login = ?2, role = ?3 where id =?1", nativeQuery = true)
    void updatePersonById(int id, String login, String role);
}
