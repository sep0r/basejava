package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    protected Integer getKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    public void preSave(Integer uuid, Resume resume) {
        list.add(resume);
    }

    public void preUpdate(String uuid, Resume resume) {
        int index = (int) ExistStorageException(uuid);
        list.set(index, resume);
    }

    public Resume get(String uuid) {
        int index = (int) ExistStorageException(uuid);
        return list.get(index);
    }

    public void delete(String uuid) {
        int index = (int) ExistStorageException(uuid);
        list.remove(index);
    }

    public int size() {
        return list.size();
    }

    public Resume[] getAll() {
        return list.toArray(new Resume[list.size()]);
    }

    public void clear() {
        list.clear();
    }
}
