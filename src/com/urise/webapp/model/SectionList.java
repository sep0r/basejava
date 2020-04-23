package com.urise.webapp.model;

import java.util.List;

public class SectionList extends Section {
    private List<String> listText;

    public SectionList(List<String> listText) {
        this.listText = listText;
    }

    public List<String> getListText() {
        return listText;
    }
}
