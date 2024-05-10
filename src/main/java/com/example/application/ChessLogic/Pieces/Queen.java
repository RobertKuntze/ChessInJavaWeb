package com.example.application.ChessLogic.Pieces;

import java.util.HashSet;
import java.util.Set;

import com.example.application.ChessLogic.Position;

public class Queen extends Piece{
    public Queen(boolean isWhite) {
        super(isWhite);
    }

    public String toString() {
        return "Q";
    }

    public Set<Position> getMoves(Position position) {
        Set<Position> moves = new HashSet<Position>();

        for (int file = -1; file <= 1; file++) {
            for (int rank = -1; rank <= 1; rank++) {
                if (file == 0 && rank == 0) {
                    continue;
                }

                for (int i = 1; i < 8; i++) {
                    Position move = new Position(position.rank() + rank * i, position.file() + file * i);
                    if (move.isValid()) moves.add(move);
                }
            }   
        }

        return moves;
    }
}
