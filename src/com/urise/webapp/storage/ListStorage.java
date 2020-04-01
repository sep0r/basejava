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

    @Override
    protected boolean checkIndex(Object index) {
        return index != null;
    }

    public void preSave(Integer uuid, Resume resume) {
        list.add(resume);
    }

    public void preUpdate(String uuid, Resume resume) {
        Integer index = (Integer) ExistStorageException(uuid);
        list.set(index, resume);
    }

    public Resume preGet(Integer index) {
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
