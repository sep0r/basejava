package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<Organization> orgText;

    public OrganizationSection() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(orgText, that.orgText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgText);
    }
}