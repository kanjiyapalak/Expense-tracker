package javaproject.com.expensetracker.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import javaproject.com.expensetracker.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}

