package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContact();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, AbstractSection> sections = resume.getTextSection();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                AbstractSection abstractSection = entry.getValue();
                if (SectionType.OBJECTIVE.equals(entry.getKey()) || SectionType.PERSONAL.equals(entry.getKey())) {
                    dos.writeUTF(((TextSection) abstractSection).getText());
                }
                if (SectionType.ACHIEVEMENT.equals(entry.getKey()) || SectionType.QUALIFICATIONS.equals(entry.getKey())) {
                    List<String> listText = ((ListSection) abstractSection).getListText();
                    dos.writeInt(listText.size());
                    for (String text : listText) {
                        dos.writeUTF(text);
                    }
                }
                if (SectionType.EXPERIENCE.equals(entry.getKey()) || SectionType.EDUCATION.equals(entry.getKey())) {
                    List<Organization> organizationList = ((OrganizationSection) abstractSection).getOrgText();
                    dos.writeInt(organizationList.size());
                    for (Organization organization : organizationList) {
                        dos.writeUTF(organization.getName());
                        dos.writeUTF(organization.getLink().getName());
                        dos.writeUTF(organization.getLink().getUrl());
                        List<Organization.Position> positions = organization.getPositions();
                        dos.writeInt(positions.size());
                        for (Organization.Position position : positions) {
                            dos.writeUTF(position.getPosition());
                            dos.writeUTF(position.getStartDate().toString());
                            dos.writeUTF(position.getFinishDate().toString());
                            dos.writeUTF(position.getContent());
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int sizeContactType = dis.readInt();
            for (int i = 0; i < sizeContactType; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sizeSectionType = dis.readInt();
            AbstractSection AbstractSection = null;
            SectionType sectionType;
            for (int i = 0; i < sizeSectionType; i++) {
                sectionType = SectionType.valueOf(dis.readUTF());
                if (SectionType.OBJECTIVE.equals(sectionType) || SectionType.PERSONAL.equals(sectionType)) {
                    AbstractSection = new TextSection(dis.readUTF());
                }
                if (SectionType.ACHIEVEMENT.equals(sectionType) || SectionType.QUALIFICATIONS.equals(sectionType)) {
                    int sizeTextList = dis.readInt();
                    List<String> textList = new ArrayList<>();
                    for (int j = 0; j < sizeTextList; j++) {
                        textList.add(dis.readUTF());
                    }
                    AbstractSection = new ListSection(textList);
                }
                if (SectionType.EXPERIENCE.equals(sectionType) || SectionType.EDUCATION.equals(sectionType)) {
                    int sizeOrganizationList = dis.readInt();
                    List<Organization> organizationList = new ArrayList<>();
                    Organization organization = null;
                    for (int g = 0; g < sizeOrganizationList; g++) {
                        String name = dis.readUTF();
                        String linkName = dis.readUTF();
                        String linkUrl = dis.readUTF();
                        List<Organization.Position> positions = new ArrayList<>();
                        int sizeEventPeriod = dis.readInt();
                        for (int l = 0; l < sizeEventPeriod; l++) {
                            positions.add(new Organization.Position(dis.readUTF(), LocalDate.parse(dis.readUTF()),
                                    LocalDate.parse(dis.readUTF()), dis.readUTF()));
                        }
                        organization = new Organization(name, linkName, linkUrl, positions);
                        organizationList.add(organization);
                    }
                    AbstractSection = new OrganizationSection(organizationList);
                }
                resume.addSection(sectionType, AbstractSection);
            }
            return resume;
        }
    }
}