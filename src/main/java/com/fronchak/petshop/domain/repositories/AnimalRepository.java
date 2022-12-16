package com.fronchak.petshop.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fronchak.petshop.domain.entities.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

}
