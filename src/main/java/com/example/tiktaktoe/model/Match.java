package com.example.tiktaktoe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "Match")
@Entity
public class Match {
    @Id
    @GeneratedValue
    private int matchId;

    private int boardSize;

    private String[][] board;

    private int moveCount;

    private String result;

    private LocalDateTime startTime;

    private LocalDateTime EndTimeTime;

    private boolean isActive;

    public Match() {
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public String[][] getBoard() {
        return board;
    }


    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTimeTime() {
        return EndTimeTime;
    }

    public void setEndTimeTime(LocalDateTime endTimeTime) {
        EndTimeTime = endTimeTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    //private
}
