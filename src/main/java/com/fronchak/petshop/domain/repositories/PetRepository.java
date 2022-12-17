package com.fronchak.petshop.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fronchak.petshop.domain.entities.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {}
