package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<Organization> orgText;

    public OrganizationSection() {}

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