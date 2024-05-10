package com.example.application.ChessLogic;

import java.util.Set;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Map;

public record Position(int rank, int file) implements Comparable<Position>{
    public Position(String string) {
        this(string.charAt(1) - '1', string.charAt(0) - 'a');
    }

    public String toString() {
        return (char)('a' + file) + "" + (rank + 1);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Position)) {
            return false;
        }
        Position other = (Position) obj;
        return file == other.file && rank == other.rank;
    }
    
    public int compareTo(Position position) {
        if (rank == position.rank) {
            return file - position.file;
        }
        return rank - position.rank;
    }

    public boolean isValid() {
        return file >= 0 && file < 8 && rank >= 0 && rank < 8;
    }

    public static Map<Position, Set<Position>> groupInDirections(Set<Position> positions, Position start) {
        Map<Position, Set<Position>> groupedPositions = new HashMap<Position, Set<Position>>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                groupedPositions.put(new Position(i, j), new TreeSet<Position>());
            }
        }

        for (Position position : positions) {
            int fileDiff = position.file - start.file;
            int rankDiff = position.rank - start.rank;
            if (fileDiff == 0 && rankDiff == 0) {
                continue;
            }
            int fileDirection = fileDiff == 0 ? 0 : (int) Math.signum(fileDiff);
            int rankDirection = rankDiff == 0 ? 0 : (int) Math.signum(rankDiff);
            Position direction = new Position(rankDirection, fileDirection);
            groupedPositions.get(direction).add(position);
        }

        return groupedPositions;
    }

    public Position add(Position position) {
        return new Position(this.rank() + position.rank(), this.file() + position.file());
    }
}
