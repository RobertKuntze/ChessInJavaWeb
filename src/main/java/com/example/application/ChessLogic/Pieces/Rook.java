package com.example.application.ChessLogic.Pieces;

import java.util.TreeSet;
import java.util.Set;

import com.example.application.ChessLogic.Position;

public class Rook extends Piece {
    public Rook(boolean isWhite) {
        super(isWhite);
    }

    public String toString() {
        return "R";
    }

    public Set<Position> getMoves(Position position) {
        Set<Position> moves = new TreeSet<Position>();
        for (int i = 1; i < 8; i++) {
            Position move = new Position(position.rank(), position.file() + i);
            if (move.isValid()) moves.add(move);
            move = new Position(position.rank(), position.file() - i);
            if (move.isValid()) moves.add(move);
            move = new Position(position.rank() + i, position.file());
            if (move.isValid()) moves.add(move);
            move = new Position(position.rank() - i, position.file());
            if (move.isValid()) moves.add(move);
        }
        return moves;
    }
}
