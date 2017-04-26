package com.warumono.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.warumono.entities.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long>
{
	Optional<AppUser> findOneByUsername(String username);
}
