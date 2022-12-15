package com.fronchak.petshop.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fronchak.petshop.domain.entities.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {

	Color findByName(String name);
	
	Color findByRgb(String rgb);
	
	Color findByHex(String hex);
}
