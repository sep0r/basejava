package com.urise.webapp.model;

public class TextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private String text;

    public String getText() {
        return text;
    }

    public TextSection(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text.toString();
    }
}
