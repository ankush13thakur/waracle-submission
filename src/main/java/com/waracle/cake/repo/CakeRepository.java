package com.waracle.cake.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.waracle.cake.entity.Cake;

public interface CakeRepository extends JpaRepository<Cake, Integer> {

	Page<Cake> findAll(Pageable page);
}
