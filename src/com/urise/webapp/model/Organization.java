package com.urise.webapp.model;

import java.time.YearMonth;

public class Organization {
    private String name;
    private YearMonth startDate;
    private YearMonth finishDate;
    private String position;
    private String content;
    private Link link;

    public Organization(String name, YearMonth startDate, YearMonth finishDate, String position, String content, String urlLink, String nameLink) {
        this.name = name;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.position = position;
        this.content = content;
        this.link = new Link(nameLink, urlLink);
    }

    @Override
    public String toString() {
        return name + " " + startDate + " - " + finishDate + " " + position + " " + content + " " + link;
    }
}