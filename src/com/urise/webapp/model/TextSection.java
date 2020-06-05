package com.urise.webapp.model;

public class TextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private String text;

    public TextSection() {
    }

    public String getText() {
        return text;
    }

    public TextSection(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return text.equals(that.text);

    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}
