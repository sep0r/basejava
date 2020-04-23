package com.urise.webapp.model;

import java.util.List;

public class SectionOrg extends Section {
    private List<Organization> orgText;

    public SectionOrg(List<Organization> orgText) {
        this.orgText = orgText;
    }

    public List<Organization> getOrgText() {
        return orgText;
    }
}