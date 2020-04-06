package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    protected Object getSearchKey (String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey != -1;
    }

    public void preSave(Object searchKey, Resume resume) {
        list.add(resume);
    }

    public void preUpdate(Object searchKey, Resume resume) {
        list.set((int)searchKey, resume);
    }

    public Resume preGet(Object searchKey) {
        return list.get((int)searchKey);
    }

    public void preDelete(Object searchKey) {
        list.remove((int)searchKey);
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
