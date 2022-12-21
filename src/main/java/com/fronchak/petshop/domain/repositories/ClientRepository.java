package com.fronchak.petshop.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fronchak.petshop.domain.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	Client findByEmail(String email);
	
	Client findByCpf(String cpf);
}
