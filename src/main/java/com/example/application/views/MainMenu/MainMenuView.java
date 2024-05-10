package com.example.application.views.MainMenu;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle(value = "Main Menu")
@Route(value = "")
public class MainMenuView extends VerticalLayout {
    public MainMenuView() {
        Button startGameButton = new Button("Start Game", click -> {
            UI.getCurrent().navigate("game");
        });

        add(startGameButton);
    }
}
