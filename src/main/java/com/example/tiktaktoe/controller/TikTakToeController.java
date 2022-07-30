package com.example.tiktaktoe.controller;

import com.example.tiktaktoe.model.Match;
import com.example.tiktaktoe.model.Move;
import com.example.tiktaktoe.model.TikTakToeRequest;
import com.example.tiktaktoe.model.TikTakToeResponse;
import com.example.tiktaktoe.service.impl.TikTakToeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class TikTakToeController {

    private final static Logger logger = LoggerFactory.getLogger(TikTakToeController.class);

    private TikTakToeServiceImpl service;

    @Autowired
    public TikTakToeController(TikTakToeServiceImpl service) {
        this.service = service;
    }

    @ResponseBody
    @GetMapping("/tiktaktoe/match")
    public ResponseEntity<Match> getMatch(@RequestParam int matchId) {
        try {
            Optional<Match> matchById = service.getMatchDetails(matchId);
            return new ResponseEntity<>(matchById.get(), HttpStatus.OK);
        } catch (NoSuchElementException noSuchElementException) {
            logger.error(noSuchElementException.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @PostMapping("/tiktaktoe/match")
    public ResponseEntity<TikTakToeResponse> newMatch(@RequestBody TikTakToeRequest tikTakToeRequest) {
        ResponseEntity<TikTakToeResponse> responseEntity;
        Integer boardSize = tikTakToeRequest.getBoardSize();
        try {
            Match match = service.startNewMatch(boardSize);

            responseEntity = new ResponseEntity<>(new TikTakToeResponse(), HttpStatus.OK);
            responseEntity.getBody().setMatchId(match.getMatchId());
            responseEntity.getBody().setActive(match.isActive());
            return responseEntity;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @PostMapping("/tiktaktoe/move")
    public ResponseEntity<TikTakToeResponse> move(@RequestBody Move move) {
        TikTakToeResponse response = new TikTakToeResponse();
        int matchId = move.getMatchId();
        Optional<Match> matchById = service.getMatchDetails(matchId);

        if(!matchById.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!matchById.get().isActive()) {
            response.setMatchId(matchById.get().getMatchId());
            response.setResult(matchById.get().getResult());
            response.setActive(matchById.get().isActive());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        try {
            response = service.move(matchById.get(), move);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
