package com.example.tiktaktoe.repository;

import com.example.tiktaktoe.model.Match;
import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, Integer> {
}
