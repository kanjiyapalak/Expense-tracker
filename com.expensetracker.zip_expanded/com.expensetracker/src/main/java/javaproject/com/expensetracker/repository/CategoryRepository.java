package javaproject.com.expensetracker.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import javaproject.com.expensetracker.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

