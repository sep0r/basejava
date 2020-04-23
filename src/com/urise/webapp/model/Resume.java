package com.urise.webapp.model;

import java.util.Map;
import java.util.UUID;

public class Resume implements Comparable<Resume> {
    private String uuid;
    private String fullName;

    private Map<Contact, String> contact;
    private Map<SectionType, Section> textSection;

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Map<Contact, String> getContact() {
        return contact;
    }

    public void setContact(Map<Contact, String> contact) {
        this.contact = contact;
    }

    public Map<SectionType, Section> getTextSection() {
        return textSection;
    }

    public void setTextSection(Map<SectionType, Section> textSection) {
        this.textSection = textSection;
    }

    @Override
    public String toString() {
        return uuid + " name: " + fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (uuid != null ? !uuid.equals(resume.uuid) : resume.uuid != null) return false;
        return fullName != null ? fullName.equals(resume.fullName) : resume.fullName == null;

    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Resume o) {
        int result = fullName.compareTo(o.fullName);
        if (result != 0) {
            return result;
        } else {
            return uuid.compareTo(o.uuid);
        }
    }
}
