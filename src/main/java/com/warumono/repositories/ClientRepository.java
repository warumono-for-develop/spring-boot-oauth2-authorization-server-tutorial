package com.warumono.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.warumono.entities.AppClient;

public interface ClientRepository extends JpaRepository<AppClient, String>
{
	Optional<AppClient> findOneById(String id);
}
