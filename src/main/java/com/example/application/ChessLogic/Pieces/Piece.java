package com.example.application.ChessLogic.Pieces;

import java.util.Set;

import com.example.application.ChessLogic.Position;

public abstract class Piece {
    public final boolean isWhite;
    
    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public static Piece fromChar(char type, boolean isWhite) {
        switch (type) {
            case 'P':
                return new Pawn(isWhite);
            case 'K':
                return new King(isWhite);
            case 'N':
                return new Knight(isWhite);
            case 'Q':
                return new Queen(isWhite);
            case 'B':
                return new Bishop(isWhite);
            case 'R':
                return new Rook(isWhite);
            default:
                return null;
        }
    }

    public abstract Set<Position> getMoves(Position position);
}
