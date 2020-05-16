package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;

    private String uuid;
    private String fullName;

    private Map<ContactType, String> contact = new EnumMap<>(ContactType.class);
    private Map<SectionType, AbstractSection> textSection = new EnumMap<>(SectionType.class);

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void addContact(ContactType type, String value) {
        contact.put(type, value);
    }
    public void addSection(SectionType type, AbstractSection abstractSection) {
        textSection.put(type, abstractSection);
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

        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contact, resume.contact) &&
                Objects.equals(textSection, resume.textSection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contact, textSection);
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