package com.aydsii.demo.repository;

import com.aydsii.demo.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IPersonaRepository extends JpaRepository<Persona, Integer> {
    @Query("SELECT p.puntos FROM Persona p WHERE p.id = :id")
    Integer getPuntosById(@Param("id") Integer id);
}