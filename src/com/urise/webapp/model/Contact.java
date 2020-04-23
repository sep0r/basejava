package com.urise.webapp.model;

public enum Contact {
    TELEPHONE("Телефон"),
    SKYPE("Скайп"),
    EMAIL("Е-mail"),
    LINKEDIN("LinkedIn"),
    GITHUB("GitHub"),
    STACKOVERFLOW("Stackoverflow");

    private String title;

    Contact(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
