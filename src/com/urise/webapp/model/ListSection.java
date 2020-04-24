package com.urise.webapp.model;

import java.util.List;

public class ListSection extends AbstractSection {
    private List<String> listText;

    public ListSection(List<String> listText) {
        this.listText = listText;
    }

    public List<String> getListText() {
        return listText;
    }

    @Override
    public String toString() {
        return listText.toString();
    }
}
