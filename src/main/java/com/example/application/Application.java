package com.example.application;

import com.example.application.ChessLogic.Board;
import com.example.application.ChessLogic.Position;
import com.example.application.ChessLogic.Pieces.*;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "mytodo")
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        // Map<Piece, Position> boardInit = new HashMap<Piece, Position>();
        // boardInit.put(new King(false), new Position("e8"));
        // boardInit.put(new Bishop(true), new Position("b5"));
        // boardInit.put(new Pawn(false), new Position("c7"));

        // Board board = new Board(boardInit);

        // System.out.println(board.isCheck(false));
        // Set<Position> moves = board.getValidMoves(new Position("c7"));
        // System.out.println(moves);
        // System.out.println(board.removeCheckMoves(moves, new Position("c7"), false));
    }

}
