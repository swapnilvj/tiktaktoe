package com.example.tiktaktoe.service.impl;

import com.example.tiktaktoe.model.Match;
import com.example.tiktaktoe.model.Move;
import com.example.tiktaktoe.model.TikTakToeResponse;
import com.example.tiktaktoe.repository.MatchRepository;
import com.example.tiktaktoe.service.TikTakToeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.tiktaktoe.helper.TikTakToeHelper.*;

@Service
public class TikTakToeServiceImpl implements TikTakToeService {

    private final static Logger logger = LoggerFactory.getLogger(TikTakToeServiceImpl.class);

    private MatchRepository matchRepository;
    public static final String MATCH_IN_PROGRESS = "Match In Progress";
    public static final String MATCH_WIN = "%s Wins !";
    public static final String MATCH_DRAWN = "Match Drawn !";

    @Autowired
    public TikTakToeServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public Match startNewMatch(Integer boardSize) {
        Match matchEntity = initMatch(boardSize);
        logger.info("Match Initiated.");
        return matchRepository.save(matchEntity);
    }

    private Match initMatch(Integer boardSize) {
        Match matchEntity = new Match();
        matchEntity.setStartTime(LocalDateTime.now());
        matchEntity.setBoardSize(boardSize);
        matchEntity.setBoard(new String[boardSize][boardSize]);
        matchEntity.setActive(true);
        return matchEntity;
    }

    @Override
    public TikTakToeResponse move(Match match, Move move){
        TikTakToeResponse response = new TikTakToeResponse();
        String result = MATCH_IN_PROGRESS;
        response.setMatchId(match.getMatchId());

        int rowPosition = move.getRowPosition();
        int columnPosition = move.getColumnPosition();
        String state = move.getState();

        String[][] board = match.getBoard();
        if(board[rowPosition][columnPosition] == null){
            board[rowPosition][columnPosition] = state;
            match.setBoard(board);
        }

        Integer moveCount = match.getMoveCount();
        match.setMoveCount(++moveCount);

        //check end conditions
        if(checkColumnConditionForMove(board, rowPosition, state) ||
                checkRowConditionForMove(board, columnPosition, state) ||
                checkDiagonalConditionForMove(board, rowPosition, columnPosition, state) ||
                checkAntiDiagonalConditionForMove(board, rowPosition, columnPosition, state)
        ) {
            result = String.format(MATCH_WIN, state);
            match.setEndTimeTime(LocalDateTime.now());
            match.setActive(false);
        }

        //check draw
        if(moveCount == (Math.pow(board.length, 2) - 1)){
            result = String.format(MATCH_DRAWN);
            match.setEndTimeTime(LocalDateTime.now());
            match.setActive(false);
        }
        match.setResult(result);

        // protection against concurrent access
        synchronized(matchRepository) {
            matchRepository.save(match);
        }
        response.setResult(result);
        response.setActive(match.isActive());

        return response;
    }

    @Override
    public Optional<Match> getMatchDetails(int matchId) {

        return matchRepository.findById(matchId);
    }
}
