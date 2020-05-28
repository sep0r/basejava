package com.urise.webapp.model;

import java.util.List;

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
}
