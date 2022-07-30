package com.example.tiktaktoe.service;

import com.example.tiktaktoe.model.Match;
import com.example.tiktaktoe.model.Move;
import com.example.tiktaktoe.model.TikTakToeResponse;

import java.util.Optional;

public interface TikTakToeService {
    public Match startNewMatch(Integer boardSize);
    public TikTakToeResponse move(Match match, Move move);

    Optional<Match> getMatchDetails(int matchId);
}
