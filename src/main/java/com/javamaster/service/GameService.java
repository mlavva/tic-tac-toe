package com.javamaster.service;

import com.javamaster.exception.InvalidParamException;
import com.javamaster.exception.InvalidGameException;

import com.javamaster.exception.NotFoundException;
import com.javamaster.model.Game;
import com.javamaster.model.GameStatus;
import com.javamaster.model.Player;
import com.javamaster.storage.GameStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GameService {
    public final static int BOARD_SIZE = 3;

    public Game createGame(Player player) {
        Game game = new Game();
        game.setBoard(new int[BOARD_SIZE][BOARD_SIZE]);
        game.setGameId(UUID.randomUUID().toString());
        game.setPlayer1(player);
        game.setGameStatus(GameStatus.NEW);
        return game;
    }

    public Game connectToGame(Player player2, String gameId) throws InvalidParamException, InvalidGameException {
        if (!GameStorage.getInstance().getGames().containsKey(gameId)) {
            throw new InvalidParamException("Provided Game Id doesn't exist.");
        }

        Game game = GameStorage.getInstance().getGames().get(gameId);

        if (game.getPlayer2() != null) {
            throw new InvalidGameException("Game is not valid anymore.");
        }

        game.setPlayer2(player2);
        game.setGameStatus(GameStatus.IN_PROGRESS);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    public Game connectToRandomGame(Player player2) throws NotFoundException {
        Game game = GameStorage.getInstance().getGames().values().stream().
                filter(it -> it.getGameStatus().equals(GameStatus.NEW)).findFirst()
                .orElseThrow(() -> new NotFoundException("Game not found in server."));
        game.setPlayer2(player2);
        game.setGameStatus(GameStatus.IN_PROGRESS);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    public Game gamePlay() {

    }
}
