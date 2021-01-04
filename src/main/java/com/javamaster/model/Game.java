package com.javamaster.model;

import lombok.Data;

@Data
public class Game {
    private String gameId;
    Player player1;
    Player player2;
    private GameStatus gameStatus;
    private int[][] board;
    private TicTacToe winner;
}
