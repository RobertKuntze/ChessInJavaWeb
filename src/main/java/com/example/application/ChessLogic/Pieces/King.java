package com.example.application.ChessLogic.Pieces;

import java.util.TreeSet;
import java.util.Set;

import com.example.application.ChessLogic.Position;

public class King extends Piece {
    public King(boolean isWhite) {
        super(isWhite);
    }

    public String toString() {
        return "K";
    }

    public Set<Position> getMoves(Position position) {
        Set<Position> moves = new TreeSet<Position>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                Position move = new Position(position.rank() + i, position.file() + j);
                if (move.isValid()) moves.add(move);
            }
        }

        return moves;
    }
}
