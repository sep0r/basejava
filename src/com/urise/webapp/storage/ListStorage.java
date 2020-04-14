package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    @Override
    protected Object getSearchKey(String uuid) {
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

    @Override
    public void preSave(Object searchKey, Resume resume) {
        list.add(resume);
    }

    @Override
    public void preUpdate(Object searchKey, Resume resume) {
        list.set((int) searchKey, resume);
    }

    @Override
    public Resume preGet(Object searchKey) {
        return list.get((int) searchKey);
    }

    @Override
    public void preDelete(Object searchKey) {
        list.remove((int) searchKey);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(list);
    }

    @Override
    public void clear() {
        list.clear();
    }
}
