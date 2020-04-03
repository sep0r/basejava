package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    protected int getKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(int index) {
        return index != -1;
    }

    public void preSave(int uuid, Resume resume) {
        list.add(resume);
    }

    public void preUpdate(int index, Resume resume) {
        list.set(index, resume);
    }

    public Resume preGet(int index) {
        return list.get(index);
    }

    public void preDelete(int index) {
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
