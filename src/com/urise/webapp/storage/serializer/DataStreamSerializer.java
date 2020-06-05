package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            forEachWrite(resume.getContact().entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            forEachWrite(resume.getTextSection().entrySet(), dos, entry -> {
                SectionType key = entry.getKey();
                dos.writeUTF(key.name());

                AbstractSection abstractSection = entry.getValue();
                switch (key) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) abstractSection).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        forEachWrite(((ListSection) abstractSection).getListText(), dos, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        forEachWrite(((OrganizationSection) abstractSection).getOrgText(), dos, organization -> {
                            dos.writeUTF(organization.getName());
                            dos.writeUTF(organization.getLink().getName());
                            dos.writeUTF(organization.getLink().getUrl());
                            forEachWrite(organization.getPositions(), dos, position -> {
                                dos.writeUTF(position.getPosition());
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getFinishDate().toString());
                                dos.writeUTF(position.getContent());
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            forEachRead(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            forEachRead(dis, () -> {
                AbstractSection abstractSection;
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        abstractSection = new TextSection(dis.readUTF());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        abstractSection = new ListSection(forEachListRead(dis, dis::readUTF));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        abstractSection = new OrganizationSection(
                                forEachListRead(dis, () -> new Organization(dis.readUTF(), dis.readUTF(), dis.readUTF(),
                                        forEachListRead(dis, () -> new Organization.Position(dis.readUTF(),
                                                LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF())))));
                        break;
                    default:
                        throw new IOException("IOException, section isn't found");
                }
                resume.addSection(sectionType, abstractSection);
            });
            return resume;
        }
    }

    private <T> void forEachWrite(Collection<T> type, DataOutputStream dos, ForEachWriter<T> writer) throws IOException {
        dos.writeInt(type.size());
        for (T t : type) {
            writer.write(t);
        }
    }

    private void forEachRead(DataInputStream dis, ForEachReader reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    private <T> List<T> forEachListRead(DataInputStream dis, ForEachListReader<T> reader) throws IOException {
        List<T> list = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private interface ForEachWriter<T> {
        void write(T t) throws IOException;
    }

    private interface ForEachReader {
        void read() throws IOException;
    }

    private interface ForEachListReader<T> {
        T read() throws IOException;
    }
}