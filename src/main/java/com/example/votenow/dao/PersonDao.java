package com.example.votenow.dao;

import com.example.votenow.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonDao extends JpaRepository<Person, Long> {
}

