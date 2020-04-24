package com.urise.webapp.model;

public enum ContactType {
    TELEPHONE("Телефон"),
    SKYPE("Скайп"),
    EMAIL("Е-mail"),
    LINKEDIN("LinkedIn"),
    GITHUB("GitHub"),
    STACKOVERFLOW("Stackoverflow");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
