package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    protected Object getKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index != -1;
    }

    public void preSave(Object uuid, Resume resume) {
        list.add(resume);
    }

    public void preUpdate(Object index, Resume resume) {
        list.set((Integer)index, resume);
    }

    public Resume preGet(Object index) {
        return list.get((Integer)index);
    }

    public void preDelete(Object index) {
        list.remove((int)index);
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
