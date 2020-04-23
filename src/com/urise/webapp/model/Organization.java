package com.urise.webapp.model;

import java.time.YearMonth;

public class Organization {
    String nameOrganization;
    YearMonth startDate;
    YearMonth finishDate;
    String position;
    String content;
    Link link;

    public Organization(String nameOrganization, YearMonth startDate, YearMonth finishDate, String position, String content, String linkAddress) {
        this.nameOrganization = nameOrganization;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.position = position;
        this.content = content;
        this.link = new Link(linkAddress);
    }
}
