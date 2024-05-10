package com.example.application.ChessLogic.Pieces;

import java.util.TreeSet;
import java.util.Set;

import com.example.application.ChessLogic.Position;

public class Knight extends Piece{
    public Knight(boolean isWhite) {
        super(isWhite);
    }

    public String toString() {
        return "N";
    }

    public Set<Position> getMoves(Position position) {
        Set<Position> moves = new TreeSet<Position>();
        for (int file = -2; file<=2; file++) {
            for (int rank = -2; rank<=2; rank++) {
                if (Math.abs(file) + Math.abs(rank) != 3) {
                    continue;
                }
                Position move = new Position(position.rank() + rank, position.file() + file);
                if (move.isValid()) moves.add(move);
            }
        }
        return moves;
    }
}
