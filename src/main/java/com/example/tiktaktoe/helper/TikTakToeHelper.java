package com.example.tiktaktoe.helper;

import java.util.Optional;

public class TikTakToeHelper {

    public static final String EMPTY_STRING = "";

    public static Boolean checkColumnConditionForMove(String[][] board, int rowPosition, String state) {
        for(int i = 0; i < board.length; i++){
            if(!Optional.ofNullable(board[rowPosition][i]).orElse(EMPTY_STRING).equals(state))
                break;
            if(i == board.length-1){
                return true;
            }
        }
        return false;
    }

    public static Boolean checkRowConditionForMove(String[][] board, int columnPosition, String state) {
        for(int i = 0; i < board.length; i++){
            if(!Optional.ofNullable(board[i][columnPosition]).orElse(EMPTY_STRING).equals(state))
                break;
            if(i == board.length-1){
                return true;
            }
        }
        return false;
    }

    public static Boolean checkDiagonalConditionForMove(String[][] board, int rowPosition, int columnPosition, String state) {
        if(rowPosition == columnPosition){
            for(int i = 0; i < board.length; i++){
                if(!Optional.ofNullable(board[i][i]).orElse(EMPTY_STRING).equals(state))
                    break;
                if(i == board.length-1){
                    return true;
                }
            }
        }
        return false;
    }

    public static Boolean checkAntiDiagonalConditionForMove(String[][] board, int rowPosition, int columnPosition, String state) {
        if(rowPosition + columnPosition == board.length - 1){
            for(int i = 0; i < board.length; i++){
                if(!Optional.ofNullable(board[i][(board.length-1)-i]).orElse(EMPTY_STRING).equals(state))
                    break;
                if(i == board.length-1){
                    return true;
                }
            }
        }
        return false;
    }
}
