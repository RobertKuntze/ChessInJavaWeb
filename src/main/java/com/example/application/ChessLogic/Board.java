package com.example.application.ChessLogic;

import java.util.HashMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.Set;

import com.example.application.ChessLogic.Pieces.King;
import com.example.application.ChessLogic.Pieces.Knight;
import com.example.application.ChessLogic.Pieces.Pawn;
import com.example.application.ChessLogic.Pieces.Piece;

public class Board {
    private Map<Piece, Position> board;

    public Board(Map<Piece, Position> board) {
        this.board = new HashMap<Piece, Position>(board);
    }

    public Board() {
        this.board = new HashMap<Piece, Position>();
    }

    public void initializeBoard() {
        // Initialize the board with pieces
        String pieces = "RNBQKBNR";
        for (int i = 0; i < 8; i++) {
            board.put(Piece.fromChar(pieces.charAt(i), true), new Position(0, i));
            board.put(Piece.fromChar('P', true), new Position(1, i));
            board.put(Piece.fromChar(pieces.charAt(i), false), new Position(7, i));
            board.put(Piece.fromChar('P', false), new Position(6, i));
        }
    }

    public void movePiece(Position start, Position end) {
        // Move the piece from start to end
        if (getValidMoves(start).contains(end)) {
            Piece piece = getPiece(start);
            if (getPiece(end) != null) {
                board.remove(getPiece(end));
            }
            board.put(piece, end);
        } else {
            throw new IllegalArgumentException("Invalid move");
        }
    }

    public Position getPosition(Piece piece) {
        return board.get(piece);
    }

    public Piece getPiece(Position position) {
        for (Piece p : board.keySet()) {
            if (board.get(p).equals(position)) {
                return p;
            }
        }
        return null;
    }

    public Set<Position> getValidMoves(Position position) {
        Piece piece = getPiece(position);
        if (piece == null) {
            return new TreeSet<Position>();
        }
        Set<Position> moves = piece.getMoves(position);
        if (piece instanceof Knight) {
            for (Position move : new TreeSet<Position>(moves)) {
                if (getPiece(move) != null && getPiece(move).isWhite == piece.isWhite) {
                    moves.remove(move);
                }
            }
            return moves;
        }
        Map<Position, Set<Position>> groupedMoves = Position.groupInDirections(moves, position);
        Set<Position> validMoves = new TreeSet<Position>();
        for (Position direction : groupedMoves.keySet()) {
            Set<Position> directionMoves = groupedMoves.get(direction);
            Position pos = position;
            while (!directionMoves.isEmpty()) {
                Position cur = pos.add(direction);
                if (!cur.isValid()) break;
                if (getPiece(cur) == null) {
                    validMoves.add(cur);
                    directionMoves.remove(cur);
                    pos = cur;
                } else if (getPiece(cur).isWhite != piece.isWhite) {
                    validMoves.add(cur);
                    directionMoves.clear();
                } else {
                    directionMoves.clear();
                }
            }    
        }
        if (piece instanceof Pawn) {
            for (Position move : new TreeSet<Position>(validMoves)) {
                if (getPiece(move) == null && move.file() != position.file()) {
                    validMoves.remove(move);
                } if (getPiece(move) != null && move.file() == position.file()) {
                    validMoves.remove(move);
                }
            }
        }
        return validMoves;
    }

    public boolean isCheck(boolean isWhite) {
        Position kingPosition = null;
        for (Piece piece : board.keySet()) {
            if (piece instanceof King && piece.isWhite == isWhite) {
                kingPosition = board.get(piece);
                break;
            }
        }
        for (Piece piece : board.keySet()) {
            if (piece.isWhite != isWhite) {
                Set<Position> moves = getValidMoves(board.get(piece));
                if (moves.contains(kingPosition)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Set<Position> removeCheckMoves(Set<Position> moves, Position start, boolean isWhite) {
        Set<Position> validMoves = new TreeSet<Position>();
        for (Position move : moves) {
            Board copiedBoard = new Board(board);
            try {
                copiedBoard.movePiece(start, move);
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            }
            if (!copiedBoard.isCheck(isWhite)) {
                validMoves.add(move);
            }
        }
        return validMoves;
    }
}
