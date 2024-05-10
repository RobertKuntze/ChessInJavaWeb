package com.example.application.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.shared.Registration;
import com.example.application.ButtonWithListener;
import com.example.application.ChessLogic.*;
import com.example.application.ChessLogic.Pieces.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@PageTitle("Game")
@Route(value = "/game")
@RouteAlias(value = "game")
public class MainView extends VerticalLayout {

    private final Map<Position, ButtonWithListener> buttonBoard = new HashMap<>();
    private final Board board = new Board();
    private boolean isWhiteTurn = true;

    public MainView() {
        VerticalLayout files = new VerticalLayout();
        files.setSpacing(false);
        for (int i = 0; i < 8; i++) {
            files.add(new HorizontalLayout());
        }
        List<Component> ranks = files.getChildren().toList();
        for (Component space : ranks) {
            HorizontalLayout rank = (HorizontalLayout) space;
            rank.setSpacing(false);
            rank.setHeight("70px");
            for (int i = 0; i < 8; i++) {
                Button button = new Button();
                Registration clickListener = button.addClickListener(click -> {
                    selectPiece(button);
                });
                button.setHeight("70px");
                button.setWidth("70px");
                rank.add(button);

                Position position = new Position(7 - ranks.indexOf(rank), i);
                buttonBoard.put(position, new ButtonWithListener(button, clickListener));
            }
        }

        board.initializeBoard();
        resetToSelectPhase();

        add(new H1("Chess Board"), files);
    }

    private void selectPiece(Button button) {
        Position position = null;
        for (Position pos : buttonBoard.keySet()) {
            if (buttonBoard.get(pos).button().equals(button)) {
                position = pos;
                Notification.show("Button clicked at position " + pos);
                break;
            }
        }
        if (position == null) {
            Notification.show("Button not found in board");
            return;
        }

        for (ButtonWithListener b1 : buttonBoard.values()) {
            b1.button().setEnabled(false);
            b1.registration().remove();
        }

        Set<Position> moves = board.getValidMoves(position);
        if (board.isCheck(isWhiteTurn)) {
            moves = board.removeCheckMoves(moves, position, isWhiteTurn);
        }
        if(moves.isEmpty()) {
            Notification.show("No valid moves for this piece");
            resetToSelectPhase();
            return;
        }

        Notification.show(moves.toString());

        final Position finalPosition = position;
        button.setEnabled(true);
        Registration registration = button.addClickListener(click -> {
            resetToSelectPhase();
        });
        buttonBoard.put(position, new ButtonWithListener(button, registration));
        for (Position move : moves) {
            Button moveButton = buttonBoard.get(move).button();
            moveButton.setEnabled(true);
            moveButton.addClassName("selectable");
            registration = moveButton.addClickListener(click -> {
                movePiece(moveButton, finalPosition);
                resetToSelectPhase();
            });
            buttonBoard.put(move, new ButtonWithListener(moveButton, registration));
        }
    }

    private void movePiece(Button button, Position start) {
        Position position = null;
        for (Position pos : buttonBoard.keySet()) {
            if (buttonBoard.get(pos).button().equals(button)) {
                position = pos;
                board.movePiece(start, position);
                Notification.show("Piece moved to " + position);
                isWhiteTurn = !isWhiteTurn;
                break;
            }
        }
    }

    private void resetToSelectPhase() {
        for (Position position : buttonBoard.keySet()) {
            Piece piece = board.getPiece(position);
            Button button = buttonBoard.get(position).button();
            Registration registration = buttonBoard.get(position).registration();
            button.setEnabled(false);
            button.removeClassName("selectable");
            registration.remove();
            if (piece == null) {
                button.setText("");
            } else {
                button.setText(piece.toString());
                if (piece.isWhite == isWhiteTurn) button.setEnabled(true);
                Registration reg = button.addClickListener(click -> {
                    selectPiece(button);
                });
                buttonBoard.put(position, new ButtonWithListener(button, reg));
            }
            if ((position.rank() + position.file()) % 2 == 0) {
                button.addClassName("black");
            } else {
                button.addClassName("white");
            }
        }
    }

}
