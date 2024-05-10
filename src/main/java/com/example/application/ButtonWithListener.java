package com.example.application;

import com.vaadin.flow.component.button.Button;

public record ButtonWithListener(Button button, com.vaadin.flow.shared.Registration registration) {
    
}
