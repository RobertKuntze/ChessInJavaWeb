package com.example.application.ChessLogic.Pieces;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.List;
import java.util.Set;

import com.example.application.ChessLogic.Position;

public class Pawn extends Piece{
    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    public String toString() {
        return "P";
    }

    public Set<Position> getMoves(Position position) {
        Set<Position> moves = new TreeSet<Position>();
        
        int direction = isWhite ? 1 : -1;
        int range = (isWhite && position.rank() == 1) || (!isWhite && position.rank() == 6) ? 2 : 1;

        for (int i = 1; i <= range; i++) {
            Position move = new Position(position.rank() + direction * i, position.file());
            if (move.isValid()) moves.add(move);
        }

        Position move = new Position(position.rank() + direction, position.file() + 1);
        if (move.isValid()) moves.add(move);
        move = new Position(position.rank() + direction, position.file() - 1);
        if (move.isValid()) moves.add(move);

        return moves;
    }
}
