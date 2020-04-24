package com.urise.webapp.model;

import java.util.Map;
import java.util.UUID;

public class Resume implements Comparable<Resume> {
    private String uuid;
    private String fullName;

    private Map<ContactType, String> contact;
    private Map<SectionType, AbstractSection> textSection;

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

    public Map<ContactType, String> getContact() {
        return contact;
    }

    public void setContact(Map<ContactType, String> contact) {
        this.contact = contact;
    }

    public Map<SectionType, AbstractSection> getTextSection() {
        return textSection;
    }

    public void setTextSection(Map<SectionType, AbstractSection> textSection) {
        this.textSection = textSection;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid=' " + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", contact= " + contact +
                ", textSection= " + textSection +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (uuid != null ? !uuid.equals(resume.uuid) : resume.uuid != null) return false;
        if (fullName != null ? !fullName.equals(resume.fullName) : resume.fullName != null) return false;
        if (contact != null ? !contact.equals(resume.contact) : resume.contact != null) return false;
        return textSection != null ? textSection.equals(resume.textSection) : resume.textSection == null;

    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (textSection != null ? textSection.hashCode() : 0);
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