package com.example.demo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Serie;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Integer> {
	public Serie findByName(String name);
}
