package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<String> listText;

    public ListSection() {
    }

    public List<String> getListText() {
        return listText;
    }

    public ListSection(List<String> listText) {
        this.listText = listText;
    }

    @Override
    public String toString() {
        return listText.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(listText, that.listText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listText);
    }
}
