package com.urise.webapp.model;

import java.util.List;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<Organization> orgText;

    public OrganizationSection(List<Organization> orgText) {
        this.orgText = orgText;
    }

    public List<Organization> getOrgText() {
        return orgText;
    }

    @Override
    public String toString() {
        return orgText.toString();
    }
}